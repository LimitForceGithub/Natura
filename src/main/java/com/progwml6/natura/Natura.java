package com.progwml6.natura;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.NaturaWorld;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.pulsar.control.PulseManager;

@Mod(modid = Util.MODID, name = "Natura", version = "3.0.0", guiFactory = "com.progwml6.natura.common.config.ConfigGui$ConfigGuiFactory", dependencies = "required-after:Forge@[12.16.0.1850,);" + "required-after:mantle@[1.9-0.9.4,)", acceptedMinecraftVersions = "[1.9,]")
public class Natura
{
    /* Instance of this mod, used for grabbing prototype fields */
    @Instance(Util.MODID)
    public static Natura instance;

    public static PulseManager pulseManager = new PulseManager(Config.pulseConfig);

    static
    {
        pulseManager.registerPulse(new NaturaWorld());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.load(event);
    }

}
