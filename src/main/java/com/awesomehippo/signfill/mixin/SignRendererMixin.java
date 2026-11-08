package com.awesomehippo.signfill.mixin;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public class SignRendererMixin {
    @Shadow
    @Final
    private Font font;

    @Inject(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SignText;hasGlowingText()Z"))
    private void scaleText(BlockPos pos, SignText text, PoseStack pose, MultiBufferSource buffer, int light, int lineHeight, int maxWidth, boolean front, CallbackInfo ci) {
        FormattedCharSequence[] lines = text.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (component) -> this.line(component, maxWidth));
        int first = -1;
        int last = -1;
        int widest = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].accept((index, style, codePoint) -> Character.isWhitespace(codePoint))) {
                continue;
            }
            int width = this.font.width(lines[i]);
            if (first < 0) {
                first = i;
            }
            last = i;
            if (width > widest) {
                widest = width;
            }
        }
        if (first < 0) {
            return;
        }
        int used = last - first + 1;
        float scale = lines.length / (float) used;
        float widthScale = maxWidth * 0.85F / widest;
        if (widthScale < scale) {
            scale = widthScale;
        }
        if (scale <= 1.0F) {
            return;
        }
        int top = lines.length * lineHeight / 2;
        float glyph = this.font.lineHeight - 2;
        float center = (first + last) * 0.5F * lineHeight - top + glyph * 0.5F;
        float target = (lines.length - 1) * 0.5F * lineHeight - top + glyph * 0.5F;
        pose.translate(0.0D, target, 0.0D);
        pose.scale(scale, scale, 1.0F);
        pose.translate(widest % 2 == 0 ? 0.5D : 0.0D, -center, 0.0D);
    }

    private FormattedCharSequence line(Component component, int maxWidth) {
        List<FormattedCharSequence> split = this.font.split(component, maxWidth);
        if (split.isEmpty()) {
            return FormattedCharSequence.EMPTY;
        }
        return split.get(0);
    }
}
