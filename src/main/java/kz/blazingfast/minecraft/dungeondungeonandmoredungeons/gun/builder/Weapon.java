package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Weapon extends WeaponBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final Material material;
    private final String type;
    private final String name;
    private final int cost;
    private final int reward;
    private final double damage;
    private final int magazine;
    private final int ammo;
    
    public Weapon(ItemStack itemStack, ItemMeta itemMeta, Material material, String type, String name, int cost, int reward, double damage, int magazine, int ammo ) {
        this.itemStack = itemStack;
        this.itemMeta = itemMeta;
        this.material = material;
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.reward = reward;
        this.damage = damage;
        this.magazine = magazine;
        this.ammo = ammo;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public Material getMaterial() {
        return material;
    }

    public String getWeaponType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public int getReward() {
        return reward;
    }

    public int getCost() {
        return cost;
    }

    public int getMagazine() {
        return magazine;
    }

    public int getAmmo() {
        return ammo;
    }
}
