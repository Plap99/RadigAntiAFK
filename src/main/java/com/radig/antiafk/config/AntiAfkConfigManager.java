package com.radig.antiafk.config;

public final class AntiAfkConfigManager {

    private AntiAfkConfigManager() {
    }

    public static void load() {

        AntiAfkConfig.setMinActionDelaySeconds(
            AntiAfkForgeConfig.MIN_ACTION_DELAY_SECONDS.get()
        );

        AntiAfkConfig.setMaxActionDelaySeconds(
            AntiAfkForgeConfig.MAX_ACTION_DELAY_SECONDS.get()
        );

        AntiAfkConfig.setCrouchEnabled(
            AntiAfkForgeConfig.CROUCH_ENABLED.get()
        );

        AntiAfkConfig.setJumpEnabled(
            AntiAfkForgeConfig.JUMP_ENABLED.get()
        );

        AntiAfkConfig.setRotateEnabled(
            AntiAfkForgeConfig.ROTATE_ENABLED.get()
        );

        AntiAfkConfig.setWalkEnabled(
            AntiAfkForgeConfig.WALK_ENABLED.get()
        );

        AntiAfkConfig.setReturnToOriginEnabled(
            AntiAfkForgeConfig.RETURN_TO_ORIGIN_ENABLED.get()
        );

        AntiAfkConfig.setMinRotateDegrees(
            AntiAfkForgeConfig.MIN_ROTATE_DEGREES.get().floatValue()
        );

        AntiAfkConfig.setMaxRotateDegrees(
            AntiAfkForgeConfig.MAX_ROTATE_DEGREES.get().floatValue()
        );

        AntiAfkConfig.setMinWalkDurationMilliseconds(
            AntiAfkForgeConfig.MIN_WALK_DURATION_MS.get()
        );

        AntiAfkConfig.setMaxWalkDurationMilliseconds(
            AntiAfkForgeConfig.MAX_WALK_DURATION_MS.get()
        );
    }

    public static void save() {

        AntiAfkForgeConfig.MIN_ACTION_DELAY_SECONDS.set(
            AntiAfkConfig.getMinActionDelaySeconds()
        );

        AntiAfkForgeConfig.MAX_ACTION_DELAY_SECONDS.set(
            AntiAfkConfig.getMaxActionDelaySeconds()
        );

        AntiAfkForgeConfig.CROUCH_ENABLED.set(
            AntiAfkConfig.isCrouchEnabled()
        );

        AntiAfkForgeConfig.JUMP_ENABLED.set(
            AntiAfkConfig.isJumpEnabled()
        );

        AntiAfkForgeConfig.ROTATE_ENABLED.set(
            AntiAfkConfig.isRotateEnabled()
        );

        AntiAfkForgeConfig.WALK_ENABLED.set(
            AntiAfkConfig.isWalkEnabled()
        );

        AntiAfkForgeConfig.RETURN_TO_ORIGIN_ENABLED.set(
            AntiAfkConfig.isReturnToOriginEnabled()
        );

        AntiAfkForgeConfig.MIN_ROTATE_DEGREES.set(
            (double) AntiAfkConfig.getMinRotateDegrees()
        );

        AntiAfkForgeConfig.MAX_ROTATE_DEGREES.set(
            (double) AntiAfkConfig.getMaxRotateDegrees()
        );

        AntiAfkForgeConfig.MIN_WALK_DURATION_MS.set(
            AntiAfkConfig.getMinWalkDurationMilliseconds()
        );

        AntiAfkForgeConfig.MAX_WALK_DURATION_MS.set(
            AntiAfkConfig.getMaxWalkDurationMilliseconds()
        );

        AntiAfkForgeConfig.SPEC.save();
    }

    public static void resetToDefaults() {

        AntiAfkConfig.setMinActionDelaySeconds(45);
        AntiAfkConfig.setMaxActionDelaySeconds(90);

        AntiAfkConfig.setCrouchEnabled(true);
        AntiAfkConfig.setJumpEnabled(true);
        AntiAfkConfig.setRotateEnabled(true);
        AntiAfkConfig.setWalkEnabled(true);

        AntiAfkConfig.setMinRotateDegrees(20.0f);
        AntiAfkConfig.setMaxRotateDegrees(70.0f);

        AntiAfkConfig.setMinWalkDurationMilliseconds(500);
        AntiAfkConfig.setMaxWalkDurationMilliseconds(1500);

        AntiAfkConfig.setReturnToOriginEnabled(true);

        save();
    }
}