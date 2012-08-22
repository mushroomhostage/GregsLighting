package net.minecraft.server;

import forge.IGuiHandler;
import forge.MinecraftForge;
import forge.NetworkMod;
import gcewing.lighting.BlockFloodlight;
import gcewing.lighting.BlockFloodlightBeam;
import gcewing.lighting.BlockFloodlightCarbide;
import gcewing.lighting.BlockFloodlightIC2;
import gcewing.lighting.OrderedProperties;
import gcewing.lighting.TEFloodlightCarbide;
import gcewing.lighting.TEFloodlightIC2;
import gcewing.lighting.TexturedItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public abstract class GregsLighting extends NetworkMod implements IGuiHandler
{
    public static String modName = "GregsLighting";
    public static String channelName = "gce.lighting";
    public static String version = "1.2";
    public static final String textureDir = "/gcewing/lighting/resources/";
    public static final String textureFile = "/gcewing/lighting/resources/textures.png";
    public static final int guiFloodlightCarbide = 1;
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
    static Map idToName = new Hashtable();
    public static boolean isServer;
    public static GregsLighting mod;

    GregsLighting(boolean var1)
    {
        mod = this;
        isServer = var1;
    }

    public boolean clientSideRequired()
    {
        return true;
    }

    public boolean serverSideRequired()
    {
        return true;
    }

    public void load(File var1)
    {
        System.out.printf("GregsLighting: load()\n", new Object[0]);
        MinecraftForge.setGuiHandler(this, this);
        this.loadConfig(var1);
        this.registerBlocks();
        this.registerTileEntities();
        this.registerItems();
        this.addRecipes();
        this.registerChannels();
        this.registerIC2();
        this.saveConfig();
    }

    void loadConfig(File var1)
    {
        File var2 = new File(var1, "/config/");
        cfgfile = new File(var2, configName);
        config = new OrderedProperties();

        try
        {
            config.load(new FileInputStream(cfgfile));
        }
        catch (FileNotFoundException var4)
        {
            System.out.printf("%s: No existing config file\n", new Object[] {modName});
        }
        catch (IOException var5)
        {
            System.out.printf("%s: Failed to read %s\n%s\n", new Object[] {modName, cfgfile.getPath(), var5});
        }

        config.extended = false;
    }

    void saveConfig()
    {
        try
        {
            if (config.extended)
            {
                System.out.printf("%s: Writing config file\n", new Object[] {modName});
                config.store(new FileOutputStream(cfgfile), modName + " Configuration File");
            }
        }
        catch (IOException var2)
        {
            System.out.printf("%s: Failed to %s\n%s\n", new Object[] {modName, cfgfile.getPath(), var2});
        }
    }

    public String getVersion()
    {
        return version;
    }

    void registerChannels() {}

    void registerBlocks()
    {
        System.out.printf("%s: registerBlocks()\n", new Object[] {modName});
        floodlightCarbide = new BlockFloodlightCarbide(getBlockID(253, "carbideFloodlight"));
        floodlightCarbide.c(1.5F);
        this.addBlock("Carbide Floodlight", floodlightCarbide);
        floodlightBeam = new BlockFloodlightBeam(getBlockID(254, "floodlightBeam"));
        this.addBlock("Floodlight Beam", floodlightBeam);
        floodlight = new BlockFloodlight(getBlockID(255, "floodlight"));
        floodlight.c(1.5F);
        this.addBlock("Floodlight", floodlight);
    }

    void registerTileEntities()
    {
        ModLoader.registerTileEntity(TEFloodlightCarbide.class, "gcewing.CarbideFloodlight");
        ModLoader.registerTileEntity(TEFloodlightIC2.class, "gcewing.IC2ElectricFloodlight");
    }

    void registerItems()
    {
        calciumCarbide = this.addItem("Calcium Carbide", this.newItemI(10300, "calciumCarbide", 32));
        bonemealAndCharcoal = this.addItem("Bonemeal-Charcoal Mixture", this.newItemI(10301, "bonemealAndCharcoal", 33));
        gaslightMantle = this.addItem("Gaslight Mantle", this.newItemI(10302, "gaslightMantle", 34));
        glowstoneBulb = this.addItem("High Pressure Glowstone Bulb", this.newItemI(10303, "glowstoneBulb", 35));
    }

    void addRecipes()
    {
        this.addShapelessRecipe(bonemealAndCharcoal, 1, new Object[] {new ItemStack(Item.INK_SACK, 1, 15), new ItemStack(Item.COAL, 1, 1)});
        this.addSmeltingRecipe(calciumCarbide, 1, bonemealAndCharcoal);
        this.addRecipe(gaslightMantle, 4, new Object[] {"WgW", "gdg", "WgW", 'W', Block.WOOL, 'g', Item.GLOWSTONE_DUST, 'd', Item.DIAMOND});
        this.addRecipe((Block)floodlightCarbide, 1, new Object[] {"CrC", "CmC", "GGG", 'C', Block.COBBLESTONE, 'r', Item.REDSTONE, 'm', gaslightMantle, 'G', Block.GLASS});
        this.addRecipe(glowstoneBulb, 4, new Object[] {"GgG", "gdg", "GrG", 'G', Block.GLASS, 'g', Item.GLOWSTONE_DUST, 'd', Item.DIAMOND, 'r', Item.REDSTONE});
    }

    void registerIC2()
    {
        if (ModLoader.isModLoaded("mod_IC2"))
        {
            floodlightIC2 = new BlockFloodlightIC2(getBlockID(252, "ic2ElectricFloodlight"));
            floodlightIC2.c(1.5F);
            this.addBlock("IC2 Electric Floodlight", floodlightIC2);
            this.addRecipe((Block)floodlightIC2, 1, new Object[] {"IrI", "IbI", "GGG", 'I', Item.IRON_INGOT, 'r', Item.REDSTONE, 'b', glowstoneBulb, 'G', Block.GLASS});
        }
    }

    void addRecipe(Item var1, int var2, Object ... var3)
    {
        ModLoader.addRecipe(new ItemStack(var1, var2), var3);
    }

    void addRecipe(Block var1, int var2, Object ... var3)
    {
        ModLoader.addRecipe(new ItemStack(var1, var2), var3);
    }

    void addShapelessRecipe(Item var1, int var2, Object ... var3)
    {
        ModLoader.addShapelessRecipe(new ItemStack(var1, var2), var3);
    }

    void addSmeltingRecipe(Item var1, int var2, Item var3)
    {
        ModLoader.addSmelting(var3.id, new ItemStack(var1, var2));
    }

    void addSmeltingRecipe(Item var1, int var2, Block var3)
    {
        ModLoader.addSmelting(var3.id, new ItemStack(var1, var2));
    }

    static int getBlockID(int var0, String var1)
    {
        return getBlockOrItemID(var0, "block." + var1, 0);
    }

    static int getItemID(int var0, String var1)
    {
        return getBlockOrItemID(var0, "item." + var1, 256);
    }

    static int getBlockOrItemID(int var0, String var1, int var2)
    {
        String var3 = var1 + ".id";
        int var4;

        if (config.containsKey(var3))
        {
            String var5 = (String)config.get(var3);
            var4 = Integer.parseInt(var5);
        }
        else
        {
            config.put(var3, Integer.toString(var0));
            var4 = var0;
        }

        idToName.put(Integer.valueOf(var2 + var4), var1);
        return var4;
    }

    Item newItem(int var1, String var2)
    {
        return this.customItem(getItemID(var1, var2));
    }

    Item newItemI(int var1, String var2, int var3)
    {
        return this.customItem(getItemID(var1, var2)).d(var3);
    }

    Item newItemSI(int var1, String var2, int var3, int var4)
    {
        return this.customItem(getItemID(var1, var2)).e(var3).d(var4);
    }

    Item customItem(int var1)
    {
        return new TexturedItem(var1);
    }

    Block addBlock(String var1, Block var2)
    {
        System.out.printf("%s: Adding block %s id %s\n", new Object[] {modName, var1, Integer.valueOf(var2.id)});
        var2.a("gcewing." + (String)idToName.get(Integer.valueOf(var2.id)));
        ModLoader.registerBlock(var2);
        ModLoader.addName(var2, var1);
        return var2;
    }

    Item addItem(String var1, Item var2)
    {
        var2.a("gcewing." + (String)idToName.get(Integer.valueOf(var2.id)));
        ModLoader.addName(var2, var1);
        return var2;
    }

    public boolean shiftKeyDown()
    {
        return false;
    }

    public static void openGuiFloodlightCarbide(World var0, int var1, int var2, int var3, EntityHuman var4)
    {
        var4.openGui(mod, 1, var0, var1, var2, var3);
    }
}
