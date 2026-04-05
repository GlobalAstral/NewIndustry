package me.globalastral.newindustry.blockentities.custom;

import me.globalastral.newindustry.utils.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEnergyMachineBlockEntity extends AbstractMachineBlockEntity {

    protected abstract int get_capacity();
    protected abstract int get_max_transfer();

    protected CustomEnergyStorage energyStorage;
    protected CustomEnergyStorage getEnergyStorage() {
        if (energyStorage == null) {
            energyStorage = new CustomEnergyStorage(get_capacity(), get_max_transfer()) {
                @Override
                public void onEnergyChanged() {
                    setChanged();
                }
            };
        }
        return energyStorage;
    }
    protected LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    public AbstractEnergyMachineBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyStorage = LazyOptional.of(this::getEnergyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyStorage.invalidate();
    }

    @Override
    public void load(CompoundTag pTag) {
        getEnergyStorage().setEnergy(pTag.getInt("energy"));
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("energy", getEnergyStorage().getEnergyStored());
        super.saveAdditional(pTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
            return lazyEnergyStorage.cast();
        return super.getCapability(cap, side);
    }
}
