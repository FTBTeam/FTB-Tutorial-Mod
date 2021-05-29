package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialMod;
import dev.ftb.mods.ftblibrary.net.snm.PacketID;
import dev.ftb.mods.ftblibrary.net.snm.SimpleNetworkManager;

/**
 * @author LatvianModder
 */
public interface FTBTutorialModNetHandler {
	SimpleNetworkManager NET = SimpleNetworkManager.create(FTBTutorialMod.MOD_ID);

	PacketID OPEN_TUTORIAL = NET.registerS2C("open_tutorial", MessageOpenTutorial::new);

	static void init() {
	}
}