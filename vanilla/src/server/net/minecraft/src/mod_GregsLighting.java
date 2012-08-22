//------------------------------------------------------
//
//   Greg's Lighting Mod - Server
//
//------------------------------------------------------

package net.minecraft.src;

import java.io.File;
import gcewing.lighting.*;

public class mod_GregsLighting extends GregsLighting {

	public mod_GregsLighting() {
		super(true);
	}

	public void load() {
		super.load(new File("."));
	}
	
//	public void onPacket250Received(EntityPlayer player, Packet250CustomPayload pkt) {
//	}

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
		//System.out.printf("mod_GregsLighting[server].getGuiElement %d\n", id);
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null) {
			//System.out.printf("mod_GregsLighting.getGuiElement: have TE\n");
			switch (id) {
				case guiFloodlightCarbide:
					//System.out.printf("mod_GregsLighting.getGuiElement: creating ContainerFloodlightCarbide\n");
					return new ContainerFloodlightCarbide(player.inventory, (TEFloodlightCarbide)te);
			}
		}
		return null;
	}

}
