package me.globalastral.newindustry.blocks;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.blocks.custom.AlloySmelterBlock;
import me.globalastral.newindustry.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            NewIndustry.MODID);

    public static final RegistryObject<Block> ALLOY_SMELTER = register("alloy_smelter", () -> new AlloySmelterBlock(
            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()), new Item.Properties());

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties properties) {
        RegistryObject<Block> ret = BLOCKS.register(name, block);
        ModItems.getITEMS().register(name, () -> new BlockItem(ret.get(), properties));
        return ret;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
