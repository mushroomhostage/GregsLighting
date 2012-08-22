package gcewing.lighting;

import net.minecraft.server.ICrafting;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.EntityHuman;

public class ContainerFloodlightCarbide extends ContainerFLCBase
{
    public ContainerFloodlightCarbide(PlayerInventory var1, TEFloodlightCarbide var2)
    {
        super(var1, var2);
    }

    public void addSlotListener(ICrafting var1)
    {
        super.addSlotListener(var1);
        this.sendStateTo(var1);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void a()
    {
        super.a();

        for (int var1 = 0; var1 < this.listeners.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.listeners.get(var1);
            this.sendStateTo(var2);
        }
    }

    void sendStateTo(ICrafting var1)
    {
        var1.setContainerData(this, 0, this.te.waterLevel);
        var1.setContainerData(this, 1, this.te.carbideLevel);
    }
}
