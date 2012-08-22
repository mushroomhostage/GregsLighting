//------------------------------------------------------
//
//   Greg's Prospecting Mod - Common
//
//------------------------------------------------------

package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Hashtable;
import net.minecraft.src.forge.*;
import gcewing.lighting.*;

public abstract class GregsLighting extends NetworkMod implements IGuiHandler {

	public static String modName = "GregsLighting";
	public static String channelName = "gce.lighting";
	public static String version = "1.2";
	public final static String textureDir = "/gcewing/lighting/resources/";
	public final static String textureFile = textureDir + "textures.png";
	
	public final static int guiFloodlightCarbide = 1;

	public static BlockFloodlightIC2 floodlightIC2;
	public static BlockFloodlightCarbide floodlightCarbide;
	public static BlockFloodlight floodlight;
	public static BlockFloodlightBeam floodlightBeam;

	public static Item calciumCarbide;
	public static Item bonemealAndCharcoal;
	public static Item gaslightMantle;
	public static Item glowstoneBulb;
	
	static String configName = modName + ".cfg";
	static File cfgfile;
	static OrderedProperties config;
	static Map <Integer, String> idToName = new Hashtable <Integer, String>();
	
	public static boolean isServer;
	public static GregsLighting mod;
	
	GregsLighting(boolean server) {
		mod = this;
		isServer = server;
	}
	
	public boolean clientSideRequired() {
		return true;
	}
	
	public boolean serverSideRequired() {
		return true;
	}

	public void load(File mcdir) {
		System.out.printf("GregsLighting: load()\n");
		//showLoadedMods();
		MinecraftForge.setGuiHandler(this, this);
		loadConfig(mcdir);
		registerBlocks();
		registerTileEntities();
		registerItems();
		addRecipes();
		registerChannels();
		registerIC2();
		saveConfig();
	}
	
//	void showLoadedMods() {
//		for (Object mod : ModLoader.getLoadedMods()) {
//			System.out.printf("GregsLighting: Mod present: %s\n",
//				((BaseMod)mod).getName());
//		}
//	}
	
	void loadConfig(File mcdir) {
		File cfgdir = new File(mcdir, "/config/");
		cfgfile = new File(cfgdir, configName);
		config = new OrderedProperties();
		try {
			config.load(new FileInputStream(cfgfile));
		}
		catch (FileNotFoundException e) {
			System.out.printf("%s: No existing config file\n", modName);
		}
		catch (IOException e) {
			System.out.printf("%s: Failed to read %s\n%s\n", modName,
				cfgfile.getPath(), e);
		}
		config.extended = false;
	}
	
	void saveConfig() {
		try {
			if (config.extended) {
				System.out.printf("%s: Writing config file\n", modName);
				config.store(new FileOutputStream(cfgfile),
					modName + " Configuration File");
			}
		}
		catch (IOException e) {
			System.out.printf("%s: Failed to %s\n%s\n", modName,
				cfgfile.getPath(), e);
		}
	}

	public String getVersion() {
		return version;
	}
	
	void registerChannels() {
//		ModLoader.registerPacketChannel(this, channelName);
	}
	
	void registerBlocks() {
		System.out.printf("%s: registerBlocks()\n", modName);
		floodlightCarbide = new BlockFloodlightCarbide(getBlockID(253, "carbideFloodlight"));
		floodlightCarbide.setHardness(1.5F);
		addBlock("Carbide Floodlight", floodlightCarbide);
		floodlightBeam = new BlockFloodlightBeam(getBlockID(254, "floodlightBeam"));
		addBlock("Floodlight Beam", floodlightBeam);
		floodlight = new BlockFloodlight(getBlockID(255, "floodlight"));
		floodlight.setHardness(1.5F);
		addBlock("Floodlight", floodlight);
	}
	
	void registerTileEntities() {
		ModLoader.registerTileEntity(TEFloodlightCarbide.class, "gcewing.CarbideFloodlight");
		ModLoader.registerTileEntity(TEFloodlightIC2.class, "gcewing.IC2ElectricFloodlight");
	}
	
	void registerItems() {
		calciumCarbide = addItem("Calcium Carbide", newItemI(10300, "calciumCarbide", 0x20));
		bonemealAndCharcoal = addItem("Bonemeal-Charcoal Mixture", newItemI(10301, "bonemealAndCharcoal", 0x21));
		gaslightMantle = addItem("Gaslight Mantle", newItemI(10302, "gaslightMantle", 0x22));
		glowstoneBulb = addItem("High Pressure Glowstone Bulb", newItemI(10303, "glowstoneBulb", 0x23));
	}

	void addRecipes() {
		addShapelessRecipe(bonemealAndCharcoal, 1, new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.coal, 1, 1));
		//addRecipe(floodlight, 1, "IrI", "IgI", "GGG",
		//	'I', Item.ingotIron, 'r', Item.redstone, 'g', Item.lightStoneDust, 'G', Block.glass);
		addSmeltingRecipe(calciumCarbide, 1, bonemealAndCharcoal);
		addRecipe(gaslightMantle, 4, "WgW", "gdg", "WgW",
			'W', Block.cloth, 'g', Item.lightStoneDust, 'd', Item.diamond);
		addRecipe(floodlightCarbide, 1, "CrC", "CmC", "GGG",
			'C', Block.cobblestone, 'r', Item.redstone, 'm', gaslightMantle, 'G', Block.glass);
		addRecipe(glowstoneBulb, 4, "GgG", "gdg", "GrG",
			'G', Block.glass, 'g', Item.lightStoneDust, 'd', Item.diamond, 'r', Item.redstone);
}
	
	void registerIC2() {
		if (ModLoader.isModLoaded("mod_IC2")) {
			floodlightIC2 = new BlockFloodlightIC2(getBlockID(252, "ic2ElectricFloodlight"));
			floodlightIC2.setHardness(1.5F);
			addBlock("IC2 Electric Floodlight", floodlightIC2);
			addRecipe(floodlightIC2, 1, "IrI", "IbI", "GGG",
			'I', Item.ingotIron, 'r', Item.redstone, 'b', glowstoneBulb, 'G', Block.glass);
		}
	}
	
	void addRecipe(Item product, int qty, Object... params) {
		ModLoader.addRecipe(new ItemStack(product, qty), params);
	}

	void addRecipe(Block product, int qty, Object... params) {
		ModLoader.addRecipe(new ItemStack(product, qty), params);
	}

	void addShapelessRecipe(Item product, int qty, Object... params) {
		ModLoader.addShapelessRecipe(new ItemStack(product, qty), params);
	}

	void addSmeltingRecipe(Item product, int qty, Item input) {
		ModLoader.addSmelting(input.shiftedIndex, new ItemStack(product, qty));
	}
	
	void addSmeltingRecipe(Item product, int qty, Block input) {
		ModLoader.addSmelting(input.blockID, new ItemStack(product, qty));
	}
	
	static int getBlockID(int defaultID, String name) {
		return getBlockOrItemID(defaultID, "block." + name, 0);
	}
	
	static int getItemID(int defaultID, String name) {
		return getBlockOrItemID(defaultID, "item." + name, 256);
	}
	
	static int getBlockOrItemID(int defaultID, String name, int offset) {
		String key = name + ".id";
		int id;
		if (config.containsKey(key)) {
			String value = (String) config.get(key);
			id = Integer.parseInt(value);
		}
		else {
			config.put(key, Integer.toString(defaultID));
			id = defaultID;
		}
		idToName.put(Integer.valueOf(offset + id), name);
		return id;
	}

	Item newItem(int id, String name) {
		return customItem(getItemID(id, name));
	}
	
	Item newItemI(int id, String name, int icon) {
		return customItem(getItemID(id, name)).setIconIndex(icon);
	}
	
	Item newItemSI(int id, String name, int maxStack, int icon) {
		return customItem(getItemID(id, name)).setMaxStackSize(maxStack).setIconIndex(icon);
	}
	
	Item customItem(int id) {
		return new TexturedItem(id);
	}
	
//	int iconOverride(String name) {
//		return ModLoader.addOverride("/gui/items.png", "/gcewing/prospecting/resources/" + name);
//	}
	
	Block addBlock(String name, Block block) {
		System.out.printf("%s: Adding block %s id %s\n", modName, name, block.blockID);
		block.setBlockName("gcewing." + idToName.get(block.blockID));
		ModLoader.registerBlock(block);
		ModLoader.addName(block, name);
		return block;
	}

	Item addItem(String name, Item item) {
		item.setItemName("gcewing." + idToName.get(item.shiftedIndex));
		ModLoader.addName(item, name);
		return item;
	}
	
	public boolean shiftKeyDown() {
		return false;
	}
	
	//-----------------------------------------------------------------------------------------
	
	public static void openGuiFloodlightCarbide(World world, int x, int y, int z, EntityPlayer player) {
		//System.out.printf("GregsLighting.openGuiFloodlightCarbide\n");
		player.openGui(mod, guiFloodlightCarbide, world, x, y, z);
	}

}
