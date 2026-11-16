package com.awesomehippo.signfill.mixin;

import com.awesomehippo.signfill.TextFill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
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
        float widthLimit = 0.85F;
        TextFill.scale(text, this.font, pose, lineHeight, maxWidth, widthLimit, 2 * lineHeight, false);
    }
}
