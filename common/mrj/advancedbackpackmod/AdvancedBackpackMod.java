package mrj.advancedbackpackmod;

import net.minecraft.item.Item;
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
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="AdvancedBackpackMod", name="Advanced Backpack Mod", version="0.0.1a")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class AdvancedBackpackMod {

	@Instance("AdvancedBackpackMod")
	public static AdvancedBackpackMod instance;
	
	private final static Item baseBackpack = new ItemBackpackBase(5000);
	
	@SidedProxy(clientSide="mrj.advancedbackpackmod.client.ClientProxy", serverSide="mrj.advancedbackpackmod.CommonProxy")
    public static CommonProxy proxy;
   
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
            // Stub Method
    }
   
    @Init
    public void load(FMLInitializationEvent event) {
            proxy.registerRenderers();
            LanguageRegistry.addName(baseBackpack, "basic backpack");	
            NetworkRegistry.instance().registerGuiHandler(instance, proxy);
    }
   
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
            // Stub Method
    }
}
