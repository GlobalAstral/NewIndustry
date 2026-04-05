package me.globalastral.newindustry.blockentities.custom;

import me.globalastral.newindustry.blockentities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
//TODO FAI INTERFACCIA PER AIUTI SU PROGRESS E MAX PROGRESS SE SERVE
public class AlloySmelterBlockEntity extends AbstractMachineBlockEntity {

    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int OUTPUT_SLOT = 2;

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
}
