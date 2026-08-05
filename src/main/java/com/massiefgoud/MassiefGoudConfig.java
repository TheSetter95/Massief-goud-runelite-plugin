package com.massiefgoud;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("massiefgoud")
public interface MassiefGoudConfig extends Config
{
	@ConfigItem(
		keyName = "apiKey",
		name = "Plugin-sleutel",
		description = "Je persoonlijke sleutel van de Massief Goud website (Profiel -> RuneLite-plugin koppelen)"
	)
	default String apiKey()
	{
		return "";
	}

	@ConfigItem(
		keyName = "apiUrl",
		name = "Website URL",
		description = "De URL van de Massief Goud website (laat op de standaard staan tenzij je zelf host)"
	)
	default String apiUrl()
	{
		return "https://massiefgoud.vercel.app";
	}
}
