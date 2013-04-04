package mrj.advancedbackpackmod;

import java.io.File;

import mrj.advancedbackpackmod.config.ConfigurationHandler;
import mrj.advancedbackpackmod.config.ConfigurationStore;
import mrj.advancedbackpackmod.recipe.RecipeColorBackpack;
import mrj.advancedbackpackmod.recipe.RecipeExtendBackpackMagic;
//import mrj.advancedbackpackmod.recipe.RecipeExtendBackpack;
import mrj.advancedbackpackmod.recipe.RecipeExtendBackpackBase;

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

/**
 * Advanced Backpack Mod
 * 
 * AdvancedBackpackMod
 * 
 * @author MrJ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid="AdvancedBackpackMod", name="Advanced Backpack Mod", version="0.0.1a")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class AdvancedBackpackMod {

	@Instance("AdvancedBackpackMod")
	public static AdvancedBackpackMod instance;
	
	public static Item baseBackpack;
	public static Item magicBackpack;
	public static Item powerCore;
	
	@SidedProxy(clientSide="mrj.advancedbackpackmod.client.ClientProxy", serverSide="mrj.advancedbackpackmod.CommonProxy")
    public static CommonProxy proxy;
   
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
    	ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/abm/" + "AdvancedBackpackMod" + ".cfg"));
    }
   
    @Init
    public void load(FMLInitializationEvent event) {
    	
    	proxy.registerRenderers(); 	
    	
    	baseBackpack = new ItemBackpackBase(ConfigurationStore.BACKPACK_BASE_ID - 256).setUnlocalizedName("Bag of Holding");
        LanguageRegistry.addName(baseBackpack, "Bag of Holding");	        
        magicBackpack = new ItemBackpackMagic(ConfigurationStore.BACKPACK_MAGIC_ID - 256).setUnlocalizedName("Portable Pocketdimension");
        LanguageRegistry.addName(magicBackpack, "Portable Pocketdimension");	
        powerCore = new ItemNetherPowerCore(22102-256).setUnlocalizedName("Nether Power Core");
        LanguageRegistry.addName(powerCore, "Nether Power Core");
        
        
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        
        GameRegistry.addRecipe(new ItemStack(baseBackpack), "aba", "aca", "ada", 'a', new ItemStack(Item.leather),
        		'b', new ItemStack(Item.enderPearl), 'c', new ItemStack(Block.chest), 'd', new ItemStack(Item.emerald));
        GameRegistry.addRecipe(new ItemStack(powerCore), "aaa", "aba", "aaa", 'a', new ItemStack(Item.redstone), 
        		'b', new ItemStack(Item.netherStar));
        GameRegistry.addRecipe(new ItemStack(magicBackpack), "aba", "aca", "ada", 'a', new ItemStack(Item.leather),
        		'b', new ItemStack(Item.enderPearl), 'c', new ItemStack(Block.enderChest), 'd', new ItemStack(powerCore));
        
        CraftingManager.getInstance().getRecipeList().add(new RecipeExtendBackpackBase());
        CraftingManager.getInstance().getRecipeList().add(new RecipeExtendBackpackMagic());
        CraftingManager.getInstance().getRecipeList().add(new RecipeColorBackpack());
    }
   
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
}
