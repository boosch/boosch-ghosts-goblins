package net.cboschen.booschgg.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.item.ModItems;
import net.cboschen.booschgg.villager.ModVillagers;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Example of how to hijack the trades modifiers for an existing villager type and insert your own trades
 * Follows that an example for our modded profession
 */
@Mod.EventBusSubscriber (modid = BooschGGMod.MOD_ID)
public class ModEvents {

    /**
     * This is a super weak way to override trades.
     * Triggers on every VillagerTradeEvent to check if we should be adding some trades or not.
     * ... There must be a better way ...
     * @param event
     */
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){

        /*
        // Example for modifying another existing villager's trades
        if(event.getType() == VillagerProfession.TOOLSMITH){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.EIGHTBALL.get(), 12);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    stack,
                    10,
                    8,
                    0.02F)
            );
        }*/

        if(event.getType() == ModVillagers.JUMP_MASTER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.BLUEBERRY_SEEDS.get(), 6);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    stack,
                    10, //max uses
                    8, //xp yielded
                    0.02F) //price multiplier
            );
        }

        if(event.getType() == ModVillagers.JEWELER.get()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Items.GLASS, 9);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    stack,
                    10, //max uses
                    8, //xp yielded
                    0.02F) //price multiplier
            );
        }

        if(event.getType() == ModVillagers.BANKER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(ModItems.COPPER_NUGGET.get(), 9);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    stack,
                    10, //max uses
                    8, //xp yielded
                    0.02F) //price multiplier
            );


            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(ModItems.COPPER_NUGGET.get(), 9),
                    new ItemStack(Items.EMERALD, 1),
                    10, //max uses
                    8, //xp yielded
                    0.02F) //price multiplier
            );
        }
    }
}
