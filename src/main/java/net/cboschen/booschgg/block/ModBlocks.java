package net.cboschen.booschgg.block;

import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.block.custom.JumpyBlock;
import net.cboschen.booschgg.block.custom.ZirconLampBlock;
import net.cboschen.booschgg.item.ModCreativeModeTab;
import net.cboschen.booschgg.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Houses declaration and registration of all blocks added by this mod
 */
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BooschGGMod.MOD_ID);

    //basic block, won't drop itself unless there's a correct tool specified (incomplete)
    //a block needs a registered entry, a blockstates.json, a block.json, a item file, and textures
    public static final RegistryObject<Block> ZIRCON_BLOCK = registerBlock("zircon_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(6f).requiresCorrectToolForDrops()), ModCreativeModeTab.BOOSCHGG_TAB);

    //exp-dropping ore blocks
    public static final RegistryObject<Block> ZIRCON_ORE = registerBlock("zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3,7)), ModCreativeModeTab.BOOSCHGG_TAB);
    public static final RegistryObject<Block> DEEPSLATE_ZIRCON_ORE = registerBlock("deepslate_zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3,7)), ModCreativeModeTab.BOOSCHGG_TAB);

    public static final RegistryObject<Block> JUMPY_BLOCK = registerBlock("jumpy_block",
            () -> new JumpyBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(2f)
                    .requiresCorrectToolForDrops()), ModCreativeModeTab.BOOSCHGG_TAB);

    public static final RegistryObject<Block> ZIRCON_LAMP = registerBlock("zircon_lamp",
            () -> new ZirconLampBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f)
                    .lightLevel(state -> state.getValue(ZirconLampBlock.LIT) ? 15:0)), ModCreativeModeTab.BOOSCHGG_TAB);


    //using Generics here, allows this method to return any type of RegistryObject which extends Block
    // registers a block's item with the block.
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }

    //called when registering a block, registers its item
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BooschGGBlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
