package de.horcraft.nex.commands;

import de.horcraft.nex.StopTimerMain;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class StopCommand extends CommandAPICommand {

    public StopCommand(String commandName) {
        super(commandName);

        withPermission("stoptimer.stop");
        withArguments(new IntegerArgument("seconds"));

        executesPlayer((player, args) -> {
            int seconds = args.getUnchecked("seconds");
            AtomicInteger counter = new AtomicInteger(seconds);

            player.sendMessage(
                    Component.text("Der Server wird in ", NamedTextColor.BLUE)
                            .append(Component.text(seconds + " Sekunden ", NamedTextColor.GREEN))
                            .append(Component.text("heruntergefahren!", NamedTextColor.GREEN))
            );

            Bukkit.getGlobalRegionScheduler().runAtFixedRate(
                    StopTimerMain.getInstance(),
                    task -> {
                        int time = counter.getAndDecrement();

                        if (time <= 0) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendActionBar(Component.text("Server stoppt jetzt!", NamedTextColor.RED));
                            }
                            task.cancel();
                            Bukkit.shutdown();
                            return;
                        }

                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendActionBar(
                                    Component.text("Restart: ", NamedTextColor.AQUA)
                                            .append(Component.text(time, NamedTextColor.GREEN))
                                            .append(Component.text("s", NamedTextColor.GREEN))
                            );
                        }
                    },
                    1L,   // Start nach 1 Tick
                    20L   // alle 20 Ticks = 1 Sekunde
            );
        });
    }
}
