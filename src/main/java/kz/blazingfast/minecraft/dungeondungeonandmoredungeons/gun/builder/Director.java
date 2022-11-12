package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder;

import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.Objects;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons.getInstance;

public class Director {
    public void build_ak47(@Nonnull WeaponBuilder builder) {
        String weapon = "ak47";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.BLACK_DYE);
        parseConfiguration(builder, weapon);
    }

    private void parseConfiguration(@Nonnull WeaponBuilder builder, String weapon) {
        builder.setType(getInstance().getConfig().getString("weapon." + weapon + ".type"));
        builder.setName(getInstance().getConfig().getString("weapon." + weapon + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("weapon." + weapon + ".cost"))));
        builder.setReward(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("weapon." + weapon + ".reward"))));
        builder.setDamage(Double.parseDouble(Objects.requireNonNull(getInstance().getConfig().getString("weapon." + weapon + ".damage"))));
        builder.setMagazine(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("weapon." + weapon + ".magazine"))));
        builder.setAmmo(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("weapon." + weapon + ".ammo"))));
    }

    public void build_m4a1s(@Nonnull WeaponBuilder builder) {
        String weapon = "m4a1s";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.BLUE_DYE);
        parseConfiguration(builder, weapon);
    }

    public void build_sg553(@Nonnull WeaponBuilder builder) {
        String weapon = "sg553";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.GRAY_DYE);
        parseConfiguration(builder, weapon);
    }

    public void build_awp(@Nonnull WeaponBuilder builder) {
        String weapon = "awp";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.GREEN_DYE);
        parseConfiguration(builder, weapon);
    }

    public void build_glock(@Nonnull WeaponBuilder builder) {
        String weapon = "glock";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.BROWN_DYE);
        parseConfiguration(builder, weapon);
    }

    public void build_usp(@Nonnull WeaponBuilder builder) {
        String weapon = "usp";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.LIGHT_BLUE_DYE);
        parseConfiguration(builder, weapon);
    }

    public void build_helmet(@Nonnull WeaponBuilder builder) {
        String armor = "helmet";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.LEATHER_HELMET);
        builder.setType(getInstance().getConfig().getString("util." + armor + ".type"));
        builder.setName(getInstance().getConfig().getString("util." + armor + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("util." + armor + ".cost"))));
    }

    public void build_armor(@Nonnull WeaponBuilder builder) {
        String armor = "armor";
        builder.setItemStack(null);
        builder.setItemMeta(null);
        builder.setMaterial(Material.LEATHER_CHESTPLATE);
        builder.setType(getInstance().getConfig().getString("util." + armor + ".type"));
        builder.setName(getInstance().getConfig().getString("util." + armor + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("util." + armor + ".cost"))));
    }

    // HE - CYAN_DYE
    public void build_he(@Nonnull WeaponBuilder builder) {
        String grenade = "he";
        builder.setItemMeta(null);
        builder.setItemStack(null);
        builder.setMaterial(Material.CYAN_DYE);
        builder.setType("grenade");
        builder.setReward(getInstance().getConfig().getInt("grenade." + grenade + ".type"));
        builder.setName(getInstance().getConfig().getString("grenade." + grenade + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("grenade." + grenade + ".cost"))));
    }

    // smoke - WHITE_DYE
    public void build_smoke(@Nonnull WeaponBuilder builder) {
        String grenade = "smoke";
        builder.setItemMeta(null);
        builder.setItemStack(null);
        builder.setMaterial(Material.WHITE_DYE);
        builder.setType("grenade");
        builder.setReward(getInstance().getConfig().getInt("grenade." + grenade + ".type"));
        builder.setName(getInstance().getConfig().getString("grenade." + grenade + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("grenade." + grenade + ".cost"))));
    }

    // flashback - LIME_DYE
    public void build_flashback(@Nonnull WeaponBuilder builder) {
        String grenade = "flashback";
        builder.setItemMeta(null);
        builder.setItemStack(null);
        builder.setMaterial(Material.LIME_DYE);
        builder.setType("grenade");
        builder.setReward(getInstance().getConfig().getInt("grenade." + grenade + ".type"));
        builder.setName(getInstance().getConfig().getString("grenade." + grenade + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("grenade." + grenade + ".cost"))));
    }

    // fire - MAGENTA_DYE
    public void build_fire(@Nonnull WeaponBuilder builder) {
        var grenade = "fire";
        builder.setItemMeta(null);
        builder.setItemStack(null);
        builder.setMaterial(Material.MAGENTA_DYE);
        builder.setType("grenade");
        builder.setReward(getInstance().getConfig().getInt("grenade." + grenade + ".type"));
        builder.setName(getInstance().getConfig().getString("grenade." + grenade + ".name"));
        builder.setCost(Integer.parseInt(Objects.requireNonNull(getInstance().getConfig().getString("grenade." + grenade + ".cost"))));
    }
}
