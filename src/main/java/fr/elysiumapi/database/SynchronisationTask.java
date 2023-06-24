package fr.elysiumapi.database;

import fr.elysiumapi.spigot.ElysiumAPI;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class SynchronisationTask {

    public void startSynchronizingTask(int hours){
        new BukkitRunnable() {
            @Override
            public void run() {
                execute();
            }
        }.runTaskTimerAsynchronously(ElysiumAPI.getInstance(), 0, hours*60*60*20L);
    }

    public abstract void execute();
}