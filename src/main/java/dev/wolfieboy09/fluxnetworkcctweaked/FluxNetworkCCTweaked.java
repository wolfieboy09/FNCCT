package dev.wolfieboy09.fluxnetworkcctweaked;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(FluxNetworkCCTweaked.MOD_ID)
public class FluxNetworkCCTweaked {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "fncct";

    public FluxNetworkCCTweaked() {
        ForgeComputerCraftAPI.registerPeripheralProvider(new FluxPeripheralProvider());
        LOGGER.info("Flux Computers when");
    }
}
