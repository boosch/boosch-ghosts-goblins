package net.cboschen.booschgg.villager;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;

/*
    Going to add a Villager with a Profession
    There's this, Registry in BooschGGMod, and in common setup there...
    ...several JSON, texture (not model), tag(job POI)
    ...ModEvents bus subscribing too...

    :Done - Jump Master, uses jumpy block, sells mod-items
    :Jeweler, uses Jeweler's Bench, buys glass, diamonds, emeralds, amythist, sells gold, emeralds, copper(coins?)
    :Banker, uses Ledger Bench, buys  copper nuggets(coins?), gold nuggets(coins?), sells iron nuggets(coins?), Diamonds
 */
public class ModVillagers {

    private static final Logger LOGGER = LogUtils.getLogger();

    //register obj for things that villagers can look at
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, BooschGGMod.MOD_ID);
    //register obj for the villager profession itself
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, BooschGGMod.MOD_ID);

    /*register obj for what the target (point of interest) blockstate as a point of interest
        with the below definition, a villager can use this block with ANY state - if we wanted to change this (think the zircon lamp example)
        we'd need to specify the set of valid blockstates that are permitted for use

        Tickets refers to the count of villagers who can get this job from one POI
        Valid range is the range for discovery for the villager.
     */
    public static final RegistryObject<PoiType> JUMPY_BLOCK_POI = POI_TYPES.register("jumpy_block_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.JUMPY_BLOCK.get().getStateDefinition().getPossibleStates()), 1, 1));

    //for the jeweler's bench - all vanilla pois have 1, 1 for the last two parameters.
    public static final RegistryObject<PoiType> JEWELER_BENCH_POI = POI_TYPES.register("jeweler_bench_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.JEWELER_BENCH.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final RegistryObject<PoiType> BANKER_BENCH_POI = POI_TYPES.register("banker_bench_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.BANKER_BENCH.get().getStateDefinition().getPossibleStates()), 1,1));

    /*
        the predicates (x -> x.get()) - in line refer to the primary and secondary worksites for villagers with this profession
        The sets are what he wants to trade/accept (requested items), and where he works
     */
    public static final RegistryObject<VillagerProfession> JUMP_MASTER = VILLAGER_PROFESSIONS.register("jump_master",
            () -> new VillagerProfession("jump_master",
                    x -> x.get() == JUMPY_BLOCK_POI.get(),
                    x -> x.get() == JUMPY_BLOCK_POI.get(),
                    ImmutableSet.of(), // what I will run and grab
                    ImmutableSet.of(), //where I will work
                    SoundEvents.VILLAGER_WORK_ARMORER));
    // example of above for farmer, from VillagerProfession class:
    //   public static final VillagerProfession FARMER = register("farmer", PoiTypes.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.BONE_MEAL), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.VILLAGER_WORK_FARMER);

    //jeweler implementation
    public static final RegistryObject<VillagerProfession> JEWELER = VILLAGER_PROFESSIONS.register("jeweler",
            () -> new VillagerProfession("jeweler",
                    x -> x.get() == JEWELER_BENCH_POI.get(),
                    x -> x.get() == JEWELER_BENCH_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CARTOGRAPHER));
    //banker implementation
    public static final RegistryObject<VillagerProfession> BANKER = VILLAGER_PROFESSIONS.register("banker",
            () -> new VillagerProfession("banker",
                    x -> x.get() == BANKER_BENCH_POI.get(),
                    x -> x.get() == BANKER_BENCH_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CARTOGRAPHER));


    public static void registerPOIs(){
        try{
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke( null, JUMPY_BLOCK_POI.get() );
            LOGGER.info("VILLAGER POI JUMP_MASTER REGISTERED~~~~~~~~~~~~~~~~~~");
        }catch(InvocationTargetException | IllegalAccessException e){
            LOGGER.info("VILLAGER POI JUMP_MASTER ERROR~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
        }
        try{
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, JEWELER_BENCH_POI.get() );

            LOGGER.info("VILLAGER POI JEWELER REGISTERED~~~~~~~~~~~~~~~~~~");
        }catch(InvocationTargetException | IllegalAccessException e){
            LOGGER.info("VILLAGER POI JEWELER ERROR~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
        }
        try{
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke(null, BANKER_BENCH_POI.get() );

            LOGGER.info("VILLAGER POI BANKER REGISTERED~~~~~~~~~~~~~~~~~~");
        }catch(InvocationTargetException | IllegalAccessException e){
            LOGGER.info("VILLAGER POI BANKER ERROR~~~~~~~~~~~~~~~~~~");
            e.printStackTrace();
        }
    }
    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
