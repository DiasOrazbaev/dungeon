package kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.observer;

import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Game;
import kz.blazingfast.minecraft.dungeondungeonandmoredungeons.game.Member;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class TeamchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player p) {
            if (Game.isMember(p.getName())) {
                Member member = Game.getMember(p.getName());
                String team = Game.getMemberTeamname(member.getMembername());
                Teamchat chat = Game.getTeamChat(team);
                if (label.equalsIgnoreCase("teamchat") && args[0].isEmpty()) {
                    p.sendMessage("usage /teamchat <message>");
                }

                if (label.equalsIgnoreCase("teamchat") && !args[0].isEmpty()) {
                    assert chat != null;
                    chat.sendMessage(p, args[0]);
                    return true;
                }
            } else {
                p.sendMessage("You have no team.");
            }
        }

        return false;
    }
}
