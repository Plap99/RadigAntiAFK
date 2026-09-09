package com.radig.antiafk.config;
import com.radig.antiafk.action.AntiAfkAction;

public final class AntiAfkConfig {

    // Tiempo antes de la primera acción al activar el Anti-AFK.
    private static int firstActionDelaySeconds = 5;

    // Intervalo aleatorio entre acciones.
    private static int minActionDelaySeconds = 5; //45;
    private static int maxActionDelaySeconds = 10; //90;

    // Duración del agachado.
    private static int crouchDurationMilliseconds = 500;

    // Acciones disponibles.
    private static boolean crouchEnabled = true;
    private static boolean jumpEnabled = true;
    private static boolean rotateEnabled = false;
    private static boolean walkEnabled = false;

    private AntiAfkConfig() {
    }

    public static int getFirstActionDelaySeconds() {
        return firstActionDelaySeconds;
    }

    public static int getMinActionDelaySeconds() {
        return minActionDelaySeconds;
    }

    public static int getMaxActionDelaySeconds() {
        return maxActionDelaySeconds;
    }

    public static int getCrouchDurationMilliseconds() {
        return crouchDurationMilliseconds;
    }

    public static boolean isCrouchEnabled() {
        return crouchEnabled;
    }

    public static boolean isJumpEnabled() {
        return jumpEnabled;
    }

    public static boolean isRotateEnabled() {
        return rotateEnabled;
    }

    public static boolean isWalkEnabled() {
        return walkEnabled;
    }

    public static boolean isActionEnabled(AntiAfkAction action) {
        return switch (action) {
            case CROUCH -> crouchEnabled;
            case JUMP -> jumpEnabled;
            case ROTATE_LEFT, ROTATE_RIGHT -> rotateEnabled;
            case WALK_FORWARD,
                    WALK_BACKWARD,
                    WALK_LEFT,
                    WALK_RIGHT ->
                walkEnabled;
        };
    }
}