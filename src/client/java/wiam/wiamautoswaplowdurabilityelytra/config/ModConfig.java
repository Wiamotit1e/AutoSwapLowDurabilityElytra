package wiam.wiamautoswaplowdurabilityelytra.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import wiam.wiamautoswaplowdurabilityelytra.WiamModClient;

@Config(name = WiamModClient.MOD_ID)
public class ModConfig implements ConfigData {

    public int lowestDurabilityWhenSwap = 23;

    public boolean isAutoSwapOn = true;
}