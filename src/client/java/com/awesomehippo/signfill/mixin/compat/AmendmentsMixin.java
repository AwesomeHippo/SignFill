package com.awesomehippo.signfill.mixin.compat;

import com.awesomehippo.signfill.TextFill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.amendments.client.renderers.HangingSignRendererExtension;
import net.mehvahdjukaar.moonlight.api.client.util.LOD;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.SignText;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = HangingSignRendererExtension.class, remap = false)
public class AmendmentsMixin {
    @Inject(method = "renderSignText", at = @At("HEAD"))
    private static void scaleText(SignText text, Font font, PoseStack pose, MultiBufferSource buffer, int light, Vector3f normal, LOD lod, boolean filtered, int lineHeight, int maxWidth, float colorMult, CallbackInfo ci) {
        float widthLimit = 0.85F;
        TextFill.scale(text, font, pose, lineHeight, maxWidth, widthLimit, 0, true);
    }
}
