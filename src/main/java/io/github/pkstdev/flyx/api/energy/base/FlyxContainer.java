package io.github.pkstdev.flyx.api.energy.base;

import io.github.pkstdev.flyx.api.energy.instance.FlyxContainerInstance;

public class FlyxContainer {
    private final double maxAmount;
    public double currentAmount = 0;
    private final double maxInsert;
    private final double maxExtract;

    protected FlyxContainer(double maxAmount, double maxInsert, double maxExtract) {
        this.maxAmount = maxAmount;
        this.maxInsert = maxInsert;
        this.maxExtract = maxExtract;
    }

    public static FlyxContainer createInstance(double maxAmount, double maxInsert, double maxExtract) {
        return new FlyxContainer(maxAmount, maxInsert, maxExtract);
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public double getMaxInsert() {
        return maxInsert;
    }

    public double getMaxExtract() {
        return maxExtract;
    }

    public void setCurrentAmount(double amount) {
        currentAmount = Math.max(Math.min(amount, this.getMaxAmount()), 0);
    }

    public void addToCurrentAmount(double amount) {
        currentAmount = Math.max(Math.min(this.getCurrentAmount() + amount, this.getMaxAmount()), 0);
    }

    public void takeFromCurrentAmount(double amount) {
        this.addToCurrentAmount(-amount);
    }

    public boolean push(FlyxContainerInstance source, FlyxContainerInstance target, double amount) {
        boolean success = false;
        double sourceMaxAmount = source.getEnergyContainer().getMaxAmount();
        double sourceCurrentAmount = source.getEnergyContainer().getCurrentAmount();
        double sourceMaxExtract = source.getEnergyContainer().getMaxExtract();
        double targetMaxAmount = target.getEnergyContainer().getMaxAmount();
        double targetCurrentAmount = target.getEnergyContainer().getCurrentAmount();
        double targetMaxInsert = target.getEnergyContainer().getMaxInsert();
        if ((sourceMaxAmount - sourceCurrentAmount) >= amount && targetCurrentAmount >= targetMaxAmount) {
            double actualExtract = Math.min(Math.min(sourceMaxExtract, targetMaxInsert), targetMaxAmount - targetCurrentAmount);
            source.getEnergyContainer().takeFromCurrentAmount(actualExtract);
            target.getEnergyContainer().addToCurrentAmount(actualExtract);
            success = true;
        }
        return success;
    }

    public void tick() {
        if (this.getCurrentAmount() > this.getMaxAmount()) currentAmount = this.getMaxAmount();
        if (this.getCurrentAmount() < 0) currentAmount = 0;
    }
}
