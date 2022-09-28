package net.cboschen.booschgg.networking;

import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.networking.packet.DrinkWaterC2SPacket;
import net.cboschen.booschgg.networking.packet.ExampleC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

//this could be named anything
public class ModMessages {
    private static SimpleChannel INSTANCE;

    //solves for unique message ids simply.
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    //we made this, so we have to call it in BooschGGMod or it does nothing.
    public static void register(){
        //boilerplate
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(BooschGGMod.MOD_ID, "messages"))
                .networkProtocolVersion( () -> "1.0") //string supplier, arbitrary, used to track your message versions in the event of changes thereto; when changed, should be updated in that message.
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        //registers our example message about cow spawning
        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

        //registers our  message about trying to drink
        net.messageBuilder(DrinkWaterC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkWaterC2SPacket::new)
                .encoder(DrinkWaterC2SPacket::toBytes)
                .consumerMainThread(DrinkWaterC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG serverMessage){
        INSTANCE.sendToServer(serverMessage);
    }

    public static <MSG> void sendToPlayer(MSG playerMessage, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), playerMessage);
    }

}
