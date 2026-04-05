package me.globalastral.newindustry.utils;

import net.minecraftforge.energy.EnergyStorage;

public abstract class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int delta = super.receiveEnergy(maxReceive, simulate);
        if (delta != 0)
            onEnergyChanged();
        return delta;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int delta = super.extractEnergy(maxExtract, simulate);
        if (delta != 0)
            onEnergyChanged();
        return delta;
    }

    public int setEnergy(int energy) {
        boolean callback_needed = this.energy != energy;
        this.energy = energy;
        if (callback_needed)
            onEnergyChanged();
        return energy;
    }

    public abstract void onEnergyChanged();
}
