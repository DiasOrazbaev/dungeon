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
                if (label.equalsIgnoreCase("teamchat") && args.length == 0) {
                    p.sendMessage("usage /teamchat <message>");
                }

                if (label.equalsIgnoreCase("teamchat") && args.length > 0) {
                    assert chat != null;
                    StringBuilder builder = new StringBuilder();
                    for (String str: args) {
                        builder.append(str).append(" ");
                    }
                    chat.sendMessage(p, builder.toString());
                    return true;
                }
            } else {
                p.sendMessage("You have no team.");
            }
            return false;
        }
        return false;
    }
}