package com.progwml6.natura.world;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents
{
    @SubscribeEvent
    public void bonemealEvent(BonemealEvent event)
    {

    }

    @SubscribeEvent
    public void chunkDataSave(ChunkDataEvent.Save event)
    {
        event.getData().setBoolean("Natura.Retrogen", true);
    }
}
