package net.prosavage;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import static com.earth2me.essentials.I18n.tl;

public class ChestKits extends JavaPlugin {


    private static ChestKits instance;
    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("ChestKits v" + getDescription().getVersion() + " - By ProSavage.");
        instance = this;
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
    }

    private boolean checkIfConfigExists() {
        File configFile = new File(getDataFolder(), "config.yml");

        return configFile.exists();

    }

    public static ChestKits getInstance() {
        return instance;
    }












}
