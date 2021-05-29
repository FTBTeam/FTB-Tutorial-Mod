package com.feed_the_beast.mods.ftbtutorialmod.kubejs;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import com.feed_the_beast.mods.ftbtutorialmod.net.MessageOpenTutorial;
import dev.latvian.kubejs.player.PlayerDataJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * @author LatvianModder
 */
public class FTBTutorialModPlayerData
{
	private final PlayerDataJS playerData;

	public FTBTutorialModPlayerData(PlayerDataJS p)
	{
		playerData = p;
	}

	public void openTutorial(ResourceLocation id)
	{
		Player player = playerData.getMinecraftPlayer();

		if (player instanceof ServerPlayer)
		{
			new MessageOpenTutorial(id).sendTo((ServerPlayer) player);
		}
		else
		{
			FTBTutorialModClient.open(id);
		}
	}
}