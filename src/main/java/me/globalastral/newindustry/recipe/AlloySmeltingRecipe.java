package me.globalastral.newindustry.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.blockentities.custom.AlloySmelterBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AlloySmeltingRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;

    public int getProcess_time() {
        return process_time;
    }

    private final int process_time;

    public AlloySmeltingRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> ingredients, int process_time) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
        this.process_time = process_time;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide())
            return false;

        return ingredients.get(0).test(pContainer.getItem(AlloySmelterBlockEntity.INPUT_SLOT1)) &&
                ingredients.get(1).test(pContainer.getItem(AlloySmelterBlockEntity.INPUT_SLOT2));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final String IDENTIFIER = "alloying";

    public static class Type implements RecipeType<AlloySmeltingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = IDENTIFIER;
    }

    public static class Serializer implements RecipeSerializer<AlloySmeltingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID, IDENTIFIER);


        @Override
        public AlloySmeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            int process_time = GsonHelper.getAsInt(pSerializedRecipe, "process_time");
            return new AlloySmeltingRecipe(pRecipeId, output, inputs, process_time);
        }

        @Override
        public @Nullable AlloySmeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            int process_time = buf.readInt();
            return new AlloySmeltingRecipe(pRecipeId, output, inputs, process_time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlloySmeltingRecipe pRecipe) {
            buf.writeInt(pRecipe.ingredients.size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
            buf.writeInt(pRecipe.process_time);
        }
    }
}
