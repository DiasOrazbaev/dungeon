package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WeaponLogic {

    public static boolean isGun(@NotNull ItemStack itemInMainHand) {

        ItemMeta meta = itemInMainHand.getItemMeta();
        assert meta != null;
        String type = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_type")),
                PersistentDataType.STRING
        );

        assert type != null;
        return type.equals("rifle") || type.equals("rifleWithScope") || type.equals("sniper") || type.equals("pistol");
    }

    public static void shoot(@NotNull ItemStack gun, Player player) {

        ItemMeta meta = gun.getItemMeta();
        assert meta != null;

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER
        );

        if (bulletsInMagazine <= 0) {
            return;
        }

        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER,
                bulletsInMagazine - 1
        );
        gun.setItemMeta(meta);

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                100,
                (entity -> entity != player && entity instanceof LivingEntity)
        );

        double damage = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_damage")),
                PersistentDataType.DOUBLE
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

    public static void reload(@NotNull ItemStack gun) {

        ItemMeta meta = gun.getItemMeta();
        assert meta != null;

        int ammoLeft = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_ammo")),
                PersistentDataType.INTEGER
        );

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER
        );

        ammoLeft = ammoLeft + bulletsInMagazine;

        bulletsInMagazine = 0;

        if (ammoLeft >= getFullMagazine(gun)) {
            ammoLeft = ammoLeft - getFullMagazine(gun);
            bulletsInMagazine = bulletsInMagazine + getFullMagazine(gun);
        } else {
            bulletsInMagazine = ammoLeft;
            ammoLeft = 0;
        }

        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER,
                bulletsInMagazine
        );

        meta.getPersistentDataContainer().set(
                Objects.requireNonNull(NamespacedKey.fromString("gun_ammo")),
                PersistentDataType.INTEGER,
                ammoLeft
        );

        gun.setItemMeta(meta);
    }

    public static boolean scope(@NotNull ItemStack itemStack, Player player, int zoomLevel) {
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;

        String type = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_type")),
                PersistentDataType.STRING
        );
        assert type != null;

        if (type.equals("sniper") && zoomLevel == 1) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 5));
            return true;
        } else if (type.equals("rifleWithScope") && zoomLevel == 1 || type.equals("sniper") && zoomLevel == 2) {
            player.removePotionEffect(PotionEffectType.SLOW);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1));
            return true;
        } else {
            player.removePotionEffect(PotionEffectType.SLOW);
            return false;
        }
    }

    public static void displayGunInterface(@NotNull ItemStack gun) {
        ItemMeta meta = gun.getItemMeta();
        assert meta != null;

        int ammoLeft = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_ammo")),
                PersistentDataType.INTEGER
        );

        int bulletsInMagazine = meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER
        );

        meta.setDisplayName(String.format(
                "%s %s|%s %s",
                "" + ChatColor.WHITE + "" + getName(gun),
                coloredBullets(bulletsInMagazine, getFullMagazine(gun)),
                "" + ChatColor.GRAY + "" + getFullMagazine(gun),
                "" + ChatColor.DARK_GREEN + ammoLeft));

        gun.setItemMeta(meta);
    }

    public static String coloredBullets(int currentMagazine, int fullMagazin) {
        if (fullMagazin/3 >= currentMagazine) return "" +  ChatColor.RED + currentMagazine;
        return  "" + ChatColor.WHITE + currentMagazine;
    }

    public static String getName(@NotNull ItemStack gun) {
        ItemMeta meta = gun.getItemMeta();
        assert meta != null;
        return meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_name")),
                PersistentDataType.STRING);
    }

    public static int getFullMagazine(@NotNull ItemStack gun) {
        ItemMeta meta = gun.getItemMeta();
        assert meta != null;
        return meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine_full")),
                PersistentDataType.INTEGER);
    }

    public static int getCurrentMagazine(@NotNull ItemStack gun) {
        ItemMeta meta = gun.getItemMeta();
        assert meta != null;
        return meta.getPersistentDataContainer().get(
                Objects.requireNonNull(NamespacedKey.fromString("gun_magazine")),
                PersistentDataType.INTEGER);
    }

}
