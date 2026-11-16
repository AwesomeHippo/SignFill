package com.awesomehippo.signfill.mixin.compat;

import com.awesomehippo.signfill.TextFill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.client.renderer.CanvasSignRenderer;

@Pseudo
@Mixin(CanvasSignRenderer.class)
public abstract class FarmersDelightMixin {
    @Shadow(remap = false)
    public abstract int getCustomVerticalOffset();

    @Inject(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/SignText;hasGlowingText()Z"))
    private void scaleText(BlockPos pos, SignText text, PoseStack pose, MultiBufferSource buffer, int light, int lineHeight, int maxWidth, boolean front, CallbackInfo ci) {
        float widthLimit = 0.85F;
        TextFill.scale(text, Minecraft.getInstance().font, pose, lineHeight, maxWidth, widthLimit, 2 * lineHeight + this.getCustomVerticalOffset(), false);
    }
}
