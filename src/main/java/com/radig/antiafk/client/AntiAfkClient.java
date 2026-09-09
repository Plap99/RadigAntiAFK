package com.radig.antiafk.client;
import com.radig.antiafk.config.AntiAfkConfig;
import com.radig.antiafk.action.AntiAfkAction;
import com.radig.antiafk.action.AntiAfkActionSelector;

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
    private static AntiAfkAction currentAction = null;
    private static long actionReleaseTime = 0;
    private static float rotationStartYaw = 0;
    private static float rotationTargetYaw = 0;
    private static long rotationStartTime = 0;
    private static boolean returningFromWalk = false;
    private static AntiAfkAction originalWalkAction = null;
    private static long walkDurationMilliseconds = 0;

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
    
    private static void executeAction(
            Minecraft minecraft,
            AntiAfkAction action,
            long now) {
        currentAction = action;

        switch (action) {

            case CROUCH -> {
                minecraft.options.keyShift.setDown(true);

                actionReleaseTime = now + AntiAfkConfig.getCrouchDurationMilliseconds();
            }

            case JUMP -> {
                minecraft.options.keyJump.setDown(true);

                actionReleaseTime = now + 250;
            }

            case ROTATE_LEFT, ROTATE_RIGHT -> {
                float minDegrees = AntiAfkConfig.getMinRotateDegrees();

                float maxDegrees = AntiAfkConfig.getMaxRotateDegrees();

                float degrees = minDegrees
                        + (float) Math.random() * (maxDegrees - minDegrees);

                if (action == AntiAfkAction.ROTATE_LEFT) {
                    degrees = -degrees;
                }

                rotationStartYaw = minecraft.player.getYRot();
                rotationTargetYaw = rotationStartYaw + degrees;

                rotationStartTime = now;

                int minDuration = AntiAfkConfig.getMinRotateDurationMilliseconds();

                int maxDuration = AntiAfkConfig.getMaxRotateDurationMilliseconds();

                long duration = minDuration
                        + (long) (Math.random() * (maxDuration - minDuration + 1));

                actionReleaseTime = now + duration;
            }
            
            case WALK_FORWARD,
                    WALK_BACKWARD,
                    WALK_LEFT,
                    WALK_RIGHT -> {

                switch (action) {

                    case WALK_FORWARD ->
                        minecraft.options.keyUp.setDown(true);

                    case WALK_BACKWARD ->
                        minecraft.options.keyDown.setDown(true);

                    case WALK_LEFT ->
                        minecraft.options.keyLeft.setDown(true);

                    case WALK_RIGHT ->
                        minecraft.options.keyRight.setDown(true);

                    default -> {
                    }
                }

                int minDuration = AntiAfkConfig.getMinWalkDurationMilliseconds();

                int maxDuration = AntiAfkConfig.getMaxWalkDurationMilliseconds();

                long duration = minDuration
                        + (long) (Math.random()
                                * (maxDuration - minDuration + 1));

                originalWalkAction = action;
                walkDurationMilliseconds = duration;
                returningFromWalk = false;

                actionReleaseTime = now + duration;
            }

            default -> {
                currentAction = null;
            }
        }
    }

    private static void scheduleNextAction(long now) {

        long minDelay = AntiAfkConfig.getMinActionDelaySeconds() * 1000L;

        long maxDelay = AntiAfkConfig.getMaxActionDelaySeconds() * 1000L;

        long randomDelay = minDelay
                + (long) (Math.random() * (maxDelay - minDelay + 1));

        nextActionTime = now + randomDelay;
    }

    private static void releaseCurrentAction(Minecraft minecraft) {
        if (currentAction == null) {
            return;
        }

        switch (currentAction) {

            case CROUCH ->
                minecraft.options.keyShift.setDown(false);

            case JUMP ->
                minecraft.options.keyJump.setDown(false);

            case ROTATE_LEFT, ROTATE_RIGHT -> {
                if (minecraft.player != null) {
                    minecraft.player.setYRot(rotationTargetYaw);
                }
            }

            case WALK_FORWARD ->
                minecraft.options.keyUp.setDown(false);

            case WALK_BACKWARD ->
                minecraft.options.keyDown.setDown(false);

            case WALK_LEFT ->
                minecraft.options.keyLeft.setDown(false);

            case WALK_RIGHT ->
                minecraft.options.keyRight.setDown(false);

            default -> {
            }
        }

        currentAction = null;
        actionReleaseTime = 0;
    }

    private static void updateRotation(
            Minecraft minecraft,
            long now) {
        if (minecraft.player == null) {
            return;
        }

        long totalDuration = actionReleaseTime - rotationStartTime;

        if (totalDuration <= 0) {
            minecraft.player.setYRot(rotationTargetYaw);
            return;
        }

        float progress = (float) (now - rotationStartTime)
                / (float) totalDuration;

        progress = Math.max(0.0f, Math.min(1.0f, progress));

        // Suavizado para que el giro no sea completamente lineal.
        float smoothProgress = progress * progress * (3.0f - 2.0f * progress);

        float currentYaw = rotationStartYaw
                + (rotationTargetYaw - rotationStartYaw)
                        * smoothProgress;

        minecraft.player.setYRot(currentYaw);
    }

    private static boolean isWalkAction(AntiAfkAction action) {
        return action == AntiAfkAction.WALK_FORWARD
                || action == AntiAfkAction.WALK_BACKWARD
                || action == AntiAfkAction.WALK_LEFT
                || action == AntiAfkAction.WALK_RIGHT;
    }

    private static AntiAfkAction getOppositeWalkAction(
            AntiAfkAction action) {
        return switch (action) {
            case WALK_FORWARD -> AntiAfkAction.WALK_BACKWARD;
            case WALK_BACKWARD -> AntiAfkAction.WALK_FORWARD;
            case WALK_LEFT -> AntiAfkAction.WALK_RIGHT;
            case WALK_RIGHT -> AntiAfkAction.WALK_LEFT;

            default -> null;
        };
    }

    private static void startReturnWalk(
            Minecraft minecraft,
            long now) {
        AntiAfkAction returnAction = getOppositeWalkAction(originalWalkAction);

        if (returnAction == null) {
            releaseCurrentAction(minecraft);
            resetWalkState();
            return;
        }

        // Soltamos la dirección original.
        releaseCurrentAction(minecraft);

        // Empezamos la dirección contraria.
        currentAction = returnAction;
        returningFromWalk = true;

        switch (returnAction) {

            case WALK_FORWARD ->
                minecraft.options.keyUp.setDown(true);

            case WALK_BACKWARD ->
                minecraft.options.keyDown.setDown(true);

            case WALK_LEFT ->
                minecraft.options.keyLeft.setDown(true);

            case WALK_RIGHT ->
                minecraft.options.keyRight.setDown(true);

            default -> {
            }
        }

        actionReleaseTime = now + walkDurationMilliseconds;
    }

    private static void resetWalkState() {
        returningFromWalk = false;
        originalWalkAction = null;
        walkDurationMilliseconds = 0;
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
        if (enabled && currentAction == null) {

            boolean playerMoved =
                minecraft.options.keyUp.isDown()
                || minecraft.options.keyDown.isDown()
                || minecraft.options.keyLeft.isDown()
                || minecraft.options.keyRight.isDown()
                || minecraft.options.keyJump.isDown()
                || minecraft.options.keyShift.isDown();

            if (playerMoved) {
                enabled = false;
                releaseCurrentAction(minecraft);
                resetWalkState();

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
                nextActionTime = System.currentTimeMillis() + AntiAfkConfig.getFirstActionDelaySeconds() * 1000L;
            } else {
                releaseCurrentAction(minecraft);
                resetWalkState();
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

        if (currentAction == AntiAfkAction.ROTATE_LEFT
                || currentAction == AntiAfkAction.ROTATE_RIGHT) {
            updateRotation(minecraft, now);
        }

        // Finalizar la acción automática cuando termine su duración.
        if (currentAction != null && now >= actionReleaseTime) {

            if (isWalkAction(currentAction)
                    && AntiAfkConfig.isReturnToOriginEnabled()
                    && !returningFromWalk) {
                startReturnWalk(minecraft, now);
            } else {
                releaseCurrentAction(minecraft);
                resetWalkState();
            }
        }

        // Ejecutar nueva acción Anti-AFK.
        if (currentAction == null && now >= nextActionTime) {

            AntiAfkAction action = AntiAfkActionSelector.getRandomEnabledAction();

            if (action != null) {
                executeAction(minecraft, action, now);
            }

            scheduleNextAction(now);
        }
    }
}