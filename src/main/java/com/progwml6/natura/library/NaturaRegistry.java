package com.progwml6.natura.library;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.CreativeTab;

public final class NaturaRegistry
{
	private NaturaRegistry()
	{
	}

	public static CreativeTab tabGeneral = new CreativeTab("NaturaGeneral", new ItemStack(Items.SLIME_BALL));
}
