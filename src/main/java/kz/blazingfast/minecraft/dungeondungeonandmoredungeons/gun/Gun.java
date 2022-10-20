package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

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
    AK47(10, 30, "AK47", Material.BLACK_DYE),
    M4A1S(8, 25, "M4A1-S", Material.BLUE_DYE);


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

        int bullets = meta.getPersistentDataContainer().get(
                NamespacedKey.fromString("bullets_count"),
                PersistentDataType.INTEGER
        );

        Gun gun = Gun.getGunFrom(itemStack);

        if (bullets <= 0) {
            reload(itemStack);
            return;
        }

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("bullets_count"),
                PersistentDataType.INTEGER,
                bullets - 1
        );

        meta.setDisplayName(String.format("%s %d|%d", gun.getName(), gun.getBulletCount(), gun.getBulletCount()));

        itemStack.setItemMeta(meta);

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                100,
                (entity -> entity != player && entity instanceof LivingEntity)
        );

        if (rayTraceResult != null) {
            LivingEntity livingEntity = (LivingEntity) rayTraceResult.getHitEntity();

            RayTraceResult rayTraceBlocks = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getLocation().getDirection(), 99);
            if (rayTraceBlocks == null) {
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

        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("bullets_count"),
                PersistentDataType.INTEGER,
                bulletCount
        );

        itemStack.setItemMeta(meta);
    }

    private double damage;
    private int bulletCount;
    private String name;
    private Material material;

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
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

    Gun(double damage, int bulletCount, String name, Material material) {
        this.damage = damage;
        this.bulletCount = bulletCount;
        this.name = name;
        this.material = material;
    }
}
