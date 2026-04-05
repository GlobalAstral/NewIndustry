package me.globalastral.newindustry.blocks.custom;

import me.globalastral.newindustry.blockentities.ModBlockEntities;
import me.globalastral.newindustry.blockentities.custom.AlloySmelterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AlloySmelterBlock extends AbstractMachineBlock<AlloySmelterBlockEntity> {

    public static final BooleanProperty HAS_PORTS = BooleanProperty.create("has_ports");

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(3f, 0f, 3f, 13f, 16f, 13f);
    }

    public AlloySmelterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HAS_PORTS);
    }

    @Override
    protected boolean is_compatible(BlockEntity entity) {
        return entity instanceof AlloySmelterBlockEntity;
    }

    @Override
    protected AlloySmelterBlockEntity cast(BlockEntity entity) {
        return (AlloySmelterBlockEntity) entity;
    }

    @Override
    protected BlockEntityType<AlloySmelterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.ALLOY_SMELTER_BE.get();
    }

    @Override
    protected BlockState getDefaultState() {
        return this.defaultBlockState().setValue(HAS_PORTS, false);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlloySmelterBlockEntity(pPos, pState);
    }
}
