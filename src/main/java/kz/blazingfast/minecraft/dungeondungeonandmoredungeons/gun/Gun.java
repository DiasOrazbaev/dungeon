package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;

import java.util.Objects;

public enum Gun {

    AK47(10, 30, 120,  "AK-47", Material.BLACK_DYE),
    M4A1S(8, 25, 90, "M4A1-S", Material.BLUE_DYE),
    AWP(20, 10, 30, "AWP", Material.GREEN_DYE),
    USP(4, 12, 24, "USP-S", Material.LIGHT_BLUE_DYE),
    GLOCK(3, 20, 60, "GLOCK-18", Material.YELLOW_DYE);

    public static Gun getGunFrom(ItemStack itemStack) {

        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        var gunName = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_name")),
                PersistentDataType.STRING
        );

        if (gunName == null) {
            return null;
        }
        return valueOf(gunName);
    }

    public void shoot(ItemStack itemStack, Player player) {

        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;

        Gun gun = Gun.getGunFrom(itemStack);
        assert gun != null;

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER
        );

        if (bulletsInMagazine <= 0) {
            return;
        }

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER,
                bulletsInMagazine - 1
        );
        itemStack.setItemMeta(meta);

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                100,
                (entity -> entity != player && entity instanceof LivingEntity)
        );

        if (rayTraceResult != null) {
            LivingEntity livingEntity = (LivingEntity) rayTraceResult.getHitEntity();

            assert livingEntity != null;

            RayTraceResult rayTraceBlocks = player.getWorld().rayTraceBlocks(
                    player.getEyeLocation(),
                    player.getLocation().getDirection(),
                    99);

            if (rayTraceBlocks == null) {
                livingEntity.damage(damage);
                return;
            }

            Block block = rayTraceBlocks.getHitBlock();

            if (block != null) {
                if (player.getLocation().distance(block.getLocation()) < player.getLocation().distance(livingEntity.getLocation())) {
                    return;
                }
            }
            livingEntity.damage(damage);
        }
    }

    public void reload(ItemStack itemStack) {

        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;

        int ammoLeft = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("gun_ammo"),
                PersistentDataType.INTEGER
        );

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER
        );

        ammoLeft = ammoLeft + bulletsInMagazine;

        bulletsInMagazine = 0;

        if (ammoLeft >= getMagazine()) {
            ammoLeft = ammoLeft - getMagazine();
            bulletsInMagazine = bulletsInMagazine + getMagazine();
        } else {
            bulletsInMagazine = ammoLeft;
            ammoLeft = 0;
        }

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER,
                bulletsInMagazine
        );

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("gun_ammo"),
                PersistentDataType.INTEGER,
                ammoLeft
        );

        itemStack.setItemMeta(meta);
    }

    public void displayGunInterface(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;

        int ammoLeft = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("gun_ammo"),
                PersistentDataType.INTEGER
        );

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("gun_magazine"),
                PersistentDataType.INTEGER
        );

        meta.setDisplayName(String.format(
                "%s %s|%s %s",
                "" + ChatColor.WHITE + "" + getName(),
                coloredBullets(bulletsInMagazine, getMagazine()),
                "" + ChatColor.GRAY + "" + getMagazine(),
                "" + ChatColor.DARK_GREEN + ammoLeft));

        itemStack.setItemMeta(meta);
    }

    public String coloredBullets(int currentMagazine, int fullMagazin) {
        if (fullMagazin/3 >= currentMagazine) return "" +  ChatColor.RED + currentMagazine;
        return  "" + ChatColor.WHITE + currentMagazine;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getMagazine() {
        return magazine;
    }

    public void setMagazine(int magazine) {
        this.magazine = magazine;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    private double damage;
    private int magazine, ammo;
    private String name;
    private Material material;

    Gun(double gunDamage, int gunMagazine, int gunAmmo, String gunName, Material gunMaterial) {
        this.damage = gunDamage;
        this.magazine = gunMagazine;
        this.ammo = gunAmmo;
        this.name = gunName;
        this.material = gunMaterial;
    }
}
