package me.globalastral.newindustry.blockentities.custom;

import me.globalastral.newindustry.blockentities.AbstractMachineBlockEntity;
import me.globalastral.newindustry.blockentities.ModBlockEntities;
import me.globalastral.newindustry.gui.alloy_smelter.AlloySmelterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AlloySmelterBlockEntity extends AbstractMachineBlockEntity implements MenuProvider {

    public static final int INPUT_SLOT1 = 0;
    public static final int INPUT_SLOT2 = 1;
    public static final int OUTPUT_SLOT = 2;

    private int progress = 0;
    private int max_progress;

    public AlloySmelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_SMELTER_BE.get(), pPos, pBlockState);
    }

    @Override
    public int getSlotAmount() {
        return 3;
    }

    @Override
    public int getInContainerData(int index) {
        return switch (index) {
            case 0 -> progress;
            case 1 -> max_progress;
            default -> 0;
        };
    }

    @Override
    public void setInContainerData(int index, int value) {
        switch (index) {
            case 0 -> progress = value;
            case 1 -> max_progress = value;
        }
    }

    @Override
    public int containerDataCount() {
        return 2;
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putInt("progress", progress);
        nbt.putInt("max_progress", max_progress);
    }

    @Override
    public void readNBT(CompoundTag nbt) {
        progress = nbt.getInt("progress");
        max_progress = nbt.getInt("max_progress");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.newindustry.alloy_smelter_title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlloySmelterMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
