/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package juuxel.treehat.mixin.client;

import net.minecraft.client.render.entity.model.AnimalModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnimalModel.class)
public interface AnimalModelAccessor {
    @Accessor
    float getInvertedChildBodyScale();

    @Accessor
    float getChildBodyYOffset();
}
