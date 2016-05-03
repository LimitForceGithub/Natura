package com.progwml6.natura.world.block.trees;

import java.util.Locale;
import java.util.Random;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldLogs extends BlockEnumLog<BlockOverworldLogs.LogType>
{
    public static PropertyEnum<LogType> TYPE = PropertyEnum.create("type", LogType.class);

    public BlockOverworldLogs()
    {
        super(TYPE, LogType.class);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Y));
    }

    @Override
    public String getLocalizedName()
    {
        return I18n.translateToLocal("natura." + this.getUnlocalizedName() + ".name");
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return this.getMetaFromState(world.getBlockState(pos)) % 4 != 2 ? this.getFlammability(world, pos, face) : 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return this.getMetaFromState(world.getBlockState(pos)) % 4 != 2 ? this.getFireSpreadSpeed(world, pos, face) : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(prop, fromMeta((meta & 3)));

        switch (meta & 12)
        {
        case 0:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Y);
            break;
        case 4:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.X);
            break;
        case 8:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.Z);
            break;
        default:
            iblockstate = iblockstate.withProperty(LOG_AXIS, BlockEnumLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i = b0 | state.getValue(prop).getMeta();

        switch (state.getValue(LOG_AXIS))
        {
        case X:
            i |= 4;
            break;
        case Z:
            i |= 8;
            break;
        case NONE:
            i |= 12;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, LOG_AXIS);
    }

    /**
     * Get the damage value that this Block should drop
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(prop).getMeta();
    }

    public enum LogType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        EUCALYPTUS, SAKURA, GHOSTWOOD, HOPSEED;

        public final int meta;

        LogType()
        {
            meta = ordinal();
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return meta;
        }
    }
}
