package net.cboschen.booschgg.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Blockitems extend this, and blocks use this when registering their items, to get uniform behaviors such as 'Hold SHIFT for more info' on hover
 */
public class BooschGGBlockItem extends BlockItem {
    public BooschGGBlockItem(Block block, Properties properties) {
        super(block, properties);
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> componentList, TooltipFlag flag) {
        //this would be better done:
        // A: as a global method for all moditems
        // B: using translatable strings
        if(Screen.hasShiftDown()){
            componentList.add(Component.translatable("item.booschgg.generic.credits").withStyle(ChatFormatting.AQUA));
        }
        else{
            componentList.add(Component.translatable("item.booschgg.generic.moreinfo").withStyle(ChatFormatting.YELLOW));
            //componentList.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }
        super.appendHoverText(itemStack, level, componentList, flag);
    }
}
