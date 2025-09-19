package de.horcraft.nex;

import de.horcraft.nex.commands.StopCommand;
import de.horcraft.nex.commands.RestartCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class StopTimerMain extends JavaPlugin {
    @Override
    public void onEnable() {
        new StopCommand("stopcd").register();
        new RestartCommand("restartcd").register();
    }

    public static StopTimerMain getInstance() {
        return JavaPlugin.getPlugin(StopTimerMain.class);
    }
}

