package net.cboschen.booschgg.block.custom;

import net.cboschen.booschgg.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

//we want a 6 stage crop (vs 7)
public class BlueberryCropBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age",0,6);

    public BlueberryCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.BLUEBERRY_SEEDS.get();//super.getBaseSeedId();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
        //return super.getAgeProperty();
    }

    //we changed this to override or age behaviors
    @Override
    public int getMaxAge() {
        return 6;
    }

    //here we append our age property to the set of properties for this crop's blockstate
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(AGE);
        //super.createBlockStateDefinition(builder);
    }
}
