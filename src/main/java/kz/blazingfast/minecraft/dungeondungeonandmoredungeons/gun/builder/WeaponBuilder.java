package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.prototype.Prototype;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WeaponBuilder implements Builder, Prototype {

    private static ItemStack itemStack;
    private ItemMeta itemMeta;
    private Material material;
    private String type;
    private String name;
    private int cost;
    private int reward;
    private double damage;
    private int magazine;
    private int ammo;

    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public void setMagazine(int magazine) {
        this.magazine = magazine;
    }

    @Override
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public Weapon getResultData() {
        return new Weapon(itemStack, itemMeta, material, type, name, cost, reward, damage, magazine, ammo);
    }

    public ItemStack getResult() {
        itemStack = new ItemStack(material, 1);
        itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(ChatColor.WHITE + name);
        List<String> itemLore = new ArrayList<>();

        itemMeta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_type")),
                PersistentDataType.STRING,
                type
        );

        itemMeta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_id")),
                PersistentDataType.STRING,
                String.valueOf(UUID.randomUUID())
        );

        itemLore.add("TYPE: " + ChatColor.WHITE + type);

        itemMeta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_name")),
                PersistentDataType.STRING,
                name
        );

        if (type.equals("rifle") || type.equals("rifleWithScope") || type.equals("sniper") || type.equals("pistol")) {

            itemLore.add("WEAPON: " + ChatColor.GOLD + name);

            itemLore.add("REWARD: " + ChatColor.GREEN + reward);
            itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString("gun_reward")), PersistentDataType.INTEGER, reward
            );

            itemLore.add("DAMAGE: " + ChatColor.RED + damage);
            itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString("gun_damage")), PersistentDataType.DOUBLE, damage
            );

            itemLore.add("MAGAZINE: " + ChatColor.WHITE + magazine);
            itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")), PersistentDataType.INTEGER, magazine
            );

            itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString("gun_magazine_full")), PersistentDataType.INTEGER, magazine
            );

            itemLore.add("AMMO: " + ChatColor.WHITE + ammo);
            itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString("gun_ammo")), PersistentDataType.INTEGER, ammo
            );
        }

        itemLore.add("COST: " + ChatColor.YELLOW + cost);
        itemMeta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_cost")),
                PersistentDataType.INTEGER,
                cost
        );


        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return new ItemStack(itemStack);
    }

    @Override
    public ItemStack copy(ItemStack original) {
        return original.clone();
    }
}
