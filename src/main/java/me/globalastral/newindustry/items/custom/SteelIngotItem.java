package me.globalastral.newindustry.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SteelIngotItem extends Item {

    private final SteelPurityLevel purityLevel;

    public SteelIngotItem(Properties pProperties, SteelPurityLevel purityLevel) {
        super(pProperties);
        this.purityLevel = purityLevel;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(
                Component.literal(purityLevel.toString()).withStyle(style -> style.withColor(ChatFormatting.BLUE))
        );
    }
}
