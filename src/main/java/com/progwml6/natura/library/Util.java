package com.progwml6.natura.library;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;

public class Util
{
    public static final String MODID = "natura";

    public static final String RESOURCE = MODID.toLowerCase(Locale.US);

    public static Logger getLogger(String type)
    {
        String log = MODID;

        return LogManager.getLogger(log + "-" + type);
    }

    public static String prefix(String name)
    {
        return String.format("%s.%s", RESOURCE, name.toLowerCase(Locale.US));
    }

    public static String resource(String res)
    {
        return String.format("%s:%s", RESOURCE, res);
    }

    public static ResourceLocation getResource(String res)
    {
        return new ResourceLocation(RESOURCE, res);
    }
}
