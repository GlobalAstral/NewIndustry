package me.globalastral.newindustry.datagen;

import me.globalastral.newindustry.NewIndustry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = NewIndustry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup_provider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new ModBlockstateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        ModBlockTagGenerator modBlockTagGenerator = generator.addProvider(event.includeServer(), new ModBlockTagGenerator(packOutput, lookup_provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput, lookup_provider, modBlockTagGenerator.contentsGetter(), existingFileHelper));
    }
}
