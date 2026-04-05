package me.globalastral.newindustry.blockentities;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.blockentities.custom.AlloySmelterBlockEntity;
import me.globalastral.newindustry.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,
            NewIndustry.MODID);

    public static final RegistryObject<BlockEntityType<AlloySmelterBlockEntity>> ALLOY_SMELTER_BE = register("alloy_smelter_be",
            AlloySmelterBlockEntity::new, ModBlocks.ALLOY_SMELTER);

    private static  <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, RegistryObject<Block> block) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
