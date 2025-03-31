package wiam.wiamautoswaplowdurabilityelytra;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import wiam.wiamautoswaplowdurabilityelytra.config.ModConfig;

public class WiamModClient implements ClientModInitializer {
	public static final String MOD_ID = "wiamautoswaplowdurabilityelytra";


	@Override
	public void onInitializeClient() {
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}