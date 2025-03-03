package dev.wolfieboy09.fluxnetworkcctweaked;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sonar.fluxnetworks.common.connection.FluxNetwork;
import sonar.fluxnetworks.common.connection.NetworkStatistics;
import sonar.fluxnetworks.common.device.FluxControllerHandler;

import java.util.Map;

public record FluxPeripheral(FluxControllerHandler handler, FluxNetwork network) implements IPeripheral {
    @Override
    public @NotNull String getType() {
        return "flux_controller";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return this == iPeripheral;
    }

    @LuaFunction(mainThread = true)
    public long getEnergy() {
        return this.handler.getBuffer();
    }

    @LuaFunction(mainThread = true)
    public long getEnergyCapacity() {
        return Math.max(this.handler.getBuffer(), this.handler.getLimit());
    }

    @LuaFunction(mainThread = true)
    public Map<String, ?> networkStats() {
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
