package com.progwml6.natura.shared;

import org.apache.logging.log4j.Logger;

import com.progwml6.natura.library.Util;

import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaCommons.PulseId, forced = true)
public class NaturaCommons
{
    public static final String PulseId = "NaturaCommons";

    static final Logger log = Util.getLogger(PulseId);
}
