package com.gonggongjohn.eok.client.gui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ButtonPrimaryBlueprintTableComponent extends GuiButton {
    private boolean activeTag;
    private int textureCommonU;
    private int textureCommonV;
    private int textureActiveU;
    private int textureActiveV;
    private ResourceLocation texturePath;
    private GUIPrimaryBlueprintTable fatherInstance;
    private Block content;

    public ButtonPrimaryBlueprintTableComponent(int buttonId, int x, int y, int textureCommonU, int textureCommonV, int textureActiveU, int textureActiveV, int widthIn, int heightIn, Block content, ResourceLocation texturePath, GUIPrimaryBlueprintTable fatherInstance) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.activeTag = false;
        this.textureCommonU = textureCommonU;
        this.textureCommonV = textureCommonV;
        this.textureActiveU = textureActiveU;
        this.textureActiveV = textureActiveV;
        this.content = content;
        this.texturePath = texturePath;
        this.fatherInstance = fatherInstance;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if(this.visible) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(texturePath);
            if(activeTag)
                drawTexturedModalRect(this.x, this.y, this.textureActiveU, this.textureActiveV, this.width, this.height);
            else
                drawTexturedModalRect(this.x, this.y, this.textureCommonU, this.textureCommonV, this.width, this.height);
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this.content));
            fatherInstance.drawItemStack(stack, this.x + 1, this.y + 1, "");
            int relx = mouseX - this.x, rely = mouseY - this.y;
            if (relx >= 0 && rely >= 0 && relx < this.width && rely < this.height){
                String name = I18n.format("eok.blueprint.component.pre") + this.content.getLocalizedName();
                mc.fontRenderer.drawString(name, mouseX + 5, mouseY + 5, 0xFF0000);
            }
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public boolean isActive() {
        return this.activeTag;
    }

    public void flipActive() {
        this.activeTag = !this.activeTag;
    }

    public Block getContent() {
        return content;
    }
}
