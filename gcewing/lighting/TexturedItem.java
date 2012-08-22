package gcewing.lighting;

import forge.ITextureProvider;
import net.minecraft.server.Item;

public class TexturedItem extends Item implements ITextureProvider
{
    public TexturedItem(int var1)
    {
        super(var1);
    }

    public String getTextureFile()
    {
        return "/gcewing/lighting/resources/textures.png";
    }
}
