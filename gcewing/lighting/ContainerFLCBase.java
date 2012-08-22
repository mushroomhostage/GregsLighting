package gcewing.lighting;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.IInventory;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;
import net.minecraft.server.EntityHuman;

public class ContainerFLCBase extends Container
{
    TEFloodlightCarbide te;
    private EntityHuman player;

    public EntityHuman getPlayer()
    {
        return player;
    }

    public IInventory getInventory()
    {
        return te;
    }

    public ContainerFLCBase(PlayerInventory var1, TEFloodlightCarbide var2)
    {
        this.player = var1.player;
        this.te = var2;
        this.a(new Slot(var2, 0, 44, 8));
        this.a(new Slot(var2, 1, 116, 60));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.a(new Slot(var1, var3, 8 + var3 * 18, 142));
        }
    }

    public boolean b(EntityHuman var1)
    {
        return this.te.a(var1);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack a(int var1)
    {
        return null;
    }
}
