package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator;

import org.bukkit.entity.Player;

public abstract class ArmorDecorator implements Armor{

    public abstract void setHp(Player p);

    public abstract void giveAbsorptionHp(Player p);
}
