package dev.wolfieboy09.fluxnetworkcctweaked;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import sonar.fluxnetworks.FluxNetworks;
import sonar.fluxnetworks.api.network.SecurityLevel;
import sonar.fluxnetworks.common.connection.FluxNetwork;
import sonar.fluxnetworks.common.connection.NetworkStatistics;
import sonar.fluxnetworks.common.device.TileFluxController;
import sonar.fluxnetworks.common.device.TileFluxDevice;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
public class FluxPeripheral implements GenericPeripheral {

    @LuaFunction(mainThread = true)
    public final long getEnergy(@NotNull TileFluxController controller) {
        return controller.getNetwork().getStatistics().totalEnergy;
    }

    @LuaFunction(mainThread = true)
    public final long getEnergyCapacity(@NotNull TileFluxController controller) {
        return controller.getNetwork().getLogicalDevices(FluxNetwork.STORAGE)
                .stream()
                .mapToLong(TileFluxDevice::getMaxTransferLimit)
                .sum();
    }

    @LuaFunction(mainThread = true)
    public final @NotNull @Unmodifiable Map<String, ?> networkStats(@NotNull TileFluxController controller) {
        NetworkStatistics stats = controller.getNetwork().getStatistics();
        return Map.of(
                "controllerCount", stats.fluxControllerCount,
                "pointCount", stats.fluxPointCount,
                "plugCount", stats.fluxPlugCount,
                "storageCount", stats.fluxStorageCount,
                "totalBuffer", stats.totalBuffer,
                "totalEnergy", stats.totalEnergy,
                "energyInput", stats.energyInput,
                "energyOutput", stats.energyOutput,
                "averageTick", stats.averageTickMicro,
                "connectionCount", stats.getConnectionCount()
        );
    }

    @LuaFunction(mainThread = true)
    public final @NotNull String getNetworkName(@NotNull TileFluxController controller) {
        return controller.getNetwork().getNetworkName();
    }

    @LuaFunction(mainThread = true)
    public final @NotNull String getSecurityLevel(@NotNull TileFluxController controller) {
        // Returns "Private", "Encrypted", or "Public"
        return controller.getNetwork().getSecurityLevel().getName();
    }

    @LuaFunction(mainThread = true)
    public final @NotNull UUID getOwnerUuid(@NotNull TileFluxController controller) {
        return controller.getNetwork().getOwnerUUID();
    }

//    @LuaFunction(mainThread = true)
//    public final @NotNull String getOwnerUsername(@NotNull TileFluxController controller) {
//        return "somehow get the username from UUID";
//    }

    @Contract(pure = true)
    @Override
    public final @NotNull String id() {
        return FluxNetworks.MODID + ":" + "flux_controller";
    }
}
