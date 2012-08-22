package gcewing.lighting;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergySink;
import net.minecraft.server.GregsLighting;
import net.minecraft.server.TileEntity;

public class TEFloodlightIC2 extends TileEntity implements IEnergySink
{
    final int maxEnergy = 5;
    final int maxInput = 32;
    final int energyUsedPerTick = 1;
    int energy = 0;
    boolean addedToEnergyNet = false;

    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        System.out.printf("TEFloodlightIC2.acceptsEnergyFrom\n", new Object[0]);
        return true;
    }

    public boolean demandsEnergy()
    {
        return this.energy < 5;
    }

    public int injectEnergy(Direction var1, int var2)
    {
        boolean var3 = this.energy > 0;
        int var4 = 0;
        this.energy += var2;

        if (this.energy > 5)
        {
            var4 = this.energy - 5;
            this.energy = 5;
        }

        if (var3 != this.energy > 0)
        {
            this.updateBlock();
        }

        return var4;
    }

    public boolean isAddedToEnergyNet()
    {
        return this.addedToEnergyNet;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        if (!this.world.isStatic)
        {
            if (!this.addedToEnergyNet)
            {
                EnergyNet.getForWorld(this.world).addTileEntity(this);
                this.addedToEnergyNet = true;
            }

            if (this.isActive())
            {
                --this.energy;

                if (this.energy < 0)
                {
                    this.energy = 0;
                }

                if (!this.isActive())
                {
                    this.updateBlock();
                }
            }
        }
    }

    /**
     * invalidates a tile entity
     */
    public void j()
    {
        if (this.addedToEnergyNet)
        {
            EnergyNet.getForWorld(this.world).removeTileEntity(this);
            this.addedToEnergyNet = false;
        }

        super.j();
    }

    void updateBlock()
    {
        GregsLighting.floodlightIC2.update(this.world, this.x, this.y, this.z);
    }

    public boolean isActive()
    {
        return this.energy > 0 && this.receivingRedstoneSignal();
    }

    boolean receivingRedstoneSignal()
    {
        return this.world.isBlockIndirectlyPowered(this.x, this.y, this.z);
    }
}
