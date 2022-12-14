package net.cboschen.booschgg;

import com.mojang.logging.LogUtils;
import net.cboschen.booschgg.block.ModBlocks;
import net.cboschen.booschgg.item.ModItems;
import net.cboschen.booschgg.networking.ModMessages;
import net.cboschen.booschgg.villager.ModVillagers;
import net.cboschen.booschgg.world.feature.ModConfiguredFeatures;
import net.cboschen.booschgg.world.feature.ModPlacedFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BooschGGMod.MOD_ID) //constructed via this tutorial: https://www.youtube.com/watch?v=LpoSy091wYI&t=303s&ab_channel=ModdingbyKaupenjoe
public class BooschGGMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "booschgg";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    /*
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    */

    public BooschGGMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        /*
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        */

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {


        //add custom messages and custom points of interest. This operation is not threadsafe, so is here in queued work
        event.enqueueWork( () -> {
            ModMessages.register();
            ModVillagers.registerPOIs();
            LOGGER.info("POI REGISTRATION COMPLETED");
        });

        //add custom compostables. This operation si not threadsafe, so is here in queued work
        //this could be merged up, but the order is important(?)
        event.enqueueWork( () -> {
            ComposterBlock.COMPOSTABLES.putIfAbsent(ModItems.BLUEBERRY.get(), 0.5f);
            ComposterBlock.COMPOSTABLES.putIfAbsent(ModItems.BLUEBERRY_SEEDS.get(), 0.3f);
            LOGGER.info("COMPOSTABLES REGISTRATION COMPLETED");
        });

        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            //seethru parts of our crop will be white if this is skipped. See if this needs to be in model of blockstate or block...
            //ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUEBERRY_CROP.get(), RenderType.cutout());

            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    /*
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    */

}
