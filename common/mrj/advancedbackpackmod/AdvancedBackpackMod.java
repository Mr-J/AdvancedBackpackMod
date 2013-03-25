package mrj.advancedbackpackmod;

import java.io.File;

import mrj.advancedbackpackmod.config.ConfigurationHandler;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import mrj.advancedbackpackmod.recipe.RecipeExtendBackpack;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="AdvancedBackpackMod", name="Advanced Backpack Mod", version="0.0.1a")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class AdvancedBackpackMod {

	@Instance("AdvancedBackpackMod")
	public static AdvancedBackpackMod instance;
	
	public static Item baseBackpack;
	
	@SidedProxy(clientSide="mrj.advancedbackpackmod.client.ClientProxy", serverSide="mrj.advancedbackpackmod.CommonProxy")
    public static CommonProxy proxy;
   
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    	ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/abm/" + "AdvancedBackpackMod" + ".cfg"));
    }
   
    @Init
    public void load(FMLInitializationEvent event) {
    	
    	 	baseBackpack = new ItemBackpackBase(ConfigurationStore.BACKPACK_BASE - 256);
            proxy.registerRenderers();
            LanguageRegistry.addName(baseBackpack, "Backpack");	
            NetworkRegistry.instance().registerGuiHandler(instance, proxy);
            
            GameRegistry.addRecipe(new ItemStack(baseBackpack), "aba", "aca", "ada", 'a', new ItemStack(Item.leather),
            		'b', new ItemStack(Item.enderPearl), 'c', new ItemStack(Block.chest), 'd', new ItemStack(Item.emerald));
            
            CraftingManager.getInstance().getRecipeList().add(new RecipeExtendBackpack());
    }
   
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
}
