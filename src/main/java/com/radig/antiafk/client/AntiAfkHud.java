package com.radig.antiafk.client;

import com.radig.antiafk.RadigAntiAFK;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;

public final class AntiAfkHud {

    private static final Identifier HUD_LAYER =
        Identifier.fromNamespaceAndPath(
            RadigAntiAFK.MOD_ID,
            "anti_afk_status"
        );

    private AntiAfkHud() {
    }

    public static void init() {
        AddGuiOverlayLayersEvent.BUS.addListener(
            AntiAfkHud::registerHudLayer
        );
    }

    private static void registerHudLayer(AddGuiOverlayLayersEvent event) {
        event.getLayeredDraw().add(
                HUD_LAYER,
                (guiGraphics, deltaTracker) -> {

                    if (!AntiAfkClient.isEnabled()) {
                        return;
                    }

                    Minecraft minecraft = Minecraft.getInstance();

                    long seconds = AntiAfkClient.getSecondsUntilNextAction();

                    String text = "Anti-AFK: ON | Próxima acción: " + seconds + "s";

                    float scale = 0.75f;

                    guiGraphics.pose().pushMatrix();

                    guiGraphics.pose().scale(
                            scale,
                            scale);

                    guiGraphics.text(
                            minecraft.font,
                            text,
                            (int) (10 / scale),
                            (int) (10 / scale),
                            0xFF55FF55,
                            true);

                    guiGraphics.pose().popMatrix();
                });
    }
}