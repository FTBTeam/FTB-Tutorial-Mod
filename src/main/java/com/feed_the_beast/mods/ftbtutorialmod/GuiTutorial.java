package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.gui.GuiBase;
import com.feed_the_beast.ftblib.lib.gui.GuiHelper;
import com.feed_the_beast.ftblib.lib.gui.Theme;
import com.feed_the_beast.ftblib.lib.icon.Color4I;
import com.feed_the_beast.ftblib.lib.util.misc.MouseButton;
import com.feed_the_beast.mods.ftbtutorialmod.data.HoverTextLayer;
import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import com.feed_the_beast.mods.ftbtutorialmod.data.TutorialLayer;
import com.feed_the_beast.mods.ftbtutorialmod.data.TutorialPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.List;

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
		ResourceLocation background = Gui.OPTIONS_BACKGROUND;

		if (tutorial.background != null)
		{
			background = tutorial.background;

			if (!tutorial.pages.isEmpty() && tutorial.pages.get(page).background != null)
			{
				background = tutorial.pages.get(page).background;
			}
		}

		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);
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

		TutorialPage p = tutorial.pages.get(page);

		double maxBoxW = width - 72D;
		double maxBoxH = height - 62D;
		double maxBoxX = (width - maxBoxW) / 2D;
		double maxBoxY = 38D;

		if (p.description.isEmpty())
		{
			maxBoxY = 27D;
			maxBoxH = height - 52D;
		}

		double canvasW = maxBoxW;
		double canvasH = maxBoxH;

		double scale = Math.max(p.width / maxBoxW, p.height / maxBoxH);

		if (scale > 1D)
		{
			canvasW = p.width / scale;
			canvasH = p.height / scale;
		}

		double canvasX = maxBoxX + (maxBoxW - canvasW) / 2D;
		double canvasY = maxBoxY + (maxBoxH - canvasH) / 2D;

		for (TutorialLayer layer : p.layers)
		{
			double tw = layer.width / p.width * canvasW;
			double th = layer.height / p.height * canvasH;
			double tx = canvasX + (layer.posX == -1D ? ((p.width - layer.width) / 2D) : layer.posX) / p.width * canvasW;
			double ty = canvasY + (layer.posY == -1D ? ((p.height - layer.height) / 2D) : layer.posY) / p.height * canvasH;
			layer.draw(this, tx, ty, tw, th);

			if (Theme.renderDebugBoxes)
			{
				GuiHelper.drawHollowRect((int) tx - 1, (int) ty - 1, (int) tw + 2, (int) th + 2, Color4I.GRAY.withAlpha(50), true);
			}
		}

		Color4I col = p.border;

		if (col.isEmpty())
		{
			col = tutorial.border;
		}

		if (Theme.renderDebugBoxes && col.isEmpty())
		{
			col = Color4I.WHITE;
		}

		if (Theme.renderDebugBoxes)
		{
			GuiHelper.drawHollowRect((int) maxBoxX - 1, (int) maxBoxY - 1, (int) maxBoxW + 2, (int) maxBoxH + 2, Color4I.GRAY.withAlpha(50), true);
		}

		GuiHelper.drawHollowRect((int) canvasX - 1, (int) canvasY - 1, (int) canvasW + 2, (int) canvasH + 2, col, false);

		if (!tutorial.title.isEmpty())
		{
			theme.drawString(TextFormatting.BOLD + tutorial.title, x + w / 2, y + 10, Color4I.WHITE, Theme.CENTERED);
		}

		if (!p.description.isEmpty())
		{
			theme.drawString(p.description, x + w / 2, y + 23, Color4I.WHITE, Theme.CENTERED);
		}

		theme.drawString((page + 1) + " / " + tutorial.pages.size(), x + w / 2, y + h - 17, Color4I.WHITE, Theme.CENTERED);

		theme.drawString(TextFormatting.BOLD + "<", x + 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
		theme.drawString(TextFormatting.BOLD + (page == tutorial.pages.size() - 1 ? "X" : ">"), x + w - 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
	}

	@Override
	public void addMouseOverText(List<String> list)
	{
		super.addMouseOverText(list);

		if (!tutorial.pages.isEmpty())
		{
			TutorialPage p = tutorial.pages.get(page);

			double maxBoxW = width - 72D;
			double maxBoxH = height - 62D;
			double maxBoxX = (width - maxBoxW) / 2D;
			double maxBoxY = 38D;

			if (p.description.isEmpty())
			{
				maxBoxY = 27D;
				maxBoxH = height - 52D;
			}

			double canvasW = maxBoxW;
			double canvasH = maxBoxH;

			double scale = Math.max(p.width / maxBoxW, p.height / maxBoxH);

			if (scale > 1D)
			{
				canvasW = p.width / scale;
				canvasH = p.height / scale;
			}

			double canvasX = maxBoxX + (maxBoxW - canvasW) / 2D;
			double canvasY = maxBoxY + (maxBoxH - canvasH) / 2D;

			for (TutorialLayer layer : p.layers)
			{
				if (layer instanceof HoverTextLayer)
				{
					double tw = layer.width / p.width * canvasW;
					double th = layer.height / p.height * canvasH;
					double tx = canvasX + (layer.posX == -1D ? ((p.width - layer.width) / 2D) : layer.posX) / p.width * canvasW;
					double ty = canvasY + (layer.posY == -1D ? ((p.height - layer.height) / 2D) : layer.posY) / p.height * canvasH;

					if (getMouseX() >= tx && getMouseY() >= ty && getMouseX() < tx + tw && getMouseY() < ty + th)
					{
						list.addAll(((HoverTextLayer) layer).text);
					}
				}
			}
		}
	}

	@Override
	public boolean onClosedByKey(int key)
	{
		if (super.onClosedByKey(key))
		{
			openYesNo(I18n.format("ftbtutorialmod.gui.exit"), "", this::closeGui);
		}

		return false;
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
				openYesNo("Exit tutorial?", "", this::closeGui);
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