package net.cboschen.booschgg.networking.packet;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

import java.util.function.Supplier;


//example client to server packet; purely for reference, not involved in main mod
public class DrinkWaterC2SPacket {

    private static final String MESSAGE_DRINK_WATER = "message.booschgg.drinkwater.drink";
    private static final String MESSAGE_NO_WATER_NEARBY = "message.booschgg.drinkwater.none_nearby";

    public DrinkWaterC2SPacket(){

    }

    //contains "additional" data sent from client to server
    //blank for now...
    public DrinkWaterC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    //silly mechanic to summon a cow by the server(?).

    /**
     * we're always on the server here, so we can do server things, but not client things.
     * @param supplier
     * @return
     */
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( () -> {
          //IN THIS, WE ARE IN THE SERVER CONTEXT ONLY
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            //check if the player is near (within 2 blocks) water - allow drink, prohibit drink
            if(hasWaterNearby(player, level, 1)){
                //notify the player about water
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));

                //increase the player's water level / decrease their thirst

                //play drinking sound

                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat()+0.1F+0.9F);

                //Output their thirst at their location - all players nearby will hear this(?)
            }
            else{
                //alert the player: no water
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER_NEARBY).withStyle(ChatFormatting.DARK_PURPLE));

                //Output their thirst

            }
        });
        return true;
    }

    private boolean hasWaterNearby(ServerPlayer player, ServerLevel level, int size){

        //returns a series of blockstates around (3 dim?) the player & check if there is water
        // gets the set of blockstates matching water as an array, if that's nonzero, we gucci
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length  >0;
    }
}
