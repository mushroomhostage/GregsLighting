package gcewing.lighting;

import net.minecraft.server.Block;
import net.minecraft.server.GregsLighting;
import net.minecraft.server.World;

public class Floodlight
{
    static boolean activeAt(World var0, int var1, int var2, int var3)
    {
        for (int var4 = var0.getHeight(); var2 < var4; ++var2)
        {
            int var5 = var0.getTypeId(var1, var2, var3);

            if (isFloodlightBlockID(var5))
            {
                BlockFloodlight var6 = (BlockFloodlight)Block.byId[var5];
                return var6.isActive(var0, var1, var2, var3);
            }

            if (var5 == GregsLighting.floodlightBeam.id)
            {
                return true;
            }

            if (var5 == 0 || Block.byId[var5].a())
            {
                return false;
            }
        }

        return false;
    }

    static boolean isFloodlightBlockID(int var0)
    {
        return var0 == GregsLighting.floodlight.id || var0 == GregsLighting.floodlightCarbide.id || GregsLighting.floodlightIC2 != null && var0 == GregsLighting.floodlightIC2.id;
    }

    static void projectBeam(World var0, int var1, int var2, int var3)
    {
        traceBeam(var0, var1, var2, var3, 0, GregsLighting.floodlightBeam.id);
    }

    static void removeBeam(World var0, int var1, int var2, int var3)
    {
        traceBeam(var0, var1, var2, var3, GregsLighting.floodlightBeam.id, 0);
    }

    static void traceBeam(World var0, int var1, int var2, int var3, int var4, int var5)
    {
        for (; var2 >= 0; --var2)
        {
            int var6 = var0.getTypeId(var1, var2, var3);

            if (var6 == var4)
            {
                var0.setTypeId(var1, var2, var3, var5);
            }
            else if (var6 != 0 && Block.byId[var6].a())
            {
                return;
            }
        }
    }
}
