package io.github.pkstdev.flyx.api.energy.instance;

import io.github.pkstdev.flyx.api.energy.base.FlyxContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FlyxContainerInstance extends BlockEntity {
    private double maxAmount;
    private double maxInsert;
    private double maxExtract;
    private final FlyxContainer energyContainer = FlyxContainer.createInstance(maxAmount, maxInsert, maxExtract);

    public FlyxContainerInstance(BlockEntityType<?> type, BlockPos pos, BlockState state, double maxAmount, double maxInsert, double maxExtract) {
        super(type, pos, state);
        this.maxAmount = maxAmount;
        this.maxInsert = maxInsert;
        this.maxExtract = maxExtract;
    }

    public FlyxContainer getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putDouble("flyx_amount", energyContainer.getCurrentAmount());
        super.writeNbt(tag);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        energyContainer.currentAmount = tag.getInt("flyx_amount");
    }

    public static void tick(World world, BlockPos pos, BlockState state, FlyxContainerInstance be) {
        be.energyContainer.tick();
    }
}
