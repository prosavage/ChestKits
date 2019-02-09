package net.prosavage.savagekits;

import net.prosavage.savagekits.gson.Persist;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class ChestKits extends JavaPlugin {


    private static ChestKits instance;
    private static Logger logger;
    private Persist persist;



    @Override
    public void onEnable() {
        logger = this.getLogger();
        logger.info("ChestKits v" + getDescription().getVersion() + " - By ProSavage.");
        instance = this;
        persist = new Persist();
        Kits.load();
        logger.info("Checking Configuration File...");
        if (!checkIfConfigExists()) {
            getLogger().info("Configuration file was not found!");
            getLogger().info("Creating config.yml...");
            saveResource("config.yml", true);
        } else {
            getLogger().info("Configuration File Found!");
        }
        logger.info("Registering Listeners.");
        getServer().getPluginManager().registerEvents(new CommandInterceptor(), this);
        getServer().getPluginManager().registerEvents(new RedeemListener(), this);

    }


    @Override
    public void onDisable() {
        logger.info("Disabling ChestKits.");
        Kits.save();
        logger.info("Saved Kits");
    }

    private boolean checkIfConfigExists() {
        File configFile = new File(getDataFolder(), "config.yml");
        return configFile.exists();
    }

    public Persist getPersist() {
        return persist;
    }

    public static ChestKits getInstance() {
        return instance;
    }


}
