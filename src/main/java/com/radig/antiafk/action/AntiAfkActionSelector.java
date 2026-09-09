package com.radig.antiafk.action;

import com.radig.antiafk.config.AntiAfkConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class AntiAfkActionSelector {

    private AntiAfkActionSelector() {
    }

    public static AntiAfkAction getRandomEnabledAction() {

        List<AntiAfkAction> enabledActions = new ArrayList<>();

        for (AntiAfkAction action : AntiAfkAction.values()) {
            if (AntiAfkConfig.isActionEnabled(action)) {
                enabledActions.add(action);
            }
        }

        if (enabledActions.isEmpty()) {
            return null;
        }

        int index = ThreadLocalRandom.current()
            .nextInt(enabledActions.size());

        return enabledActions.get(index);
    }
}