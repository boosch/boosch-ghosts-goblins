package net.cboschen.booschgg.world.feature;

import net.cboschen.booschgg.BooschGGMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, BooschGGMod.MOD_ID);

    //now our registry objects - consider using 'absolute' with the vertical anchors if you see weirdness.
    public static final RegistryObject<PlacedFeature> OVERWORLD_ZIRCON_ORE_PLACED = PLACED_FEATURES.register("overworld_zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZIRCON_ORE.getHolder().get(),
                    commonOrePlacement(7, //count of veins per chunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80) //creates a distribution 'curve' with less distro at the 2nd parameter, and the most distro at the first param - think more ore the further you get towards -80
                            )
                    )
            )
    );
    public static final RegistryObject<PlacedFeature> END_ZIRCON_ORE_PLACED = PLACED_FEATURES.register("end_zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.END_ZIRCON_ORE.getHolder().get(),
                    commonOrePlacement(7, //count of veins per chunk
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80) //creates a distribution 'curve' with less distro at the 2nd parameter, and the most distro at the first param - think more ore the further you get towards -80
                            )
                    )
            )
    );
    public static final RegistryObject<PlacedFeature> NETHER_ZIRCON_ORE_PLACED = PLACED_FEATURES.register("nether_zircon_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_ZIRCON_ORE.getHolder().get(),
                    commonOrePlacement(7, //count of veins per chunk
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80) //creates a distribution 'curve' with less distro at the 2nd parameter, and the most distro at the first param - think more ore the further you get towards -80
                            )
                    )
            )
    );




    /*
        The following three methods interact to place the ore in the world when called borrowed from OrePlacements.java
     */
    public static List<PlacementModifier> orePlacement(PlacementModifier placementMod, PlacementModifier placementMod2){
        return List.of(placementMod, InSquarePlacement.spread(), placementMod2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int num, PlacementModifier placementMod){
        return orePlacement(CountPlacement.of(num), placementMod);
    }

    public static List<PlacementModifier> rareOrePlacement(int num, PlacementModifier placementMod){
        return orePlacement(RarityFilter.onAverageOnceEvery(num), placementMod);
    }



    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
