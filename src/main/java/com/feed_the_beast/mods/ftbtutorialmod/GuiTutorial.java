package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.gui.GuiBase;
import com.feed_the_beast.ftblib.lib.gui.GuiHelper;
import com.feed_the_beast.ftblib.lib.gui.Theme;
import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.util.misc.MouseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * @author LatvianModder
 */
public class GuiTutorial extends GuiBase
{
	public final Tutorial tutorial;
	public int page = 0;

	public GuiTutorial(Tutorial t)
	{
		tutorial = t;
	}

	@Override
	public void addWidgets()
	{
	}

	@Override
	public boolean onInit()
	{
		return setFullscreen();
	}

	@Override
	public void onBack()
	{
		if (page(false))
		{
			GuiHelper.playClickSound();
		}
	}

	public boolean page(boolean next)
	{
		if (next)
		{
			if (page < tutorial.pages.size() - 1)
			{
				page++;
				return true;
			}
		}
		else if (page > 0)
		{
			page--;
			return true;
		}

		return false;
	}

	@Override
	public void drawBackground(Theme theme, int x, int y, int w, int h)
	{
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
		GlStateManager.color(1F, 1F, 1F, 1F);
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(x, y + h, 0D).tex(0D, h / 32D).color(64, 64, 64, 255).endVertex();
		bufferbuilder.pos(x + w, y + h, 0D).tex(w / 32D, h / 32D).color(64, 64, 64, 255).endVertex();
		bufferbuilder.pos(x + w, y, 0D).tex(w / 32D, 0D).color(64, 64, 64, 255).endVertex();
		bufferbuilder.pos(x, y, 0D).tex(0D, 0D).color(64, 64, 64, 255).endVertex();
		tessellator.draw();

		if (tutorial.pages.isEmpty())
		{
			return;
		}

		if (getMouseX() < 30)
		{
			Color4I.WHITE.withAlpha(50).draw(x, y, 30, h);
		}
		else if (getMouseX() > w - 30)
		{
			Color4I.WHITE.withAlpha(50).draw(x + w - 30, y, 30, h);
		}
	}

	@Override
	public void drawForeground(Theme theme, int x, int y, int w, int h)
	{
		if (tutorial.pages.isEmpty())
		{
			theme.drawString(TextFormatting.BOLD + I18n.format("ftbtutorialmod.gui.failed_to_load"), x + w / 2, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
			return;
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.color(1F, 1F, 1F, 1F);

		TextureManager manager = Minecraft.getMinecraft().getTextureManager();

		for (ResourceLocation layer : tutorial.pages.get(page).layers)
		{
			manager.bindTexture(layer);
			int rtw = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
			int rth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);

			double tw = w - 70;
			double th = h - 60;

			double scale = Math.max(rtw / tw, rth / th);

			if (scale > 1D)
			{
				tw = rtw / scale;
				th = rth / scale;
			}

			double tx = (w - tw) / 2D;
			double ty = 22 + (h - th - 29) / 2D;

			bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos(tx, ty + th, 0D).tex(0D, 1D).endVertex();
			bufferbuilder.pos(tx + tw, ty + th, 0D).tex(1D, 1D).endVertex();
			bufferbuilder.pos(tx + tw, ty, 0D).tex(1D, 0D).endVertex();
			bufferbuilder.pos(tx, ty, 0D).tex(0D, 0D).endVertex();
			tessellator.draw();

			GuiHelper.drawHollowRect((int) (tx - 1D), (int) (ty - 1D), (int) (tw + 2D), (int) (th + 2D), Color4I.BLACK, false);
		}

		theme.drawString(TextFormatting.BOLD + tutorial.title, x + w / 2, y + 10, Color4I.WHITE, Theme.CENTERED);
		theme.drawString(tutorial.pages.get(page).description, x + w / 2, y + 23, Color4I.WHITE, Theme.CENTERED);
		theme.drawString((page + 1) + " / " + tutorial.pages.size(), x + w / 2, y + h - 17, Color4I.WHITE, Theme.CENTERED);

		theme.drawString(TextFormatting.BOLD + "<", x + 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
		theme.drawString(TextFormatting.BOLD + (page == tutorial.pages.size() - 1 ? "X" : ">"), x + w - 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
	}

	@Override
	public boolean keyPressed(int key, char keyChar)
	{
		if (key == Keyboard.KEY_LEFT || key == Keyboard.KEY_BACK)
		{
			page(false);
			return true;
		}
		else if (key == Keyboard.KEY_RIGHT || key == Keyboard.KEY_NEXT)
		{
			page(true);
			return true;
		}

		return super.keyPressed(key, keyChar);
	}

	@Override
	public boolean mousePressed(MouseButton button)
	{
		if (button.id == 3 || getMouseX() < 30)
		{
			if (page(false))
			{
				GuiHelper.playClickSound();
			}

			return true;
		}
		else if (button.id == 4)
		{
			if (page(true))
			{
				GuiHelper.playClickSound();
			}

			return true;
		}
		else if (getMouseX() > width - 30)
		{
			if (page == tutorial.pages.size() - 1)
			{
				GuiHelper.playClickSound();
				closeGui();
			}
			else if (page(true))
			{
				GuiHelper.playClickSound();
			}

			return true;
		}

		return super.mousePressed(button);
	}

	@Override
	public boolean mouseScrolled(int scroll)
	{
		return page(scroll < 0);
	}
}