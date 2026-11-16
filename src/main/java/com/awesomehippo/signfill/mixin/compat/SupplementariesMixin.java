package com.awesomehippo.signfill.mixin.compat;

import com.awesomehippo.signfill.TextFill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.moonlight.api.client.util.TextUtil;
import net.mehvahdjukaar.supplementaries.client.renderers.tiles.SignPostBlockTileRenderer;
import net.mehvahdjukaar.supplementaries.common.block.TextHolder;
import net.mehvahdjukaar.supplementaries.common.block.tiles.SignPostBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = SignPostBlockTileRenderer.class, remap = false)
public class SupplementariesMixin {
    @Inject(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/mehvahdjukaar/moonlight/api/client/util/TextUtil;renderLine(Lnet/minecraft/util/FormattedCharSequence;Lnet/minecraft/client/gui/Font;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/mehvahdjukaar/moonlight/api/client/util/TextUtil$RenderProperties;)V"))
    private void scaleText(SignPostBlockTile tile, PoseStack pose, MultiBufferSource buffer, SignPostBlockTile.Sign sign, TextUtil.RenderProperties properties, int line, CallbackInfo ci) {
        Font font = Minecraft.getInstance().font;
        TextHolder holder = tile.getTextHolder(line);
        // might tweak these but looks good enough for now
        float rows = 1.8F;
        float widthLimit = 0.85F;
        TextFill.scale(new FormattedCharSequence[] {holder.getRenderMessages(0, font)}, rows, font, pose, 0, holder.getMaxLineVisualWidth(), widthLimit, 4, true);
    }
}
