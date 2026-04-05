package me.globalastral.newindustry.datagen;

import me.globalastral.newindustry.NewIndustry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockstateProvider extends BlockStateProvider {
    public ModBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NewIndustry.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
