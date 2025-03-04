package dev.wolfieboy09.fluxnetworkcctweaked;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import sonar.fluxnetworks.common.device.TileFluxController;

public class FluxPeripheralProvider implements IPeripheralProvider {
    @NotNull
    @Override
    public LazyOptional<IPeripheral> getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
        if (level.getBlockEntity(blockPos) instanceof TileFluxController tile) {
            return LazyOptional.of(() -> new FluxPeripheral(tile.getNetwork()));
        }
        return LazyOptional.empty();
    }
}
