package de.horcraft.nex.commands;

import de.horcraft.nex.StopTimerMain;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class RestartCommand extends CommandAPICommand {

    public RestartCommand(String commandName) {
        super(commandName);

        withPermission("restarttimer.stop");
        withArguments(new IntegerArgument("seconds"));

        executesPlayer((player, args) -> {
            int seconds = args.getUnchecked("seconds");
            AtomicInteger counter = new AtomicInteger(seconds);

            player.sendMessage(
                    Component.text("Der Server wird in ", NamedTextColor.DARK_AQUA)
                            .append(Component.text(seconds + " Sekunden ", NamedTextColor.YELLOW))
                            .append(Component.text("Neustarte!", NamedTextColor.DARK_AQUA))
            );

            Bukkit.getGlobalRegionScheduler().runAtFixedRate(
                    StopTimerMain.getInstance(),
                    task -> {
                        int time = counter.getAndDecrement();

                        if (time <= 0) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendActionBar(Component.text("Server Restart!", NamedTextColor.RED));

                                // 🎆 Finale Rakete
                                p.playSound(
                                        p.getLocation(),
                                        org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_BLAST,
                                        1.0f,
                                        1.0f
                                );
                            }
                            task.cancel();
                            Bukkit.restart();
                            return;
                        }


                        // Ton abspielen, sobald <= 10 Sekunden
                        if (time <= 10) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.playSound(
                                        p.getLocation(),
                                        org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING,
                                        1.0f,
                                        2.0f
                                );
                            }
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

