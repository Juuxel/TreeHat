/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat;

import juuxel.treehat.item.ThItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public final class TreeHat implements ModInitializer {
    public static final String ID = "tree_hat";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        ThItems.init();
    }
}
