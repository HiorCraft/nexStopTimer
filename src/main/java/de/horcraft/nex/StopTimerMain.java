package de.horcraft.nex;

import de.horcraft.nex.commands.StopCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class StopTimerMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new StopCommand("stopcountdown").register();
    }

    public static StopTimerMain getInstance() {
        return JavaPlugin.getPlugin(StopTimerMain.class);
    }
}

