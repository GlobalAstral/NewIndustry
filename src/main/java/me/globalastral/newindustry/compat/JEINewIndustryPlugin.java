package me.globalastral.newindustry.compat;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.gui.alloy_smelter.AlloySmelterScreen;
import me.globalastral.newindustry.recipe.AlloySmeltingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEINewIndustryPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlloySmeltingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        List<AlloySmeltingRecipe> recipes = manager.getAllRecipesFor(AlloySmeltingRecipe.Type.INSTANCE);
        registration.addRecipes(AlloySmeltingCategory.ALLOY_SMELTING_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloySmelterScreen.class, 81, 19, 13, 13, AlloySmeltingCategory.ALLOY_SMELTING_TYPE);
    }
}
