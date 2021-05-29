package com.feed_the_beast.mods.ftbtutorialmod;

import com.feed_the_beast.mods.ftbtutorialmod.data.Tutorial;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;


public class FTBTutorialModReloadListener implements ISelectiveResourceReloadListener {
	@Override
	public void onResourceManagerReload(ResourceManager resourceManager, Predicate<IResourceType> predicate) {
		Tutorial.tutorials.clear();
		Tutorial.visibleTutorials = null;
	}
}