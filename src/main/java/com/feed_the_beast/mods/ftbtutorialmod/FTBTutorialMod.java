package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.kubejs.FTBTutorialModKubeJSIntegration;
import com.feed_the_beast.mods.ftbtutorialmod.net.FTBTutorialModNetHandler;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageOpenTutorial;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FTBTutorialMod.MOD_ID)
public class FTBTutorialMod
{
	public static final String MOD_ID = "ftbtutorialmod";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static FTBTutorialMod INSTANCE;

	public FTBTutorialMod() {
		INSTANCE = this;

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::clientSetup);
		bus.addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void openOnServer(ResourceLocation id, ServerPlayer player) {
		new MessageOpenTutorial(id).sendTo(player);
	}

	public void setup(FMLCommonSetupEvent event) {
		FTBTutorialModNetHandler.init();
		if (ModList.get().isLoaded("kubejs"))
		{
			MinecraftForge.EVENT_BUS.register(FTBTutorialModKubeJSIntegration.class);
		}
	}

	private void clientSetup(FMLClientSetupEvent event) {
		FTBTutorialModClient.setup();
	}

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal(MOD_ID).then(CommandOpenTutorial.register()));
	}
}