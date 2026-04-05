package me.globalastral.newindustry.datagen;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NewIndustry.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemWithTexture(ModItems.CRUDE_STEEL_INGOT.get(), "steel_ingot");
        itemWithTexture(ModItems.REFINED_STEEL_INGOT.get(), "steel_ingot");
        itemWithTexture(ModItems.INDUSTRIAL_STEEL_INGOT.get(), "steel_ingot");
        simpleItem(ModItems.COAL_COKE.get());
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder itemWithTexture(RegistryObject<Item> item, String texture) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "item/" + texture));
    }

    private ItemModelBuilder handheldWithTexture(RegistryObject<Item> item, String texture) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "item/" + texture));
    }

    private ItemModelBuilder simpleHandheld(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "item/" + item.getId().getPath()));
    }
}
