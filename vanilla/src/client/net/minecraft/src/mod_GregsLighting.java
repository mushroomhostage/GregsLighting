//------------------------------------------------------
//
//   Greg's Lighting Mod - Client
//
//------------------------------------------------------

package net.minecraft.src;

import java.io.File;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.src.forge.MinecraftForgeClient;
//import net.minecraft.src.forge.IItemRenderer;
import gcewing.lighting.*;

public class mod_GregsLighting extends GregsLighting {

	public static Minecraft minecraft;
	public static File mcdir;
		
	public mod_GregsLighting() {
		super(false);
	}

	public void load() {
		System.out.printf("mod_GregsLighting: load()\n");
		minecraft = ModLoader.getMinecraftInstance();
		mcdir = Minecraft.getMinecraftDir();
		loadTextures();
		super.load(mcdir);
	}
	
	void loadTextures() {
		System.out.printf("mod_GregsLighting: loadTextures()\n");
		MinecraftForgeClient.preloadTexture(textureFile);
	}

	/**
	 * Returns a Container to be displayed to the user. 
	 * On the client side, this needs to return a instance of GuiScreen
	 * On the server side, this needs to return a instance of Container
	 *
	 * @param ID The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @return A GuiScreen/Container to be displayed to the user, null if none.
	 */
	public Object getGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		//System.out.printf("mod_GregsLighting[client].getGuiElement: %d\n", id);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null) {
			//System.out.printf("mod_GregsLighting.getGuiElement: have te\n");
			switch (id) {
				case guiFloodlightCarbide:
					return new GuiFloodlightCarbide(player.inventory, (TEFloodlightCarbide)te);
			}
		}
		return null;
	}

	public boolean shiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	
}
