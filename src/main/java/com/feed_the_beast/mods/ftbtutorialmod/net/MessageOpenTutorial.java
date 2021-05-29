package com.feed_the_beast.mods.ftbtutorialmod.net;

import com.feed_the_beast.mods.ftbtutorialmod.FTBTutorialModClient;
import dev.ftb.mods.ftblibrary.net.snm.BaseS2CPacket;
import dev.ftb.mods.ftblibrary.net.snm.PacketID;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * @author LatvianModder
 */
public class MessageOpenTutorial extends BaseS2CPacket {
	private final ResourceLocation tutorial;

	public MessageOpenTutorial(ResourceLocation id) {
		tutorial = id;
	}

	public MessageOpenTutorial(FriendlyByteBuf buf) {
		tutorial = buf.readResourceLocation();
	}

	@Override
	public PacketID getId() {
		return FTBTutorialModNetHandler.OPEN_TUTORIAL;
	}

	@Override
	public void write(FriendlyByteBuf friendlyByteBuf) {
		friendlyByteBuf.writeResourceLocation(tutorial);
	}

	@Override
	public void handle(NetworkManager.PacketContext packetContext) {
		FTBTutorialModClient.open(tutorial);
	}
}