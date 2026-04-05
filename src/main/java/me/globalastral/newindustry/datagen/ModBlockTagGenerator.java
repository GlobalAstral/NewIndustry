package me.globalastral.newindustry.datagen;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NewIndustry.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.ALLOY_SMELTER.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALLOY_SMELTER.get());
    }
}
