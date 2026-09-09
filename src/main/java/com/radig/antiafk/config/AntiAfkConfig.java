package com.radig.antiafk.config;
import com.radig.antiafk.action.AntiAfkAction;

public final class AntiAfkConfig {

    // Tiempo antes de la primera acción al activar el Anti-AFK.
    private static int firstActionDelaySeconds = 5;

    // Intervalo aleatorio entre acciones.
    private static int minActionDelaySeconds = 5; //45;     Habilitar para pruebas rápidas.
    private static int maxActionDelaySeconds = 10; //90;     Habilitar para pruebas rápidas.

    // Duración del agachado.
    private static int crouchDurationMilliseconds = 500;

    // Configuración del giro.
    private static float minRotateDegrees = 20.0f;
    private static float maxRotateDegrees = 70.0f;
    private static int minRotateDurationMilliseconds = 400;
    private static int maxRotateDurationMilliseconds = 800;

    // Configuración de caminata.
    private static int minWalkDurationMilliseconds = 500;
    private static int maxWalkDurationMilliseconds = 1500;

    // Regresar aproximadamente al punto de inicio después de caminar.
    private static boolean returnToOriginEnabled = true;

    // Acciones disponibles.
    private static boolean crouchEnabled = true;
    private static boolean jumpEnabled = true;
    private static boolean rotateEnabled = true;
    private static boolean walkEnabled = true;

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

    public static float getMinRotateDegrees() {
        return minRotateDegrees;
    }

    public static float getMaxRotateDegrees() {
        return maxRotateDegrees;
    }

    public static int getMinRotateDurationMilliseconds() {
        return minRotateDurationMilliseconds;
    }

    public static int getMaxRotateDurationMilliseconds() {
        return maxRotateDurationMilliseconds;
    }

    public static int getMinWalkDurationMilliseconds() {
        return minWalkDurationMilliseconds;
    }

    public static int getMaxWalkDurationMilliseconds() {
        return maxWalkDurationMilliseconds;
    }

    public static boolean isReturnToOriginEnabled() {
        return returnToOriginEnabled;
    }
    
    public static void setCrouchEnabled(boolean enabled) {
        crouchEnabled = enabled;
    }

    public static void setJumpEnabled(boolean enabled) {
        jumpEnabled = enabled;
    }

    public static void setRotateEnabled(boolean enabled) {
        rotateEnabled = enabled;
    }

    public static void setWalkEnabled(boolean enabled) {
        walkEnabled = enabled;
    }

    public static void setReturnToOriginEnabled(boolean enabled) {
        returnToOriginEnabled = enabled;
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

    public static void setMinActionDelaySeconds(int seconds) {
        minActionDelaySeconds = Math.max(1, seconds);

        if (maxActionDelaySeconds < minActionDelaySeconds) {
            maxActionDelaySeconds = minActionDelaySeconds;
        }
    }

    public static void setMaxActionDelaySeconds(int seconds) {
        maxActionDelaySeconds = Math.max(minActionDelaySeconds, seconds);
    }

    public static void setMinRotateDegrees(float degrees) {
        minRotateDegrees = Math.max(1.0f, Math.min(180.0f, degrees));

        if (maxRotateDegrees < minRotateDegrees) {
            maxRotateDegrees = minRotateDegrees;
        }
    }

    public static void setMaxRotateDegrees(float degrees) {
        maxRotateDegrees = Math.max(
                minRotateDegrees,
                Math.min(180.0f, degrees));
    }

    public static void setMinWalkDurationMilliseconds(int milliseconds) {
        minWalkDurationMilliseconds = Math.max(100, milliseconds);

        if (maxWalkDurationMilliseconds < minWalkDurationMilliseconds) {
            maxWalkDurationMilliseconds = minWalkDurationMilliseconds;
        }
    }

    public static void setMaxWalkDurationMilliseconds(int milliseconds) {
        maxWalkDurationMilliseconds = Math.max(
                minWalkDurationMilliseconds,
                milliseconds);
    }
}