package net.goosehub.useless;

import net.fabricmc.api.ModInitializer;

import net.goosehub.useless.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UselessStuff implements ModInitializer {
	public static final String MOD_ID = "useless-stuff";
	public static final Logger LOGGER = LoggerFactory.getLogger("UselessStuff");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}