package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.decorator;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorOnlyDecorator extends ArmorDecorator {

    Player p;


    @Override
    public void setHp() {

        p.setMaxHealth(20);
        p.setHealth(20);
    }

    @Override
    public void giveAbsorptionHp() {

        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 10));
    }
}
