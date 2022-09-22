package net.cboschen.booschgg.item.custom;

import net.cboschen.booschgg.item.BooschGGItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EightBallItem extends BooschGGItem {
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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag flag) {
        //this would be better done:
        // A: as a global method for all moditems
        // B: using translatable strings
        if(Screen.hasShiftDown()){
            componentList.add(Component.translatable("item.booschgg.eightball.description").withStyle(ChatFormatting.AQUA));
        }
        //Below is done by the parent. Overwriting above (the has shift down) is how we'll do item-specific tips when needed.
        /*
        else{
            componentList.add(Component.translatable("item.booschgg.generic.moreinfo").withStyle(ChatFormatting.YELLOW));
            //componentList.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }
         */
        super.appendHoverText(itemStack, level, componentList, flag);
    }
}
