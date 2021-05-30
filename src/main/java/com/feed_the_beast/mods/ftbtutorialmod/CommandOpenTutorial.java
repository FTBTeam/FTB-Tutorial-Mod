package com.feed_the_beast.mods.ftbtutorialmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

/**
 * @author LatvianModder
 */
public class CommandOpenTutorial {
	public static ArgumentBuilder<CommandSourceStack, ?> register() {
		return Commands.literal("open_tutorial")
				.then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("resource", StringArgumentType.word()).executes(CommandOpenTutorial::execute)));
	}

	private static int execute(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
		Collection<ServerPlayer> players = EntityArgument.getPlayers(commandContext, "targets");
		String resource = StringArgumentType.getString(commandContext, "resource");

		players.forEach(e -> FTBTutorialMod.INSTANCE.openOnServer(new ResourceLocation(resource), e));

		return 0;
	}
}