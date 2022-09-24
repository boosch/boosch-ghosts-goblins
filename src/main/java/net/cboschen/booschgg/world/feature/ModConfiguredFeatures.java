package net.cboschen.booschgg.world.feature;

import com.google.common.base.Suppliers;
import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGRED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, BooschGGMod.MOD_ID);

    /*
        This is a supplier of a list of target blockstates for ore configurations in the world -
            IE: what's the list of (paired) things you want to replace, and what do you want to replace them with?
            Doing this as a supplier "ensures it loads in the right order" (??)
     */
    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_ZIRCON_ORES = Suppliers.memoize( () -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ZIRCON_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ZIRCON_ORE.get().defaultBlockState())
    ));

    //note, here we can't use a replaceable for endstone; instead we can match on the type of block we want to replace.
    public static final Supplier<List<OreConfiguration.TargetBlockState>> END_ZIRCON_ORES = Suppliers.memoize( () -> List.of(
            OreConfiguration.target( new BlockMatchTest(Blocks.END_STONE), ModBlocks.END_ZIRCON_ORE.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> NETHER_ZIRCON_ORES = Suppliers.memoize( () -> List.of(
            OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.NETHER_ZIRCON_ORE.get().defaultBlockState())
    ));

    //now we make our registry objects for those suppliers...
    public static final RegistryObject<ConfiguredFeature<?,?>> ZIRCON_ORE = CONFIGRED_FEATURES.register("zircon_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_ZIRCON_ORES.get(), 5)));
    public static final RegistryObject<ConfiguredFeature<?,?>> END_ZIRCON_ORE = CONFIGRED_FEATURES.register("endstone_zircon_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(END_ZIRCON_ORES.get(), 9)));
    public static final RegistryObject<ConfiguredFeature<?,?>> NETHER_ZIRCON_ORE = CONFIGRED_FEATURES.register("netherrack_zircon_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(NETHER_ZIRCON_ORES.get(), 8)));



    public static void register(IEventBus eventBus){
        CONFIGRED_FEATURES.register(eventBus);
    }
}
