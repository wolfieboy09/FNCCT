package dev.wolfieboy09.fluxnetworkcctweaked;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import sonar.fluxnetworks.common.connection.FluxNetwork;
import sonar.fluxnetworks.common.connection.NetworkStatistics;
import sonar.fluxnetworks.common.device.FluxControllerHandler;
import sonar.fluxnetworks.common.device.TileFluxDevice;

import java.util.Map;

// I hate it as well.
// https://github.com/cc-tweaked/CC-Tweaked/issues/2129
// Look at that
@SuppressWarnings("unused")
public class FluxPeripheral implements IPeripheral {
    private final FluxNetwork network;

    public FluxPeripheral(FluxNetwork network) {
        this.network = network;
    }

    @Override
    public @NotNull String getType() {
        return "flux_controller";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return this == iPeripheral;
    }

    @LuaFunction(mainThread = true)
    public final long getEnergy() {
        return this.network.getStatistics().totalEnergy;
    }

    @LuaFunction(mainThread = true)
    public final long getEnergyCapacity() {
        return this.network.getLogicalDevices(FluxNetwork.STORAGE)
                .stream()
                .mapToLong(TileFluxDevice::getMaxTransferLimit)
                .sum();
    }

    @LuaFunction(mainThread = true)
    public final @NotNull @Unmodifiable Map<String, ?> networkStats() {
        NetworkStatistics stats = this.network.getStatistics();
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
}
