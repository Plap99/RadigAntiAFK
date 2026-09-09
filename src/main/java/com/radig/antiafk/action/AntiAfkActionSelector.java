package com.radig.antiafk.action;

import com.radig.antiafk.config.AntiAfkConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class AntiAfkActionSelector {

    private AntiAfkActionSelector() {
    }

    public static AntiAfkAction getRandomEnabledAction() {
        List<AntiAfkAction> actionTypes = new ArrayList<>();

        if (AntiAfkConfig.isCrouchEnabled()) {
            actionTypes.add(AntiAfkAction.CROUCH);
        }

        if (AntiAfkConfig.isJumpEnabled()) {
            actionTypes.add(AntiAfkAction.JUMP);
        }

        if (AntiAfkConfig.isRotateEnabled()) {
            actionTypes.add(
                    ThreadLocalRandom.current().nextBoolean()
                            ? AntiAfkAction.ROTATE_LEFT
                            : AntiAfkAction.ROTATE_RIGHT);
        }

        if (AntiAfkConfig.isWalkEnabled()) {

            AntiAfkAction[] walkActions = {
                    AntiAfkAction.WALK_FORWARD,
                    AntiAfkAction.WALK_BACKWARD,
                    AntiAfkAction.WALK_LEFT,
                    AntiAfkAction.WALK_RIGHT
            };

            actionTypes.add(
                    walkActions[ThreadLocalRandom.current()
                            .nextInt(walkActions.length)]);
        }

        if (actionTypes.isEmpty()) {
            return null;
        }

        return actionTypes.get(
                ThreadLocalRandom.current()
                        .nextInt(actionTypes.size()));
    }
}