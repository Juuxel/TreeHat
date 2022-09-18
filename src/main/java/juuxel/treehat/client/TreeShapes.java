/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Optional;

public final class TreeShapes {
    public static final BlockState[][][] OAK = basicTree(Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState());
    public static final BlockState[][][] SPRUCE = Util.make(new BlockState[7][9][7], blocks -> {
        var log = Blocks.SPRUCE_LOG.getDefaultState();
        var leaf = Blocks.SPRUCE_LEAVES.getDefaultState();
        fill(blocks, 1, 7, 7, 0, leaf, true);
        fill(blocks, 2, 5, 5, 1, leaf, true);
        fill(blocks, 3, 3, 3, 2, leaf, true);
        fill(blocks, 4, 5, 5, 1, leaf, true);
        fill(blocks, 5, 3, 3, 2, leaf, true);
        fill(blocks, 7, 3, 3, 2, leaf, true);
        blocks[3][8][3] = leaf;

        for (int y = 0; y < 7; y++) {
            blocks[3][y][3] = log;
        }
    });
    public static final BlockState[][][] BIRCH = basicTree(Blocks.BIRCH_LOG.getDefaultState(), Blocks.BIRCH_LEAVES.getDefaultState());

    private static BlockState[][][] basicTree(BlockState log, BlockState leaf) {
        var blocks = new BlockState[5][6][5];
        fill(blocks, 2, 5, 5, 0, leaf, true);
        fill(blocks, 3, 5, 5, 0, leaf, true);
        fill(blocks, 4, 3, 3, 1, leaf, false);
        fill(blocks, 5, 3, 3, 1, leaf, true);

        blocks[2][0][2] = log;
        blocks[2][1][2] = log;
        blocks[2][2][2] = log;
        blocks[2][3][2] = log;
        blocks[2][4][2] = log;
        return blocks;
    }

    private static <E> Optional<E> maybeGet(List<E> list, int index) {
        return index < list.size() ? Optional.ofNullable(list.get(index)) : Optional.empty();
    }

    private static BlockState[][][] buildXyz(List<List<List<BlockState>>> yxz) {
        int sizeX = yxz.stream().mapToInt(List::size).max().orElse(0);
        int sizeZ = yxz.stream().flatMap(List::stream).mapToInt(List::size).max().orElse(0);
        int sizeY = yxz.size();
        BlockState[][][] result = new BlockState[sizeX][sizeY][sizeZ];

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    int effX = x;
                    int effZ = z;
                    result[x][y][z] = maybeGet(yxz, y).flatMap(layer -> maybeGet(layer, effX)).flatMap(slice -> maybeGet(slice, effZ)).orElse(null);
                }
            }
        }

        return result;
    }

    private static void fill(BlockState[][][] blocks, int y, int width, int depth, int offset, BlockState state, boolean ignoreCorners) {
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                if (ignoreCorners && ((x == 0 && z == 0) || (x == 0 && z == depth - 1) || (x == width - 1 && z == 0) || (x == width - 1 && z == depth - 1))) {
                    continue;
                }

                blocks[offset + x][y][offset + z] = state;
            }
        }
    }
}
