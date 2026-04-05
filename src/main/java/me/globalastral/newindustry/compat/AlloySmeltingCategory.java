package me.globalastral.newindustry.compat;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.blocks.ModBlocks;
import me.globalastral.newindustry.recipe.AlloySmeltingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AlloySmeltingCategory implements IRecipeCategory<AlloySmeltingRecipe> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, AlloySmeltingRecipe.IDENTIFIER);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, "textures/gui/alloy_smelter.png");

    public static final RecipeType<AlloySmeltingRecipe> ALLOY_SMELTING_TYPE = new RecipeType<>(UID, AlloySmeltingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private static final int TEX_W = 176;
    private static final int TEX_H = 166;
    private static final int TEX_H_SHORT = 85;

    public AlloySmeltingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, TEX_W, TEX_H_SHORT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ALLOY_SMELTER.get()));
    }

    @Override
    public RecipeType<AlloySmeltingRecipe> getRecipeType() {
        return ALLOY_SMELTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.newindustry.alloy_smelter_title");
    }

    @SuppressWarnings("for removal")
    @Nullable
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    private static final double HEIGHT_RATIO = (double) TEX_H_SHORT / (double) TEX_H;

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloySmeltingRecipe recipe, IFocusGroup iFocusGroup) {
        int height = (int) (24 * HEIGHT_RATIO) + 11;
        int out_height = (int)(53 * HEIGHT_RATIO) + 25;
        builder.addInputSlot(56, height).addIngredients(recipe.getIngredients().get(0));
        builder.addInputSlot(102, height).addIngredients(recipe.getIngredients().get(1));
        builder.addOutputSlot(79, out_height).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}
