package com.progwml6.natura.world.block.trees;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockEnumLog<T extends Enum<T> & EnumBlock.IEnumMeta & IStringSerializable> extends EnumBlock<T>
{
	public static final PropertyEnum<BlockEnumLog.EnumAxis> LOG_AXIS = PropertyEnum.<BlockEnumLog.EnumAxis> create("axis", BlockEnumLog.EnumAxis.class);

	public BlockEnumLog(PropertyEnum<T> prop, Class<T> clazz)
	{
		super(Material.WOOD, prop, clazz);

		this.setHardness(1.5F);
		this.setResistance(5F);
		this.setSoundType(SoundType.WOOD);
		Blocks.FIRE.setFireInfo(this, 5, 20);
		this.setCreativeTab(NaturaRegistry.tabGeneral);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		int i = 4;
		int j = i + 1;

		if (worldIn.isAreaLoaded(pos.add(-j, -j, -j), pos.add(j, j, j)))
		{
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-i, -i, -i), pos.add(i, i, i)))
			{
				IBlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
				{
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
				}
			}
		}
	}

	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
	{
		IBlockState state = world.getBlockState(pos);
		for (IProperty<?> prop : state.getProperties().keySet())
		{
			if (prop.getName().equals("axis"))
			{
				world.setBlockState(pos, state.cycleProperty(prop));
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:

			switch (state.getValue(LOG_AXIS))
			{
			case X:
				return state.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Z);
			case Z:
				return state.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.X);
			default:
				return state;
			}

		default:
			return state;
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, LOG_AXIS);
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	public static enum EnumAxis implements IStringSerializable
	{
		X("x"), Y("y"), Z("z"), NONE("none");

		private final String name;

		private EnumAxis(String name)
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return this.name;
		}

		public static BlockEnumLog.EnumAxis fromFacingAxis(Axis axis)
		{
			switch (axis)
			{
			case X:
				return X;
			case Y:
				return Y;
			case Z:
				return Z;
			default:
				return NONE;
			}
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}

}
