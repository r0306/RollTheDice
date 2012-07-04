package com.github.r0306.RollTheDice;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.r0306.RollTheDice.DiceHandlers._1;
import com.github.r0306.RollTheDice.DiceHandlers._2;
import com.github.r0306.RollTheDice.DiceHandlers._3;
import com.github.r0306.RollTheDice.DiceHandlers._4;
import com.github.r0306.RollTheDice.Disguise.DisguiseListeners;
import com.github.r0306.RollTheDice.Handlers.PlayerHandlers;
import com.github.r0306.RollTheDice.Util.Colors;

public class RollTheDice extends JavaPlugin implements Colors
{
	
	public static String version = "";
	public static String website = "";
	private Executor myExecutor;
	
	public void onEnable()
	{
		
		loadConfig();
		version = getDescription().getVersion();
		website = getDescription().getWebsite();
    	myExecutor = new Executor(this);
		getCommand("rtd").setExecutor(myExecutor);
		getServer().getPluginManager().registerEvents(new PlayerHandlers(this), this);
		getServer().getPluginManager().registerEvents(new DisguiseListeners(), this);
		registerAllDiceEvents(getServer().getPluginManager());
		System.out.println("RollTheDice version [" + getDescription().getVersion() + "] loaded.");
		
	}
	
	public void onDisable()
	{
		
		System.out.println("RollTheDice version [" + getDescription().getVersion() + "] unloaded.");
		
	}
	
	public void loadConfig()
	{
		
		List<String> list = new ArrayList<String>();
		
		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		cfg.addDefault("RTD.World", Bukkit.getServer().getWorlds().get(0).getName());
		cfg.addDefault("RTD.Enabled", true);
		cfg.addDefault("RTD.Minimum", 5);
		cfg.addDefault("RTD.Delay", 60);
		cfg.addDefault("RTD.Time-Limit", 10);
		cfg.createSection("Data");
		cfg.addDefault("Players.List", list);
		cfgOptions.copyDefaults(true);
		cfgOptions.header("This is the RollTheDice configuration file.");
		cfgOptions.copyHeader(true);
		saveConfig();
		
	}
	
	public void registerAllDiceEvents(PluginManager pm)
	{
		
		pm.registerEvents(new _1(), this);
		pm.registerEvents(new _2(), this);
		pm.registerEvents(new _3(this), this);
		pm.registerEvents(new _4(), this);
		
	}

}
