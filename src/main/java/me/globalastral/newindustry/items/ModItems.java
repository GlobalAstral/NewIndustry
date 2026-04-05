package me.globalastral.newindustry.items;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.items.custom.SteelIngotItem;
import me.globalastral.newindustry.items.custom.SteelPurityLevel;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public enum ModItems implements Supplier<RegistryObject<Item>> {
    CRUDE_STEEL_INGOT("crude_steel_ingot", () -> new SteelIngotItem(new Item.Properties(), SteelPurityLevel.CRUDE)),
    REFINED_STEEL_INGOT("refined_steel_ingot", () -> new SteelIngotItem(new Item.Properties(), SteelPurityLevel.REFINED)),
    INDUSTRIAL_STEEL_INGOT("industrial_steel_ingot", () -> new SteelIngotItem(new Item.Properties(), SteelPurityLevel.INDUSTRIAL)),
    COAL_COKE("coal_coke", () -> new Item(new Item.Properties())),
    ;

    private final RegistryObject<Item> ITEM;

    private static DeferredRegister<Item> ITEMS;

    public static DeferredRegister<Item> getITEMS() {
        if (ITEMS == null)
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NewIndustry.MODID);
        return ITEMS;
    }

    ModItems(String name, Supplier<Item> item) {
        this.ITEM = getITEMS().register(name, item);
    }

    @Override
    public RegistryObject<Item> get() {
        return ITEM;
    }

    public static void register(IEventBus eventBus) {
        ModItems.values();
        getITEMS().register(eventBus);
    }
}
