package me.globalastral.newindustry.gui.alloy_smelter;

import me.globalastral.newindustry.blockentities.custom.AlloySmelterBlockEntity;
import me.globalastral.newindustry.blocks.ModBlocks;
import me.globalastral.newindustry.gui.AbstractMachineMenu;
import me.globalastral.newindustry.gui.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class AlloySmelterMenu extends AbstractMachineMenu<AlloySmelterBlockEntity> {
    private static final int CONTAINER_SIZE = 3;

    public AlloySmelterMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        super(pContainerId, inv, extraData, CONTAINER_SIZE, ModMenuTypes.ALLOY_SMELTER_MENU.get());
    }

    public AlloySmelterMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ALLOY_SMELTER_MENU.get(), pContainerId, inv, entity, data);
    }

    @Override
    protected AlloySmelterBlockEntity cast(BlockEntity entity) {
        return (AlloySmelterBlockEntity) entity;
    }

    @Override
    protected RegistryObject<Block> getBlock() {
        return ModBlocks.ALLOY_SMELTER;
    }

    @Override
    protected int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    protected List<Slot> getSlots(IItemHandler itemHandler) {
        return List.of(
            new SlotItemHandler(itemHandler, AlloySmelterBlockEntity.INPUT_SLOT1, 56, 24),
            new SlotItemHandler(itemHandler, AlloySmelterBlockEntity.INPUT_SLOT2, 102, 24),
            new SlotItemHandler(itemHandler, AlloySmelterBlockEntity.OUTPUT_SLOT, 79, 53)
        );
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress(int size) {
        int progress = data.get(0);
        int max_progress = data.get(1);

        return (max_progress != 0 && progress != 0) ? (progress * size / max_progress) : 0;
    }
}
