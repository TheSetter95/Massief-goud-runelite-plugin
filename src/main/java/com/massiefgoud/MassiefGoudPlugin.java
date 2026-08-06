package com.massiefgoud;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
@PluginDescriptor(
	name = "Massief Goud",
	description = "Meldt automatisch item-drops aan het Massief Goud Ganzebord-systeem, per team",
	tags = {"massief goud", "ganzebord", "bingo", "osrs", "clan"}
)
public class MassiefGoudPlugin extends Plugin
{
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@Inject
	private MassiefGoudConfig config;

	@Inject
	private OkHttpClient httpClient;

	@Inject
	private ItemManager itemManager;

	@Inject
	private Gson gson;

	@Provides
	MassiefGoudConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MassiefGoudConfig.class);
	}

	@Override
	protected void startUp()
	{
		log.info("Massief Goud-plugin gestart");
	}

	@Override
	protected void shutDown()
	{
		log.info("Massief Goud-plugin gestopt");
	}

	@Subscribe
	public void onLootReceived(LootReceived lootReceived)
	{
		if (config.apiKey() == null || config.apiKey().trim().isEmpty())
		{
			// Nog geen plugin-sleutel ingevuld -> niets te melden
			return;
		}

		lootReceived.getItems().forEach(itemStack ->
		{
			String itemName = itemManager.getItemComposition(itemStack.getId()).getName();
			reportDrop(itemStack.getId(), itemName, itemStack.getQuantity());
		});
	}

	private void reportDrop(int itemId, String itemName, int quantity)
	{
		JsonObject payload = new JsonObject();
		payload.addProperty("itemId", itemId);
		payload.addProperty("itemName", itemName);
		payload.addProperty("quantity", quantity);

		String baseUrl = (config.apiUrl() == null || config.apiUrl().trim().isEmpty())
			? "https://massiefgoud.vercel.app"
			: config.apiUrl().trim();

		RequestBody body = RequestBody.create(JSON, gson.toJson(payload));

		Request request = new Request.Builder()
			.url(baseUrl + "/api/plugin/report-drop")
			.header("Authorization", "Bearer " + config.apiKey().trim())
			.post(body)
			.build();

		httpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				log.warn("Massief Goud: melden van drop mislukt", e);
			}

			@Override
			public void onResponse(Call call, Response response)
			{
				if (!response.isSuccessful())
				{
					log.warn("Massief Goud: server antwoordde met status {}", response.code());
				}
				response.close();
			}
		});
	}
}
