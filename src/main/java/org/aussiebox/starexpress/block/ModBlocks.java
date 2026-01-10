package org.aussiebox.starexpress.block;

import dev.doctor4t.ratatouille.util.registrar.BlockRegistrar;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.block.custom.PlushBlock;

public interface ModBlocks {

    BlockRegistrar registrar = new BlockRegistrar(StarryExpress.MOD_ID);

    Block CIRCUITWEAVER_PLUSH = registrar.createWithItem("circuitweaver_plush", new PlushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL).noOcclusion()));
    Block JADE_PLUSH = registrar.createWithItem("jade_plush", new PlushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL).noOcclusion()));

    static void init() {
        registrar.registerEntries();
    }
}
