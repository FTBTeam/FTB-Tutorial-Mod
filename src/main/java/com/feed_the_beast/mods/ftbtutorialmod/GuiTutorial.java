package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

/**
 * @author LatvianModder
 */
public class GuiTutorial extends BaseScreen
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
			GuiHelper.playSound(SoundEvents.UI_BUTTON_CLICK, 1);
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
	public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
		ResourceLocation background = Gui.GUI_ICONS_LOCATION;//.OPTIONS_BACKGROUND;

		if (tutorial.background != null)
		{
			background = tutorial.background;

			if (!tutorial.pages.isEmpty() && tutorial.pages.get(page).background != null)
			{
				background = tutorial.pages.get(page).background;
			}
		}

		RenderSystem.disableLighting();
		RenderSystem.disableFog();
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		Minecraft.getInstance().getTextureManager().bind(background);
		RenderSystem.color4f(1F, 1F, 1F, 1F);
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferbuilder.vertex(x, y + h, 0D).uv(0F, h / 32F).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex(x + w, y + h, 0D).uv(w / 32F, h / 32F).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex(x + w, y, 0D).uv(w / 32F, 0F).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex(x, y, 0D).uv(0F, 0F).color(64, 64, 64, 255).endVertex();
		tessellator.end();

		if (tutorial.pages.isEmpty())
		{
			return;
		}

		if (getMouseX() < 30)
		{
			Color4I.WHITE.withAlpha(50).draw(matrixStack, x, y, 30, h);
		}
		else if (getMouseX() > w - 30)
		{
			Color4I.WHITE.withAlpha(50).draw(matrixStack, x + w - 30, y, 30, h);
		}
	}

	@Override
	public void drawForeground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h)
	{
		if (tutorial.pages.isEmpty())
		{
			theme.drawString(matrixStack, new TranslatableComponent("ftbtutorialmod.gui.failed_to_load").withStyle(ChatFormatting.BOLD), x + w / 2f, y + h / 2f, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
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
			layer.draw(matrixStack, this, tx, ty, tw, th);

			if (Theme.renderDebugBoxes)
			{
				GuiHelper.drawHollowRect(matrixStack,(int) tx - 1, (int) ty - 1, (int) tw + 2, (int) th + 2, Color4I.GRAY.withAlpha(50), true);
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
			GuiHelper.drawHollowRect(matrixStack,(int) maxBoxX - 1, (int) maxBoxY - 1, (int) maxBoxW + 2, (int) maxBoxH + 2, Color4I.GRAY.withAlpha(50), true);
		}

		GuiHelper.drawHollowRect(matrixStack,(int) canvasX - 1, (int) canvasY - 1, (int) canvasW + 2, (int) canvasH + 2, col, false);

		if (!tutorial.title.isEmpty())
		{
			theme.drawString(matrixStack,ChatFormatting.BOLD + tutorial.title, x + w / 2, y + 10, Color4I.WHITE, Theme.CENTERED);
		}

		if (!p.description.isEmpty())
		{
			theme.drawString(matrixStack,p.description, x + w / 2, y + 23, Color4I.WHITE, Theme.CENTERED);
		}

		theme.drawString(matrixStack,(page + 1) + " / " + tutorial.pages.size(), x + w / 2, y + h - 17, Color4I.WHITE, Theme.CENTERED);

		theme.drawString(matrixStack,ChatFormatting.BOLD + "<", x + 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
		theme.drawString(matrixStack,ChatFormatting.BOLD + (page == tutorial.pages.size() - 1 ? "X" : ">"), x + w - 15, y + h / 2, Color4I.WHITE, Theme.CENTERED | Theme.CENTERED_V);
	}

	@Override
	public void addMouseOverText(TooltipList list) {
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
				if (layer instanceof HoverTextLayer || layer instanceof ButtonLayer)
				{
					double tw = layer.width / p.width * canvasW;
					double th = layer.height / p.height * canvasH;
					double tx = canvasX + (layer.posX == -1D ? ((p.width - layer.width) / 2D) : layer.posX) / p.width * canvasW;
					double ty = canvasY + (layer.posY == -1D ? ((p.height - layer.height) / 2D) : layer.posY) / p.height * canvasH;

					if (getMouseX() >= tx && getMouseY() >= ty && getMouseX() < tx + tw && getMouseY() < ty + th)
					{
						if (layer instanceof HoverTextLayer)
						{
							((HoverTextLayer) layer).text.forEach(e -> list.add(new TextComponent(e)));
						}
						else
						{
							((ButtonLayer) layer).hover.forEach(e -> list.add(new TextComponent(e)));
						}
					}
				}
			}
		}
	}

	@Override
	public boolean onClosedByKey(Key key) {
		if (super.onClosedByKey(key))
		{
			openYesNo(new TranslatableComponent("ftbtutorialmod.gui.exit"), TextComponent.EMPTY, this::closeGui);
		}

		return false;
	}

	@Override
	public void onClosed()
	{
		super.onClosed();

		if (!tutorial.postAction.isEmpty())
		{
			handleClick(tutorial.postAction);
		}
	}

	@Override
	public boolean keyPressed(Key key) {
		if (key.keyCode == GLFW.GLFW_KEY_LEFT || key.keyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
			page(false);
			return true;
		}
		else if (key.keyCode == GLFW.GLFW_KEY_RIGHT || key.keyCode == GLFW.GLFW_KEY_PAGE_UP) {
			page(true);
			return true;
		}

		return super.keyPressed(key);
	}

	@Override
	public boolean mousePressed(MouseButton button)
	{
		if (button.id == 3 || getMouseX() < 30)
		{
			if (page(false))
			{
				this.playClickSound();
			}

			return true;
		}
		else if (button.id == 4)
		{
			if (page(true))
			{
				this.playClickSound();
			}

			return true;
		}
		else if (getMouseX() > width - 30)
		{
			if (page == tutorial.pages.size() - 1)
			{
				this.playClickSound();
				openYesNo(new TextComponent("Exit tutorial?"), TextComponent.EMPTY, this::closeGui);
			}
			else if (page(true))
			{
				this.playClickSound();
			}

			return true;
		}

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
				if (layer instanceof ButtonLayer)
				{
					double tw = layer.width / p.width * canvasW;
					double th = layer.height / p.height * canvasH;
					double tx = canvasX + (layer.posX == -1D ? ((p.width - layer.width) / 2D) : layer.posX) / p.width * canvasW;
					double ty = canvasY + (layer.posY == -1D ? ((p.height - layer.height) / 2D) : layer.posY) / p.height * canvasH;

					if (getMouseX() >= tx && getMouseY() >= ty && getMouseX() < tx + tw && getMouseY() < ty + th)
					{
						String click = ((ButtonLayer) layer).click;

						if (click.equals("next_page"))
						{
							page(true);
						}
						else if (click.equals("previous_page"))
						{
							page(false);
						}
						else
						{
							handleClick(click);
						}

						this.playClickSound();
					}
				}
			}
		}

		return super.mousePressed(button);
	}

	@Override
	public boolean mouseScrolled(double scroll)
	{
		return page(scroll < 0);
	}
}