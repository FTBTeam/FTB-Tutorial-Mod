package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.FTBLib;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = FTBTutorialMod.MOD_ID,
		name = FTBTutorialMod.MOD_NAME,
		version = FTBTutorialMod.VERSION,
		acceptableRemoteVersions = "*",
		dependencies = FTBLib.THIS_DEP
)
public class FTBTutorialMod
{
	public static final String MOD_ID = "ftbtutorialmod";
	public static final String MOD_NAME = "FTB Tutorial Mod";
	public static final String VERSION = "0.0.0.ftbtutorialmod";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	@SidedProxy(serverSide = "com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModCommon", clientSide = "com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient")
	public static FTBTutorialModCommon PROXY;

	public static void openOnServer(ResourceLocation id, EntityPlayerMP player)
	{
		new MessageOpenTutorial(id).sendTo(player);
	}

	public static void openOnClient(ResourceLocation id)
	{
		PROXY.open(id);
	}

	public static void openListOnClient()
	{
		PROXY.openList();
	}

	@Mod.EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		PROXY.postInit();
		MessageOpenTutorial.init();
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandOpenTutorial());
	}
}