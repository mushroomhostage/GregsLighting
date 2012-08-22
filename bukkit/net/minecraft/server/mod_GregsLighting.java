package net.minecraft.server;

import gcewing.lighting.ContainerFloodlightCarbide;
import gcewing.lighting.TEFloodlightCarbide;
import java.io.File;

public class mod_GregsLighting extends GregsLighting
{
    public mod_GregsLighting()
    {
        super(true);
    }

    public void load()
    {
        super.load(new File("."));
    }

    public Object getGuiElement(int var1, EntityHuman var2, World var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var3.getTileEntity(var4, var5, var6);

        if (var7 != null)
        {
            switch (var1)
            {
                case 1:
                    return new ContainerFloodlightCarbide(var2.inventory, (TEFloodlightCarbide)var7);
            }
        }

        return null;
    }
}
