package gcewing.lighting;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.GregsLighting;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockFloodlightCarbide extends BlockFloodlight
{
    public BlockFloodlightCarbide(int var1)
    {
        super(var1, 16, Material.STONE);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        if (!var5.isSneaking())
        {
            if (!var1.isStatic)
            {
                GregsLighting.openGuiFloodlightCarbide(var1, var2, var3, var4, var5);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        return new TEFloodlightCarbide();
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        Utils.dumpInventoryIntoWorld(var1, var2, var3, var4);
        super.remove(var1, var2, var3, var4);
    }

    public boolean isActive(World var1, int var2, int var3, int var4)
    {
        TEFloodlightCarbide var5 = (TEFloodlightCarbide)var1.getTileEntity(var2, var3, var4);
        return var5 != null && var5.isActive();
    }

    public void update(World var1, int var2, int var3, int var4)
    {
        if (!var1.isStatic)
        {
            TEFloodlightCarbide var5 = (TEFloodlightCarbide)var1.getTileEntity(var2, var3, var4);

            if (var5 != null)
            {
                var5.refillCarbide();
            }

            super.update(var1, var2, var3, var4);
        }
    }
}
