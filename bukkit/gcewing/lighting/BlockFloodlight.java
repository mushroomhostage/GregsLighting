package gcewing.lighting;

import forge.ITextureProvider;
import java.util.ArrayList;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockFloodlight extends BlockContainer implements ITextureProvider
{
    public BlockFloodlight(int var1)
    {
        super(var1, 0, Material.STONE);
    }

    public BlockFloodlight(int var1, int var2, Material var3)
    {
        super(var1, var2, var3);
    }

    public String getTextureFile()
    {
        return "/gcewing/lighting/resources/textures.png";
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int a(int var1, int var2)
    {
        int var3 = this.textureId;

        if (var2 > 0)
        {
            var3 += 3;
        }

        switch (var1)
        {
            case 0:
                return var3;

            case 1:
                return var3 + 2;

            default:
                return var3 + 1;
        }
    }

    public void addCreativeItems(ArrayList var1)
    {
        var1.add(new ItemStack(this, 1));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World var1, int var2, int var3, int var4)
    {
        this.update(var1, var2, var3, var4);
    }

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        this.update(var1, var2, var3, var4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        this.update(var1, var2, var3, var4);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        return null;
    }

    public void update(World var1, int var2, int var3, int var4)
    {
        if (!var1.isStatic)
        {
            boolean var5 = Floodlight.activeAt(var1, var2, var3, var4);
            this.setIlluminated(var1, var2, var3, var4, var5);

            if (var5)
            {
                Floodlight.projectBeam(var1, var2, var3 - 1, var4);
            }
            else
            {
                Floodlight.removeBeam(var1, var2, var3 - 1, var4);
            }
        }
    }

    public void setIlluminated(World var1, int var2, int var3, int var4, boolean var5)
    {
        int var6 = var1.getData(var2, var3, var4);
        int var7 = var6 & 14 | (var5 ? 1 : 0);

        if (var6 != var7)
        {
            var1.setRawData(var2, var3, var4, var7);
            var1.notify(var2, var3, var4);
        }
    }

    public boolean isActive(World var1, int var2, int var3, int var4)
    {
        return var1.isBlockIndirectlyPowered(var2, var3, var4);
    }
}
