package gcewing.lighting;

import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class TexturedItem extends Item implements ITextureProvider {

	public TexturedItem(int id) {
		super(id);
	}

	public String getTextureFile() {
		return GregsLighting.textureFile;
	}

}
