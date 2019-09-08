package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.ftblib.lib.command.CmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

/**
 * @author LatvianModder
 */
public class CommandOpenTutorial extends CmdBase
{
	public CommandOpenTutorial()
	{
		super("open_tutorial", Level.OP_OR_SP);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 1;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		checkArgs(sender, args, 2);
		FTBTutorialMod.INSTANCE.openOnServer(new ResourceLocation(args[0]), getPlayer(server, sender, args[1]));
	}
}