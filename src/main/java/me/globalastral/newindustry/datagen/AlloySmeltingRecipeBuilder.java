package me.globalastral.newindustry.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.recipe.AlloySmeltingRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AlloySmeltingRecipeBuilder implements FinishedRecipe {
    private final ResourceLocation id;
    private final RecipeSerializer<?> serializer;
    private final ArrayList<ItemStack> inputs = new ArrayList<>();
    private ItemStack output;
    private int process_time;

    private AlloySmeltingRecipeBuilder(ResourceLocation id, RecipeSerializer<?> serializer) {
        this.id = id;
        this.serializer = serializer;
    }

    public static AlloySmeltingRecipeBuilder builder(ResourceLocation id, RecipeSerializer<?> serializer) {
        return new AlloySmeltingRecipeBuilder(id, serializer);
    }

    public AlloySmeltingRecipeBuilder addIngredient(ItemStack ingredient) {
        inputs.add(ingredient);
        return this;
    }

    public AlloySmeltingRecipeBuilder output(ItemStack itemLike) {
        output = itemLike;
        return this;
    }

    public AlloySmeltingRecipeBuilder process_time(int process_time) {
        this.process_time = process_time;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        consumer.accept(this);
    }

    public static void alloying(Consumer<FinishedRecipe> pWriter, List<ItemLike> ingredients, ItemLike output, int process_time) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        ingredients.forEach(ing -> stacks.add(new ItemStack(ing)));
        alloying(pWriter, stacks, new ItemStack(output), process_time);
    }

    public static void alloying(Consumer<FinishedRecipe> pWriter, List<ItemStack> ingredients, ItemStack output, int process_time) {
        AlloySmeltingRecipeBuilder build = builder(
                ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, AlloySmeltingRecipe.IDENTIFIER + "_" + output.getItem()), AlloySmeltingRecipe.Serializer.INSTANCE)
                .output(output)
                .process_time(process_time);
        ingredients.forEach(build::addIngredient);
        build.save(pWriter);
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        json.add("output", Ingredient.of(output).toJson());
        JsonArray array = new JsonArray();
        inputs.forEach(input -> array.add(Ingredient.of(input).toJson()));
        json.add("ingredients", array);
        json.addProperty("process_time", process_time);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return serializer;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
