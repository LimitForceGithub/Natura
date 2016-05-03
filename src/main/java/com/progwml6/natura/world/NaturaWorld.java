package com.progwml6.natura.world;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.BlockCloud;
import com.progwml6.natura.world.block.trees.BlockOverworldLeaves;
import com.progwml6.natura.world.block.trees.BlockOverworldLogs;
import com.progwml6.natura.world.item.ItemBlockLeaves;
import com.progwml6.natura.world.material.CloudMaterial;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaWorld.PulseId, description = "Everything that's found in the world and worldgen")
public class NaturaWorld extends NaturaPulse
{
    public static final String PulseId = "NaturaWorld";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.world.WorldClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    public static BlockCloud cloudBlock;

    public static BlockOverworldLogs overworldLogs;

    public static BlockOverworldLeaves overworldLeaves;

    public static Material cloud = new CloudMaterial();

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        cloudBlock = registerEnumBlock(new BlockCloud(), "clouds");

        overworldLogs = registerEnumBlock(new BlockOverworldLogs(), "logs");

        overworldLeaves = registerBlock(new ItemBlockLeaves(new BlockOverworldLeaves()), "overworld_leaves");
        ItemBlockMeta.setMappingProperty(overworldLeaves, BlockOverworldLogs.TYPE);

        proxy.preInit();

        NaturaRegistry.tabGeneral.setDisplayIcon(new ItemStack(cloudBlock));
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        proxy.postInit();
    }
}
