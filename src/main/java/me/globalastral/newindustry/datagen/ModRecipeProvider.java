package me.globalastral.newindustry.datagen;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    private static final List<ItemLike> COKEABLES = List.of(Items.COAL, Items.CHARCOAL);

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreBlasting(
                pWriter,
                COKEABLES,
                RecipeCategory.MISC,
                ModItems.COAL_COKE.get().get(),
                0.1f,
                180,
                "cokeables"
        );
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients)
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer,
                            NewIndustry.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }
}
