package dev.wolfieboy09.fluxnetworkcctweaked;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.neoforge.common.UsernameCache;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import sonar.fluxnetworks.FluxNetworks;
import sonar.fluxnetworks.common.connection.FluxNetwork;
import sonar.fluxnetworks.common.connection.NetworkStatistics;
import sonar.fluxnetworks.common.device.TileFluxController;
import sonar.fluxnetworks.common.device.TileFluxDevice;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FluxPeripheral implements GenericPeripheral {

    @LuaFunction(mainThread = true)
    public final long getEnergy(TileFluxController controller) {
        return controller.getNetwork().getStatistics().totalEnergy;
    }

    @LuaFunction(mainThread = true)
    public final long getEnergyCapacity(TileFluxController controller) {
        return controller.getNetwork().getLogicalDevices(FluxNetwork.STORAGE)
                .stream()
                .mapToLong(TileFluxDevice::getMaxTransferLimit)
                .sum();
    }

    @LuaFunction(mainThread = true)
    public final @Unmodifiable Map<String, ?> networkStats(TileFluxController controller) {
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
    public final String getNetworkName(TileFluxController controller) {
        return controller.getNetwork().getNetworkName();
    }

    @LuaFunction(mainThread = true)
    public final String getSecurityLevel(TileFluxController controller) {
        // Returns "Private", "Encrypted", or "Public"
        return controller.getNetwork().getSecurityLevel().getName();
    }

    @LuaFunction(mainThread = true)
    public final UUID getOwnerUuid(TileFluxController controller) {
        return controller.getNetwork().getOwnerUUID();
    }

    @LuaFunction(mainThread = true)
    public final @Nullable String getOwnerUsername(TileFluxController controller) {
        //                                                                 IntelliJ told me
        return UsernameCache.containsUUID(controller.getOwnerUUID()) ? Objects.requireNonNull(UsernameCache.getLastKnownUsername(controller.getOwnerUUID())) : null;
    }

    @Contract(pure = true)
    @Override
    public final String id() {
        return "flux_controller";
    }
}
