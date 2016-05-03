package com.progwml6.natura.world;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.world.block.trees.BlockEnumLog.EnumAxis;
import com.progwml6.natura.world.block.trees.BlockOverworldLogs;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class WorldClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        // Entities

        super.preInit();
    }

    @Override
    protected void registerModels()
    {
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        Item log = Item.getItemFromBlock(NaturaWorld.overworldLogs);
        for (BlockOverworldLogs.LogType type : BlockOverworldLogs.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s",
                    BlockOverworldLogs.LOG_AXIS.getName(),
                    BlockOverworldLogs.LOG_AXIS.getName(EnumAxis.Y),
                    BlockOverworldLogs.TYPE.getName(),
                    BlockOverworldLogs.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(log, type.meta, new ModelResourceLocation(log.getRegistryName(), variant));
        }

        registerItemBlockMeta(NaturaWorld.cloudBlock);
    }
}
