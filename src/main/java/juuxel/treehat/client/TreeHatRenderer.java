/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.client;

import juuxel.treehat.mixin.client.AnimalModelAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public final class TreeHatRenderer implements ArmorRenderer, ModelPart.CuboidConsumer {
    private static final float SCALE = 0.15f;
    private final BlockState[][][] blocks;
    private final int width;
    private final int depth;
    private float height;

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
    public void accept(MatrixStack.Entry matrix, String path, int index, ModelPart.Cuboid cuboid) {
        height = Math.max(height, Math.abs(cuboid.maxY - cuboid.minY));
    }

    private void findHeight(MatrixStack matrices, ModelPart modelPart) {
        height = 0;
        modelPart.forEachCuboid(matrices, this);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        matrices.push();
        var hat = contextModel.hat;

        // Apply child body transformations
        if (contextModel.child) {
            float childBodyScale = 1 / ((AnimalModelAccessor) contextModel).getInvertedChildBodyScale();
            matrices.scale(childBodyScale, childBodyScale, childBodyScale);
            matrices.translate(0, ((AnimalModelAccessor) contextModel).getChildBodyYOffset() / 16.0, 0);
        }

        // Apply rotation from hat model
        hat.rotate(matrices);

        // Rescale
        matrices.scale(SCALE, SCALE, SCALE);

        // Relocate to where we're drawing it
        findHeight(matrices, hat);
        matrices.translate(-width * 0.5, -height * 0.5, -depth * 0.5);

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
