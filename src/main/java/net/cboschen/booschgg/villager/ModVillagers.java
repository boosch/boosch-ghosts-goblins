package net.cboschen.booschgg.villager;

import com.google.common.collect.ImmutableSet;
import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

/*
    Going to add a Villager with a Profession
    There's this, Registry in BooschGGMod, and in common setup there...
    ...several JSON, texture (not model), tag(job POI)
    ...ModEvents bus subscribing too...

 */
public class ModVillagers {

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

    /*
        the predicates (x -> x.get()) - in line refer to the primary and secondary worksites for villagers with this profession
        The sets are what he wants to trade/accept (requested items), and where he works
     */
    public static final RegistryObject<VillagerProfession> JUMP_MASTER = VILLAGER_PROFESSIONS.register("jump_master",
            () -> new VillagerProfession("jump_master",
                    x -> x.get() == JUMPY_BLOCK_POI.get(),
                    x -> x.get() == JUMPY_BLOCK_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER));
    // example of above for farmer, from VillagerProfession class:
    //   public static final VillagerProfession FARMER = register("farmer", PoiTypes.FARMER, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.BONE_MEAL), ImmutableSet.of(Blocks.FARMLAND), SoundEvents.VILLAGER_WORK_FARMER);


    public static void registerPOIs(){
        try{
            ObfuscationReflectionHelper.findMethod(PoiType.class,
                    "registerBlockStates", PoiType.class).invoke( null, JUMPY_BLOCK_POI.get() );
        }catch(InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
