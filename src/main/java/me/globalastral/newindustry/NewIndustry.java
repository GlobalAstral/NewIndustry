package me.globalastral.newindustry;

import com.mojang.logging.LogUtils;
import me.globalastral.newindustry.blockentities.ModBlockEntities;
import me.globalastral.newindustry.blocks.ModBlocks;
import me.globalastral.newindustry.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(NewIndustry.MODID)
public class NewIndustry {
    public static final String MODID = "newindustry";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> NewIndustryTab = CREATIVE_MODE_TABS.register("newindustry_tab", () -> CreativeModeTab.builder()
            .icon(() -> ModItems.INDUSTRIAL_STEEL_INGOT.get().get().getDefaultInstance())
            .withSearchBar()
            .title(Component.translatable("tab.newindustry.main_tab"))
            .displayItems((parameters, output) -> ModItems.getITEMS().getEntries().forEach(item -> output.accept(item.get()))).build());

    public NewIndustry(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
