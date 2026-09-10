package com.radig.antiafk.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class AntiAfkForgeConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue MIN_ACTION_DELAY_SECONDS;
    public static final ForgeConfigSpec.IntValue MAX_ACTION_DELAY_SECONDS;

    public static final ForgeConfigSpec.BooleanValue CROUCH_ENABLED;
    public static final ForgeConfigSpec.BooleanValue JUMP_ENABLED;
    public static final ForgeConfigSpec.BooleanValue ROTATE_ENABLED;
    public static final ForgeConfigSpec.BooleanValue WALK_ENABLED;

    public static final ForgeConfigSpec.DoubleValue MIN_ROTATE_DEGREES;
    public static final ForgeConfigSpec.DoubleValue MAX_ROTATE_DEGREES;

    public static final ForgeConfigSpec.IntValue MIN_WALK_DURATION_MS;
    public static final ForgeConfigSpec.IntValue MAX_WALK_DURATION_MS;

    public static final ForgeConfigSpec.BooleanValue RETURN_TO_ORIGIN_ENABLED;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");

        MIN_ACTION_DELAY_SECONDS =
            builder.defineInRange(
                "minActionDelaySeconds",
                45,
                1,
                3600
            );

        MAX_ACTION_DELAY_SECONDS =
            builder.defineInRange(
                "maxActionDelaySeconds",
                90,
                1,
                3600
            );

        builder.pop();

        builder.push("actions");

        CROUCH_ENABLED =
            builder.define("crouchEnabled", true);

        JUMP_ENABLED =
            builder.define("jumpEnabled", true);

        ROTATE_ENABLED =
            builder.define("rotateEnabled", true);

        WALK_ENABLED =
            builder.define("walkEnabled", true);

        RETURN_TO_ORIGIN_ENABLED =
            builder.define("returnToOriginEnabled", true);

        builder.pop();

        builder.push("rotation");

        MIN_ROTATE_DEGREES =
            builder.defineInRange(
                "minRotateDegrees",
                20.0,
                1.0,
                180.0
            );

        MAX_ROTATE_DEGREES =
            builder.defineInRange(
                "maxRotateDegrees",
                70.0,
                1.0,
                180.0
            );

        builder.pop();

        builder.push("walking");

        MIN_WALK_DURATION_MS =
            builder.defineInRange(
                "minWalkDurationMilliseconds",
                500,
                100,
                10000
            );

        MAX_WALK_DURATION_MS =
            builder.defineInRange(
                "maxWalkDurationMilliseconds",
                1500,
                100,
                10000
            );

        builder.pop();

        SPEC = builder.build();
    }

    private AntiAfkForgeConfig() {
    }
}