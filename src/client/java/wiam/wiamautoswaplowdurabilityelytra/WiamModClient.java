package wiam.wiamautoswaplowdurabilityelytra;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;

public class WiamModClient implements ClientModInitializer {


	public static final String MOD_ID = "wiamautoswaplowdurabilityelytra";


	@Override
	public void onInitializeClient() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
	}
}