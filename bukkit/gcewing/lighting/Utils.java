package gcewing.lighting;

import java.util.Random;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class Utils
{
    public static Random random = new Random();

    public static void dumpInventoryIntoWorld(World var0, int var1, int var2, int var3)
    {
        IInventory var4 = (IInventory)var0.getTileEntity(var1, var2, var3);

        if (var4 != null)
        {
            for (int var5 = 0; var5 < var4.getSize(); ++var5)
            {
                ItemStack var6 = var4.getItem(var5);

                if (var6 != null)
                {
                    float var7 = random.nextFloat() * 0.8F + 0.1F;
                    float var8 = random.nextFloat() * 0.8F + 0.1F;
                    float var9 = random.nextFloat() * 0.8F + 0.1F;

                    while (var6.count > 0)
                    {
                        int var10 = random.nextInt(21) + 10;

                        if (var10 > var6.count)
                        {
                            var10 = var6.count;
                        }

                        var6.count -= var10;
                        EntityItem var11 = new EntityItem(var0, (double)((float)var1 + var7), (double)((float)var2 + var8), (double)((float)var3 + var9), new ItemStack(var6.id, var10, var6.getData()));

                        if (var6.hasTag())
                        {
                            var11.itemStack.setTag((NBTTagCompound)var6.getTag().clone());
                        }

                        float var12 = 0.05F;
                        var11.motX = random.nextGaussian() * (double)var12;
                        var11.motY = random.nextGaussian() * (double)var12 + 0.20000000298023224D;
                        var11.motZ = random.nextGaussian() * (double)var12;
                        var0.addEntity(var11);
                    }
                }
            }
        }
    }
}
