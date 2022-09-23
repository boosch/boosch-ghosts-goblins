package net.cboschen.booschgg.item;

import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.block.ModBlocks;
import net.cboschen.booschgg.item.custom.EightBallItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.commands.arguments.MobEffectArgument.effect;

/**
 * Houses Declarations and registration of all Items added by the mod (that are not blockItems)
 */
public class ModItems {
    //set up a register for my mods items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BooschGGMod.MOD_ID);

    //example mod item: Zircon - for an item with no special use behaviors
    //example item properties added during this pattern
    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new BooschGGItem(new Item.Properties().tab(ModCreativeModeTab.BOOSCHGG_TAB)));
    public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon",
            () -> new BooschGGItem(new Item.Properties().tab(ModCreativeModeTab.BOOSCHGG_TAB)));

    //example moditem eightball for an item with special behaviors
    public static final RegistryObject<Item> EIGHTBALL = ITEMS.register("eightball",
            () -> new EightBallItem(new Item.Properties().tab(ModCreativeModeTab.BOOSCHGG_TAB).stacksTo(1)));

    //example blueberry seeds & crop for the blueberry crop
    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new BooschGGItem.Properties()
                            .tab(ModCreativeModeTab.BOOSCHGG_TAB)
                            .stacksTo(64)
            )
    );
    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
            () -> new BooschGGItem(new Item.Properties()
                    .tab(ModCreativeModeTab.BOOSCHGG_TAB)
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(2f)
                            .effect( () -> new MobEffectInstance(MobEffects.LUCK, 200, 2), 1f)
                                    .build()
                            )
            )
    );


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
