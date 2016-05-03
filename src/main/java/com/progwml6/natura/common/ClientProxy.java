package com.progwml6.natura.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import slimeknights.mantle.item.ItemBlockMeta;

public abstract class ClientProxy extends CommonProxy
{
    public static void initRenderer()
    {
    }

    protected void registerItemBlockMeta(Block block)
    {
        if (block != null)
        {
            ((ItemBlockMeta) Item.getItemFromBlock(block)).registerItemModels();
        }
    }
}
