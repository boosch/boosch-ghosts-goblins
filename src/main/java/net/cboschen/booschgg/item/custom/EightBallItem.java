package net.cboschen.booschgg.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EightBallItem extends Item {
    public EightBallItem( Properties properties){
        super (properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        //run on *server*, and only if this is in the *right hand*; spawning something? it's server side. Showing graphics? client side.
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){

            //gen a number & set a cooldown
            outputRandomNumberToPlayer(player);
            player.getCooldowns().addCooldown(this,30);
        }
        return super.use(level, player, hand);
    }

    private void outputRandomNumberToPlayer(Player player){
        player.sendSystemMessage(Component.literal("You're number is "+ getRandomNumber()));
    }

    private int getRandomNumber(){
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
