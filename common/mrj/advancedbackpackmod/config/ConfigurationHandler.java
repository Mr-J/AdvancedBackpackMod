package mrj.advancedbackpackmod.config;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;

import net.minecraftforge.common.Configuration;

/**
 * Advanced Backpack Mod
 * 
 * ConfigurationHandler
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ConfigurationHandler {

	public static Configuration myConfig;
	
	public static void init(File file) {

		myConfig = new Configuration(file);
		
		try
		{
			myConfig.load();
			/* item ids*/
			ConfigurationStore.BACKPACK_BASE_ID = myConfig.getItem("backpackbase", ConfigurationStore.BACKPACK_BASE_ID_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_ID_DEFAULT);
			ConfigurationStore.BACKPACK_MAGIC_ID = myConfig.getItem("backpackmagic", ConfigurationStore.BACKPACK_MAGIC_ID_DEFAULT).getInt(ConfigurationStore.BACKPACK_MAGIC_ID_DEFAULT);
			/* general settings*/
			ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE = myConfig.get("general", "fixed_column_size", ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE_DEFAULT).getBoolean(ConfigurationStore.BACKPACK_FIXED_COLUMN_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_BASE_START_SIZE = myConfig.get("general", "base_start_size", ConfigurationStore.BACKPACK_BASE_START_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_START_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_BASE_MAX_SIZE = myConfig.get("general", "base_max_size", ConfigurationStore.BACKPACK_BASE_MAX_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_MAX_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT = myConfig.get("general", "base_upgrade_increment", ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT_DEFAULT).getInt(ConfigurationStore.BACKPACK_BASE_UPGRADE_INCREMENT_DEFAULT);
			
			ConfigurationStore.BACKPACK_MAGIC_START_SIZE = myConfig.get("general", "magic_start_size", ConfigurationStore.BACKPACK_MAGIC_START_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_MAGIC_START_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE = myConfig.get("general", "magic_max_size", ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE_DEFAULT).getInt(ConfigurationStore.BACKPACK_MAGIC_MAX_SIZE_DEFAULT);
			ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT = myConfig.get("general", "magic_upgrade_increment", ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT_DEFAULT).getInt(ConfigurationStore.BACKPACK_MAGIC_UPGRADE_INCREMENT_DEFAULT);
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
