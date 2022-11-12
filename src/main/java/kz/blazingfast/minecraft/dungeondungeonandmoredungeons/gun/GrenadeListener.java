package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.DungeonDungeonAndMoreDungeons;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static kz.blazingfast.minecraft.dungeondungeonandmoredungeons.gun.WeaponLogic.*;

public class GrenadeListener implements Listener {
    public static final int HE_ID = 465;
    public static final int SMOKE_ID = 236;
    public static final int FLASHBACK_ID = 845;
    public static final int FIRE_ID = 537;
    public static Map<Entity, Integer> tasks = new ConcurrentHashMap<>();

    @EventHandler
    public void throwGrenade(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("pressed");
        if (player.getInventory().getItemInMainHand().equals(new ItemStack(Material.AIR))) {
            e.setCancelled(true);
            return;
        }
        if (isGrenade(player.getInventory().getItemInMainHand())) {
            ItemStack item = new ItemStack(player.getInventory().getItemInMainHand());
            item.setAmount(1);
            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            Location location = player.getEyeLocation();
            Entity entity = Objects.requireNonNull(location.getWorld()).dropItem(location, item);
            entity.setVelocity(player.getLocation().getDirection());

            tasks.put(entity, 4);
        }
    }

    public static void enableTask(Plugin plugin) {
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (tasks.size() > 0) {
                List<Entity> entities = new ArrayList<>(tasks.keySet());

                for (Entity entity : entities) {

                    if (entity instanceof Item) {
                        tasks.replace(entity, tasks.get(entity) - 1);
                    }

                    if (tasks.get(entity) > 0) {
                        continue;
                    }

                    assert entity instanceof Item;
                    ItemStack itemStack = ((Item) entity).getItemStack();

                    Integer type = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().get(
                            Objects.requireNonNull(NamespacedKey.fromString("type")),
                            PersistentDataType.INTEGER
                    );

                    assert type != null;

                    switch (type) {
                        case (HE_ID) -> {
                            List<Entity> nearby = entity.getNearbyEntities(4, 4, 4);

                            if (!nearby.isEmpty())
                                nearby.forEach(x -> ((LivingEntity) x).damage(15));
                        }

                        case (SMOKE_ID) -> {
                            Particle.DustOptions options = new Particle.DustOptions(Color.WHITE, 81f);
                            Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                                for (int t = 0; t < 8; t++) {
                                    for (int i = -3; i < 3; i++) {
                                        for (int j = -2; j < 3; j++) {
                                            for (int k = -3; k < 3; k++) {
                                                entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation().add(i, j, k), 30, options);
                                            }
                                        }
                                    }
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }

                        case FIRE_ID -> Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                            /*Material[][] mem = new Material[5][5];
                            var loc = entity.getLocation();
                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    var l = loc.add(new Vector(i, 1, j));
                                    mem[i + 2][j + 2] = l.getBlock().getType();
                                    l.getBlock().setType(Material.FIRE);
                                }
                            }

                            try {
                                TimeUnit.SECONDS.sleep(7);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            for (int i = -2; i < 3; i++) {
                                for (int j = -2; j < 3; j++) {
                                    var l = loc.add(new Vector(i, 1, j));
                                    l.getBlock().setType(mem[i + 2][j + 2]);
                                }
                            }*/
                            long startTime = System.currentTimeMillis();

                            // find block where potion exploded
                            int i = 4;

                            // find block where potion exploded
                            Location centerLoc = entity.getLocation();

                            while (i > 0 && !isIgnitable(centerLoc.getBlock().getType())) {
                                centerLoc.subtract(entity.getVelocity());
                                --i;
                            }
                            Vector center = centerLoc.toVector();
                            center.setX(center.getBlockX() + 0.5);
                            center.setY(center.getBlockY() + 0.5);
                            center.setZ(center.getBlockZ() + 0.5);
                            // vector calculations
                            // calculate rays from center
                            // for each ray from center:
                            //     while ray has next block within radius
                            //       block = next block on ray
                            //       if block at vector is ignitable
                            //         ignite it
                            //       else
                            //         break

                            /*double inc = Math.PI * (3.0 - Math.sqrt(5));

                            double off = 2.0
                            double y, r, phi;
                            BlockIterator blockIter;
                            Block next;
                            for (int k = 0; k < plugin.rays; ++k) {
                                y = k * off - 1 + (off / 2.0);
                                r = Math.sqrt(1 - y * y);
                                phi = k * inc;
                                Vector dirVector = new Vector(Math.cos(phi) * r, y, Math.sin(phi) * r);
                                blockIter = new BlockIterator(entity.getWorld(),
                                        center,
                                        dirVector,
                                        0,
                                        plugin.radius);
                                *//*while (blockIter.hasNext()) {
                                    next = blockIter.next();
                                    if (isIgnitable(next.getType())) {
                                        ignite(next, );
                                    } else {
                                        break;
                                    }
                                }*//*
                            }*/
                        });
                    }

                    tasks.remove(entity);
                    entity.remove();
                }
            }
        }, 20, 20);
    }

    private static boolean isIgnitable(Material mat) {
        return (mat == Material.AIR || mat == Material.FIRE || mat == Material.GRASS
                || mat == Material.POTTED_WITHER_ROSE || mat == Material.ROSE_BUSH);
    }
/*
    private static void ignite(Block block, Player player) {
        BlockIgniteEvent igniteEvent = new BlockIgniteEvent(block,
                BlockIgniteEvent.IgniteCause.SPREAD, player);
        Bukkit.getPluginManager().callEvent(igniteEvent);
        if (!igniteEvent.isCancelled()) {
            block.setType(Material.FIRE);
        }
    }*/
}
