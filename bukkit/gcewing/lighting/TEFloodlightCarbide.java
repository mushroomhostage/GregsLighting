package gcewing.lighting;

import forge.ISidedInventory;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.GregsLighting;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntity;

public class TEFloodlightCarbide extends TileEntity implements IInventory, ISidedInventory
{
    ItemStack[] inventory = new ItemStack[2];
    static final int waterSlot = 0;
    static final int carbideSlot = 1;
    static final int minutesPerCarbide = 5;
    static final int maxCarbideLevel = 300;
    static final int maxWaterLevel = 19200;
    int waterLevel;
    int carbideLevel;
    int ticks = 0;

    // CraftBukkit start
    public java.util.List<org.bukkit.entity.HumanEntity> transaction = 
            new java.util.ArrayList<org.bukkit.entity.HumanEntity>();
    
    public void onOpen(org.bukkit.craftbukkit.entity.CraftHumanEntity who) {
        transaction.add(who);
    }

    public void onClose(org.bukkit.craftbukkit.entity.CraftHumanEntity who) {
        transaction.remove(who);
    }

    public java.util.List<org.bukkit.entity.HumanEntity> getViewers() {
        return transaction;
    }

    public void setMaxStackSize(int size) {}

    public ItemStack[] getContents()
    {
        return inventory;
    }
    // CraftBukkit end

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return this.inventory.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int var1)
    {
        return this.inventory[var1];
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int var1)
    {
        return null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        System.out.printf("TEFloodlightCarbide.setInventorySlotContents: %d %s\n", new Object[] {Integer.valueOf(var1), var2});
        this.inventory[var1] = var2;
        this.refillWater();
        this.updateBlock();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "container.carbide_floodlight";
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        ItemStack var3 = this.inventory[var1];

        if (var3 != null)
        {
            if (var3.count <= var2)
            {
                this.inventory[var1] = null;
            }
            else
            {
                var3 = var3.a(var2);
            }

            this.update();
        }

        return var3;
    }

    public int getStartInventorySide(int var1)
    {
        return var1 == 1 ? 0 : 1;
    }

    public int getSizeInventorySide(int var1)
    {
        return 1;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        super.a(var1);
        NBTTagList var2 = var1.getList("Items");
        this.inventory = new ItemStack[this.getSize()];

        for (int var3 = 0; var3 < var2.size(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.inventory.length)
            {
                this.inventory[var5] = ItemStack.a(var4);
            }
        }

        this.waterLevel = var1.getInt("Water");
        this.carbideLevel = var1.getInt("Carbide");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        super.b(var1);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inventory.length; ++var3)
        {
            if (this.inventory[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inventory[var3].save(var4);
                var2.add(var4);
            }
        }

        var1.set("Items", var2);
        var1.setInt("Water", this.waterLevel);
        var1.setInt("Carbide", this.carbideLevel);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a(EntityHuman var1)
    {
        return this.world.getTileEntity(this.x, this.y, this.z) == this ? var1.e((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D) <= 64.0D : false;
    }

    public void f() {}

    public void g() {}

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        if (!this.world.isStatic)
        {
            if (this.ticks == 0)
            {
                this.ticks = 20;

                if (this.isActive())
                {
                    --this.waterLevel;
                    this.refillWater();
                    --this.carbideLevel;
                    this.refillCarbide();
                    this.update();

                    if (!this.isActive())
                    {
                        this.updateBlock();
                    }
                }
            }

            --this.ticks;
        }
    }

    void updateBlock()
    {
        GregsLighting.floodlightCarbide.update(this.world, this.x, this.y, this.z);
    }

    public boolean isActive()
    {
        return this.waterLevel > 0 && this.carbideLevel > 0 && this.receivingRedstoneSignal();
    }

    boolean receivingRedstoneSignal()
    {
        return this.world.isBlockIndirectlyPowered(this.x, this.y, this.z);
    }

    void refillWater()
    {
        if (this.waterLevel == 0)
        {
            ItemStack var1 = this.inventory[0];

            if (Water.isWaterItem(var1))
            {
                this.waterLevel = 19200;
                this.setItem(0, Water.emptyContainerFor(var1));
                this.update();
            }
        }
    }

    void refillCarbide()
    {
        if (this.carbideLevel == 0 && this.waterLevel > 0 && this.receivingRedstoneSignal())
        {
            ItemStack var1 = this.inventory[1];

            if (var1 != null && var1.getItem() == GregsLighting.calciumCarbide)
            {
                this.splitStack(1, 1);
                this.carbideLevel = 300;
            }
        }
    }
}
