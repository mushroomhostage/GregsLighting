package gcewing.lighting;

import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;

public class Water
{
    public static boolean isWaterItem(ItemStack var0)
    {
        if (var0 != null)
        {
            Item var1 = var0.getItem();

            if (var1 == Item.WATER_BUCKET)
            {
                return true;
            }

            String var2 = var1.getName();

            if (var2.equals("item.itemCellWater"))
            {
                return true;
            }

            if (var2.equals("item.waterCan"))
            {
                return true;
            }

            if (var2.equals("item.waxCapsuleWater"))
            {
                return true;
            }

            if (var2.equals("item.refractoryWater"))
            {
                return true;
            }

            System.out.printf("Water.isWaterItem: Unknown item \'%s\'\n", new Object[] {var2});
        }

        return false;
    }

    public static ItemStack emptyContainerFor(ItemStack var0)
    {
        Item var1 = var0.getItem();
        Item var2 = var1.j();
        return var2 != null ? new ItemStack(var2, 1) : null;
    }
}
