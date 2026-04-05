package me.globalastral.newindustry.gui.alloy_smelter;

import me.globalastral.newindustry.NewIndustry;
import me.globalastral.newindustry.gui.AbstractMachineScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloySmelterScreen extends AbstractMachineScreen<AlloySmelterMenu> {
    public AlloySmelterScreen(AlloySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NewIndustry.MODID,
            "textures/gui/alloy_smelter.png");

    @Override
    protected ResourceLocation getBackgroundTex() {
        return TEXTURE;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void renderAfterBG(GuiGraphics pGuiGraphics, int x, int y, float pPartialTick, int pMouseX, int pMouseY) {
        if(menu.isCrafting()) {
            pGuiGraphics.blit(TEXTURE, x + 81, y + 19, 176, 0, 14, 14 - menu.getScaledProgress(14));
            pGuiGraphics.blit(TEXTURE, x + 73, y + 35, 176, 15, 28, menu.getScaledProgress(17));
        }
    }
}
