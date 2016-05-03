package com.progwml6.natura.world.block.trees;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.world.block.trees.BlockOverworldLogs.LogType;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BlockOverworldLeaves extends BlockLeaves
{
    public BlockOverworldLeaves()
    {
        this.setCreativeTab(NaturaRegistry.tabGeneral);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (LogType type : LogType.values())
        {
            list.add(new ItemStack(this, 1, getMetaFromState(this.getDefaultState().withProperty(BlockOverworldLogs.TYPE, type))));
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return Blocks.LEAVES.isOpaqueCube(state);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return Blocks.LEAVES.getBlockLayer();
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        // isOpaqueCube returns !leavesFancy to us. We have to fix the variable before calling super
        this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    // sapling meta
    @Override
    public int damageDropped(IBlockState state)
    {
        return (state.getValue(BlockOverworldLogs.TYPE)).ordinal() & 3; // only first 2 bits
    }

    // item dropped on silktouching
    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(BlockOverworldLogs.TYPE)).ordinal() & 3);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockOverworldLogs.TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int type = meta % 4;
        if (type < 0 || type >= LogType.values().length)
        {
            type = 0;
        }
        LogType log = LogType.values()[type];
        return this.getDefaultState()
                .withProperty(BlockOverworldLogs.TYPE, log)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = (state.getValue(BlockOverworldLogs.TYPE)).ordinal() & 3; // only first 2 bits

        if (!state.getValue(DECAYABLE))
        {
            meta |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        throw new NotImplementedException(); // unused by our code.
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        return Lists.newArrayList(createStackedBlock(state));
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

}
