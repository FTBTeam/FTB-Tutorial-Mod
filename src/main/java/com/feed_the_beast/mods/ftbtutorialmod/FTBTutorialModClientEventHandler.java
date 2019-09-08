package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.events.client.CustomClickEvent;
import com.feed_the_beast.ftblib.lib.gui.GuiHelper;
import com.feed_the_beast.mods.ftbtutorialmod.data.Overlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBTutorialMod.MOD_ID, value = Side.CLIENT)
public class FTBTutorialModClientEventHandler
{
	@SubscribeEvent
	public static void onCustomClick(CustomClickEvent event)
	{
		if (event.getID().getNamespace().equals(FTBTutorialMod.MOD_ID))
		{
			if (event.getID().getPath().equals("list"))
			{
				FTBTutorialMod.INSTANCE.openListOnClient();
			}
			else if (event.getID().getPath().startsWith("open:"))
			{
				FTBTutorialMod.INSTANCE.openOnClient(new ResourceLocation(event.getID().getPath().substring(5)));
			}

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onConnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		FTBTutorialModClient.activeOverlays.clear();
	}

	@SubscribeEvent
	public static void onScreenDraw(RenderGameOverlayEvent.Post event)
	{
		if (FTBTutorialModClient.activeOverlays.isEmpty() || event.getType() != RenderGameOverlayEvent.ElementType.ALL)
		{
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();

		if (mc.gameSettings.showDebugInfo || mc.currentScreen != null)
		{
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 800D);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();

		int maxWidth = event.getResolution().getScaledWidth() / 4;
		int p = 4;
		int spx = p;
		int spy = p;
		int l = 10;

		for (Overlay o : FTBTutorialModClient.activeOverlays.values())
		{
			List<String> list = new ArrayList<>();

			for (ITextComponent t : o.text)
			{
				list.addAll(mc.fontRenderer.listFormattedStringToWidth(t.getFormattedText(), maxWidth));
			}

			int mw = 0;

			for (String s : list)
			{
				mw = Math.max(mw, mc.fontRenderer.getStringWidth(s));
			}

			if (mw == 0)
			{
				return;
			}

			o.color.withAlpha(200).draw(spx, spy, mw + p * 2, list.size() * l + p * 2 - 2);
			GuiHelper.drawHollowRect(spx, spy, mw + p * 2, list.size() * l + p * 2 - 2, o.color, false);

			for (int i = 0; i < list.size(); i++)
			{
				mc.fontRenderer.drawStringWithShadow(list.get(i), spx + p, spy + i * l + p, 0xFFFFFFFF);
			}

			spy += list.size() * l + p * 2 + (p - 2);
		}

		GlStateManager.popMatrix();
	}
}