/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.item;

import juuxel.treehat.TreeHat;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class ThItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(TreeHat.id("items"), () -> ThItems.OAK_IN_A_POT.getDefaultStack());
    public static final Item OAK_IN_A_POT = new TreeHatItem(new Item.Settings().group(ITEM_GROUP));
    public static final Item SPRUCE_IN_A_POT = new TreeHatItem(new Item.Settings().group(ITEM_GROUP));
    public static final Item BIRCH_IN_A_POT = new TreeHatItem(new Item.Settings().group(ITEM_GROUP));

    public static void init() {
        register("oak_in_a_pot", OAK_IN_A_POT);
        register("spruce_in_a_pot", SPRUCE_IN_A_POT);
        register("birch_in_a_pot", BIRCH_IN_A_POT);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, TreeHat.id(id), item);
    }
}
