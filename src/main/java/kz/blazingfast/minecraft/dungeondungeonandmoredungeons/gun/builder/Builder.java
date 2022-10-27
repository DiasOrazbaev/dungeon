package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Builder {
    void setItemStack(ItemStack itemStack);
    void setItemMeta(ItemMeta itemMeta);
    void setMaterial(Material material);
    void setType(String type);
    void setName(String name);
    void setCost(int cost);
    void setReward(int reward);
    void setDamage(double damage);
    void setMagazine(int magazine);
    void setAmmo(int ammo);
}
