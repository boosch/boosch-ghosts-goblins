package net.cboschen.booschgg.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


//example client to server packet; purely for reference, not involved in main mod
public class ExampleC2SPacket {
    public ExampleC2SPacket(){

    }

    //contains "additional" data sent from client to server
    //blank for now...
    public ExampleC2SPacket(FriendlyByteBuf buf){

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

            EntityType.COW.spawn(level, null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);
                });
        return true;
    }
}
