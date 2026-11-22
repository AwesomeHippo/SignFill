package com.awesomehippo.signfill.mixin.compat;

import com.awesomehippo.signfill.TextFill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignText;
import net.satisfy.vinery.client.render.block.ModHangingSignRenderer;
import net.satisfy.vinery.client.render.block.ModSignRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ModSignRenderer.class, remap = false)
public class VineryMixin {
    @Inject(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SignText;hasGlowingText()Z", remap = true))
    private void scaleText(BlockPos pos, SignText text, PoseStack pose, MultiBufferSource buffer, int light, int lineHeight, int maxWidth, boolean front, CallbackInfo ci) {
        float widthLimit = 0.85F;
        int width = (Object) this instanceof ModHangingSignRenderer ? 60 : maxWidth;
        TextFill.scale(text, Minecraft.getInstance().font, pose, lineHeight, width, widthLimit, 2 * lineHeight, false);
    }
}
