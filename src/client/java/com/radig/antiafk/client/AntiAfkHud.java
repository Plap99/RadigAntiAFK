package com.radig.antiafk.client;

import com.radig.antiafk.RadigAntiAFK;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public final class AntiAfkHud {
    private static final Identifier HUD_LAYER = Identifier.fromNamespaceAndPath(
            RadigAntiAFK.MOD_ID, "anti_afk_status");

    private AntiAfkHud() {}

    public static void init() {
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, HUD_LAYER,
                (graphics, deltaTracker) -> {
                    if (!AntiAfkClient.isEnabled()) return;
                    Minecraft minecraft = Minecraft.getInstance();
                    if (minecraft.player == null) return;
                    String text = "Anti-AFK: ON | Próxima acción: "
                            + AntiAfkClient.getSecondsUntilNextAction() + "s";
                    float scale = 0.75f;
                    graphics.pose().pushMatrix();
                    graphics.pose().scale(scale, scale);
                    graphics.text(minecraft.font, text,
                            (int) (10 / scale), (int) (10 / scale), 0xFF55FF55, true);
                    graphics.pose().popMatrix();
                });
    }
}
