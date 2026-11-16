package com.awesomehippo.signfill;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.SignText;

public class TextFill {
    public static void scale(SignText text, Font font, PoseStack pose, int lineHeight, int maxWidth, float widthLimit, int top, boolean floatCentered) {
        FormattedCharSequence[] lines = text.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (component) -> line(font, component, maxWidth));
        scale(lines, lines.length, font, pose, lineHeight, maxWidth, widthLimit, top, floatCentered);
    }

    public static void scale(FormattedCharSequence[] lines, float rows, Font font, PoseStack pose, int lineHeight, int maxWidth, float widthLimit, int top, boolean floatCentered) {
        int first = -1;
        int last = -1;
        int widest = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].accept((index, style, codePoint) -> Character.isWhitespace(codePoint))) {
                continue;
            }
            int width = font.width(lines[i]);
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
        float scale = rows / used;
        float widthScale = maxWidth * widthLimit / widest;
        if (widthScale < scale) {
            scale = widthScale;
        }
        if (scale <= 1.0F) {
            return;
        }
        float glyph = font.lineHeight - 2;
        float center = (first + last) * 0.5F * lineHeight - top + glyph * 0.5F;
        float target = (lines.length - 1) * 0.5F * lineHeight - top + glyph * 0.5F;
        pose.translate(0.0D, target, 0.0D);
        pose.scale(scale, scale, 1.0F);
        // half pixel fix to keep the text actually centered..
        pose.translate(floatCentered || widest % 2 == 0 ? 0.5D : 0.0D, -center, 0.0D);
    }

    private static FormattedCharSequence line(Font font, Component component, int maxWidth) {
        List<FormattedCharSequence> split = font.split(component, maxWidth);
        if (split.isEmpty()) {
            return FormattedCharSequence.EMPTY;
        }
        return split.get(0);
    }
}
