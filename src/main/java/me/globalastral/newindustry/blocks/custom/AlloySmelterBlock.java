package me.globalastral.newindustry.blocks.custom;

import me.globalastral.newindustry.blockentities.ModBlockEntities;
import me.globalastral.newindustry.blockentities.custom.AlloySmelterBlockEntity;
import me.globalastral.newindustry.blocks.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
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
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide())
            return InteractionResult.PASS;

        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (!pState.getValue(HAS_PORTS) && stack.is(Items.IRON_INGOT)) {
            if (!pPlayer.isCreative())
                stack.shrink(1);
            pLevel.setBlock(pPos, pState.setValue(HAS_PORTS, true), 11);
            return InteractionResult.SUCCESS;
        } else if (pState.getValue(HAS_PORTS) && pLevel.getBlockEntity(pPos) instanceof AlloySmelterBlockEntity entity) {
            NetworkHooks.openScreen((ServerPlayer) pPlayer, entity, pPos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        if (pState.getBlock() != pNewState.getBlock() && pState.getValue(HAS_PORTS)) {
            ItemStack stack = new ItemStack(Items.IRON_INGOT, 1);
            ItemEntity entity = new ItemEntity(
                    pLevel,
                    pPos.getX(),
                    pPos.getY(),
                    pPos.getZ(),
                    stack
            );
            pLevel.addFreshEntity(entity);
        }
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
