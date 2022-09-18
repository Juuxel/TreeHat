/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.client;

import juuxel.treehat.item.ThItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;

public final class ThClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArmorRenderer.register(new TreeHatRenderer(TreeShapes.OAK), ThItems.OAK_IN_A_POT);
        ArmorRenderer.register(new TreeHatRenderer(TreeShapes.SPRUCE), ThItems.SPRUCE_IN_A_POT);
        ArmorRenderer.register(new TreeHatRenderer(TreeShapes.BIRCH), ThItems.BIRCH_IN_A_POT);
    }
}
