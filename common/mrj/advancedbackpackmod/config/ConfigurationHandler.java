package mrj.advancedbackpackmod.config;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {

	public static Configuration myConfig;
	
	public static void init(File file) {

		myConfig = new Configuration(file);
		
		try
		{
			myConfig.load();
			ConfigurationStore.BACKPACK_BASE = myConfig.getItem("backpackbase", ConfigurationStore.BACKPACK_BASE_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_DEFAULT);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Config loading for AdvancedBackpackMod failed");
		}
		finally
		{
			myConfig.save();
		}
		
	}

}
