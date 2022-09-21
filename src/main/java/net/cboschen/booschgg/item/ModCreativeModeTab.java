package net.cboschen.booschgg.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab BOOSCHGG_TAB = new CreativeModeTab("booschggtab") {
        @Override
        public ItemStack makeIcon() {
            //icon for the mod
            return new ItemStack(ModItems.ZIRCON.get());
        }
    };

    /*
    public static final CreativeModeTab BOOSCHGG_TAB2 = new CreativeModeTab("booschggtab_2") {
        @Override
        public ItemStack makeIcon() {
            //icon for the mod
            return new ItemStack(ModItems.RAW_ZIRCON.get());
        }
    };
    */
}
