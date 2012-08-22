package gcewing.lighting;

import net.minecraft.src.TileEntity;
import net.minecraft.src.forge.*;
import net.minecraft.src.ic2.api.*;
import net.minecraft.src.GregsLighting;

public class TEFloodlightIC2 extends TileEntity implements IEnergySink {

	final int maxEnergy = 5;
	final int maxInput = 32;
	final int energyUsedPerTick = 1;
	
	int energy = 0;
	boolean addedToEnergyNet = false;
	
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		System.out.printf("TEFloodlightIC2.acceptsEnergyFrom\n");
		return true;
	}
	
	public boolean demandsEnergy() {
		//System.out.printf("TEFloodlightIC2.demandsEnergy\n");
    return energy < maxEnergy;
  }
		
	public int injectEnergy(Direction directionFrom, int amount) {
		//System.out.printf("TEFloodlightIC2.injectEnergy: %d\n", amount);
		boolean hadEnergy = energy > 0;
    int surplus = 0;
    energy += amount;
    if (energy > maxEnergy) {
      surplus = energy - maxEnergy;
      energy = maxEnergy;
    }
    if (hadEnergy != (energy > 0))
    	updateBlock();
    return surplus;
	}

	public boolean isAddedToEnergyNet() {
		//System.out.printf("TEFloodlightIC2.isAddedToEnergyNet\n");
    return addedToEnergyNet;
  }

	public void updateEntity() {
		if (!worldObj.isRemote) {
			if (!this.addedToEnergyNet) {
				//System.out.printf("TEFloodlightIC2.updateEntity: adding to energy net\n");
				EnergyNet.getForWorld(worldObj).addTileEntity(this);
				this.addedToEnergyNet = true;
			}
			if (isActive()) {
				//System.out.printf("TEFloodlightIC2.updateEntity: using %d energy from %d\n",
				//	energyUsedPerTick, energy);
				energy -= energyUsedPerTick;
				if (energy < 0)
					energy = 0;
				//onInventoryChanged();
				if (!isActive())
					updateBlock();
			}
		}
	}
	
	public void invalidate() {
		if (addedToEnergyNet) {
			//System.out.printf("TEFloodlightIC2.invalidate: removing from energy net\n");
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
			addedToEnergyNet = false;
		}
		super.invalidate();
	}

	void updateBlock() {
		GregsLighting.floodlightIC2.update(worldObj, xCoord, yCoord, zCoord);
	}
	
	public boolean isActive() {
		//System.out.printf("TEFloodlightIC2.isActive: energy = %d\n", energy);
		return energy > 0 && receivingRedstoneSignal();
	}
	
	boolean receivingRedstoneSignal() {
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
	
}
