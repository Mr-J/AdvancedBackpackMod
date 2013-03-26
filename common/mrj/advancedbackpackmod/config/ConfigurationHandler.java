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
			/* item ids*/
			ConfigurationStore.BACKPACK_BASE_ID = myConfig.getItem("backpackbase", ConfigurationStore.BACKPACK_BASE_ID_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_ID_DEFAULT);
			/* general settings*/
			ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE = myConfig.get("general", "fixed_column_size", ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE_DEFAULT).getBoolean(ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_BASE_SIZE = myConfig.get("general", "base_size", ConfigurationStore.BACKPACK_BASE_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_MAX_SIZE = myConfig.get("general", "max_size", ConfigurationStore.BACKPACK_MAX_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_MAX_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_UPGRADE_INCREMENT = myConfig.get("general", "upgrade_increment", ConfigurationStore.BACKPACK_UPGRADE_INCREMENT_DEFAULT).getInt(ConfigurationStore.BACKPACK_UPGRADE_INCREMENT_DEFAULT);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Config loading for AdvancedBackpackMod failed");
		}
		finally
		{
			myConfig.save();
		}
		
		//ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE = ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE_DEFAULT;
		
	}

}
