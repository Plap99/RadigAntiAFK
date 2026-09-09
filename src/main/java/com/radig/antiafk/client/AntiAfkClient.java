package com.radig.antiafk.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.radig.antiafk.RadigAntiAFK;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(
    modid = RadigAntiAFK.MOD_ID,
    value = Dist.CLIENT
)
public final class AntiAfkClient {

    private static boolean enabled = false;
    private static long nextActionTime = 0;
    private static long sneakReleaseTime = 10000;
    private static boolean autoSneaking = false;

    public static boolean isEnabled() {
        return enabled;
    }

    static {
        AntiAfkHud.init();
    }

    private static final KeyMapping.Category CATEGORY =
        KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(
                RadigAntiAFK.MOD_ID,
                "general"
            )
        );

    private static final KeyMapping TOGGLE_KEY = new KeyMapping(
        "key.radigantiafk.toggle",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_F8,
        CATEGORY
    );

    private AntiAfkClient() {
    }

    public static long getSecondsUntilNextAction() {
        if (!enabled) {
            return 0;
        }

        long remaining = nextActionTime - System.currentTimeMillis();

        if (remaining <= 0) {
            return 0;
        }

        return (remaining + 999) / 1000;
    }
    
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        // Si el Anti-AFK está activo y el jugador vuelve a usar los controles,
        // asumimos que regresó y lo desactivamos automáticamente.
        if (enabled && !autoSneaking) {

            boolean playerMoved =
                minecraft.options.keyUp.isDown()
                || minecraft.options.keyDown.isDown()
                || minecraft.options.keyLeft.isDown()
                || minecraft.options.keyRight.isDown()
                || minecraft.options.keyJump.isDown()
                || minecraft.options.keyShift.isDown();

            if (playerMoved) {
                enabled = false;
                minecraft.options.keyShift.setDown(false);
                autoSneaking = false;

                minecraft.player.sendSystemMessage(
                    Component.literal(
                        "§eRadig Anti-AFK: DESACTIVADO - actividad detectada"
                    )
                );

                return;
            }
        }

        while (TOGGLE_KEY.consumeClick()) {
            enabled = !enabled;

            if (enabled) {
                nextActionTime = System.currentTimeMillis() + 5_000; // Primera acción después de 5 segundos.
            } else {
                minecraft.options.keyShift.setDown(false);
                autoSneaking = false;
            }

            minecraft.player.sendSystemMessage(
                Component.literal(
                    enabled
                        ? "§aRadig Anti-AFK: ACTIVADO"
                        : "§cRadig Anti-AFK: DESACTIVADO"
                )
            );
        }

        if (!enabled) {
            return;
        }

        long now = System.currentTimeMillis();

        //Soltar SHIFT después de aproximadamente medio segundo.
        if (autoSneaking && now >= sneakReleaseTime) {
            minecraft.options.keyShift.setDown(false);
            autoSneaking = false;
        }

        // Ejecutar nueva acción Anti-AFK.
        if (!autoSneaking && now >= nextActionTime) {
            minecraft.options.keyShift.setDown(true);
            autoSneaking = true;
            sneakReleaseTime = now + 500; // Mantener SHIFT presionado durante 0.5 segundos.
            
            //Próxima acción entre 45 y 90 segundos.
            long randomDelay = 45_000 + (long) (Math.random() * 45_000);

            nextActionTime = now + randomDelay;
        }
    }
}