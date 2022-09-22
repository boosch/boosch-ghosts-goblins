package net.cboschen.booschgg.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Introduces a new type of block using block properties
 * The property LIT will be used to indicate the light-emitting-state of a given blockstate (block in the world) instance of this Block
 * see Blockstate json file for lamp to understand how the property is checked for display
 * see ModBlock declaration for property initialization on all blockstate instances of this block
 */
public class ZirconLampBlock extends Block {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public ZirconLampBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide() &&
        hand.equals(InteractionHand.MAIN_HAND) &&
        player.getMainHandItem() == ItemStack.EMPTY){
            //state.cycle basically iterates throgh all valid values of the state's property's values - in this case, toggles TRUE & FALSE because its a boolean property
            //last parameter indicates what the world notifies (client only? server only?both?other?)
            level.setBlock(blockPos, blockState.cycle(LIT), 3);
        }
        return super.use(blockState, level, blockPos, player, hand, hitResult);
    }

}
