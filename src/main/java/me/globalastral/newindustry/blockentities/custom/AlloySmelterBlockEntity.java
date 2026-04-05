package me.globalastral.newindustry.blockentities.custom;

import me.globalastral.newindustry.blockentities.AbstractMachineBlockEntity;
import me.globalastral.newindustry.blockentities.ModBlockEntities;
import me.globalastral.newindustry.gui.alloy_smelter.AlloySmelterMenu;
import me.globalastral.newindustry.recipe.AlloySmeltingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlloySmelterBlockEntity extends AbstractMachineBlockEntity implements MenuProvider {

    public static final int INPUT_SLOT1 = 0;
    public static final int INPUT_SLOT2 = 1;
    public static final int OUTPUT_SLOT = 2;

    private int progress = 0;
    private int max_progress;

    public AlloySmelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALLOY_SMELTER_BE.get(), pPos, pBlockState);
    }

    @Override
    public int getSlotAmount() {
        return 3;
    }

    @Override
    public int getInContainerData(int index) {
        return switch (index) {
            case 0 -> progress;
            case 1 -> max_progress;
            default -> 0;
        };
    }

    @Override
    public void setInContainerData(int index, int value) {
        switch (index) {
            case 0 -> progress = value;
            case 1 -> max_progress = value;
        }
    }

    @Override
    public int containerDataCount() {
        return 2;
    }

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putInt("progress", progress);
        nbt.putInt("max_progress", max_progress);
    }

    @Override
    public void readNBT(CompoundTag nbt) {
        progress = nbt.getInt("progress");
        max_progress = nbt.getInt("max_progress");
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide())
            return;

        AlloySmeltingRecipe recipe = getRecipe();
        if (recipe == null) {
            resetProgress();
            return;
        }
        incrementProgress();
        setChanged(level, pos, state);
        if (progress >= recipe.getProcess_time()) {
            craftItem(recipe);
            resetProgress();
        }
    }

    private void craftItem(AlloySmeltingRecipe recipe) {
        ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
        ItemStackHandler handler = this.getItemHandler();
        handler.extractItem(INPUT_SLOT1, 1, false);
        handler.extractItem(INPUT_SLOT2, 1, false);
        handler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), handler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private void incrementProgress() {
        progress++;
    }

    private void resetProgress() {
        progress = 0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.newindustry.alloy_smelter_title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlloySmelterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    private @Nullable AlloySmeltingRecipe getRecipe() {
        Level level = this.level;
        SimpleContainer inv = new SimpleContainer(this.getItemHandler().getSlots());
        for (int i = 0; i < this.getItemHandler().getSlots(); i++) {
            inv.setItem(i, this.getItemHandler().getStackInSlot(i));
        }

        Optional<AlloySmeltingRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AlloySmeltingRecipe.Type.INSTANCE, inv, level);

        if (recipe.isPresent()) {
            AlloySmeltingRecipe rec = recipe.get();
            ItemStack stack = rec.getResultItem(RegistryAccess.EMPTY);
            if (canInsertAmount(inv, stack.getCount()) && canInsertItem(inv, stack)) {
                this.max_progress = rec.getProcess_time();
                return rec;
            }
        }
        return null;
    }

    private static boolean canInsertItem(SimpleContainer inv, ItemStack stack) {
        ItemStack itemStack = inv.getItem(OUTPUT_SLOT);
        return itemStack.getItem() == stack.getItem() || itemStack.isEmpty();
    }

    private static boolean canInsertAmount(SimpleContainer inv, int amount) {
        ItemStack stack = inv.getItem(OUTPUT_SLOT);
        return stack.getCount() + amount <= stack.getMaxStackSize();
    }
}
