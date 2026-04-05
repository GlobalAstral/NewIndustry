package me.globalastral.newindustry.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineBlockEntity extends BlockEntity {
    protected abstract int getSlotAmount();
    protected abstract int getInContainerData(int index);
    protected abstract void setInContainerData(int index, int value);
    protected abstract int containerDataCount();
    protected abstract void writeNBT(CompoundTag nbt);
    protected abstract void readNBT(CompoundTag nbt);
    public abstract void tick(Level level, BlockPos pos, BlockState state);

    protected ItemStackHandler itemHandler;
    protected LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;

    protected ItemStackHandler getItemHandler() {
        if (itemHandler == null)
            itemHandler = new ItemStackHandler(getSlotAmount()) {
                @Override
                protected void onContentsChanged(int slot) {
                    setChanged();
                }
            };
        return itemHandler;
    }

    public AbstractMachineBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return getInContainerData(pIndex);
            }

            @Override
            public void set(int pIndex, int pValue) {
                setInContainerData(pIndex, pValue);
            }

            @Override
            public int getCount() {
                return containerDataCount();
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(this::getItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        getItemHandler().deserializeNBT(pTag.getCompound("inventory"));
        readNBT(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", getItemHandler().serializeNBT());
        writeNBT(pTag);
        super.saveAdditional(pTag);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(getItemHandler().getSlots());
        for (int i = 0; i < getItemHandler().getSlots(); i++) {
            inventory.setItem(i, getItemHandler().getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
