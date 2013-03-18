package mrj.advancedbackpackmod.client;

//import net.minecraftforge.client.MinecraftForgeClient;
import mrj.advancedbackpackmod.CommonProxy;

public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
                //MinecraftForgeClient.preloadTexture(ITEMS_PNG);
                //MinecraftForgeClient.preloadTexture(BLOCK_PNG);
                //MinecraftForgeClient.preloadTexture(BAG_PNG);
        }
       
}
