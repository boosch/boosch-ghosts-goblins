package net.cboschen.booschgg.event;

import net.cboschen.booschgg.BooschGGMod;
import net.cboschen.booschgg.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles events native to only the client in forge, such as keypressed events(example below for Thirst mechanic)
 */
public class ClientEvents {

    @Mod.EventBusSubscriber(modid = BooschGGMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key key){
            if(KeyBinding.DRINKING_KEY.consumeClick()){
                /*
                    We can do this because this method will only ever be called on the client; thus only one player.
                    If called on the server - errors, no keybindings exist on the server
                 */
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("You're tryin'a take a Drink! YARrr!"));

            }
        }
    }

    /**
     * subscribes to just events on the client's ModBus
     * By utilizing this eventBus, we can ensure that the registered key will appear in OPTIONS for the client.
     * If this method is implemented on the forge bus (thus subscribed to a different 'bus', that won't be done natively for us.
     */
    @Mod.EventBusSubscriber(modid = BooschGGMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.DRINKING_KEY);
        }
    }
}
