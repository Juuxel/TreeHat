/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.client;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Optional;

public final class TreeHatRenderer implements ArmorRenderer {
    private final BlockState[][][] blocks;
    private final int width;
    private final int depth;

    public TreeHatRenderer(BlockState[][][] blocks) {
        this.blocks = blocks;
        width = blocks.length;
        int d = 0;
        for (BlockState[][] layer : blocks) {
            for (BlockState[] slice : layer) {
                d = Math.max(d, slice.length);
            }
        }
        depth = d;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        matrices.push();
        var head = contextModel.head;

        // Apply rotation from head model
        matrices.scale(16, 16, 16);
        head.rotate(matrices);
        matrices.scale(1/16f, 1/16f, 1/16f);

        matrices.translate(head.pivotX, head.pivotY - 0.625, head.pivotZ);
        matrices.scale(0.125f, 0.125f, 0.125f);
        matrices.translate(-width * 0.5, 0, -depth * 0.5);
        for (int x = 0; x < blocks.length; x++) {
            var layer = blocks[x];
            for (int y = 0; y < layer.length; y++) {
                var slice = layer[y];
                for (int z = 0; z < slice.length; z++) {
                    var block = slice[z];
                    if (block != null) {
                        matrices.push();
                        matrices.translate(x, -y, z);
                        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(block, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
                        matrices.pop();
                    }
                }
            }
        }
        matrices.pop();
    }
}
