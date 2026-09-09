package com.radig.antiafk.client;

import com.radig.antiafk.config.AntiAfkConfig;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class AntiAfkConfigScreen extends Screen {

    private Button crouchButton;
    private Button jumpButton;
    private Button rotateButton;
    private Button walkButton;
    private Button returnButton;

    public AntiAfkConfigScreen() {
        super(Component.literal("Radig Anti-AFK"));
    }

    @Override
    protected void init() {

        int centerX = this.width / 2;
        int startY = this.height / 2 - 70;

        crouchButton = this.addRenderableWidget(
            Button.builder(
                getCrouchText(),
                button -> {
                    AntiAfkConfig.setCrouchEnabled(
                        !AntiAfkConfig.isCrouchEnabled()
                    );

                    crouchButton.setMessage(getCrouchText());
                }
            )
            .bounds(centerX - 100, startY, 200, 20)
            .build()
        );

        jumpButton = this.addRenderableWidget(
            Button.builder(
                getJumpText(),
                button -> {
                    AntiAfkConfig.setJumpEnabled(
                        !AntiAfkConfig.isJumpEnabled()
                    );

                    jumpButton.setMessage(getJumpText());
                }
            )
            .bounds(centerX - 100, startY + 25, 200, 20)
            .build()
        );

        rotateButton = this.addRenderableWidget(
            Button.builder(
                getRotateText(),
                button -> {
                    AntiAfkConfig.setRotateEnabled(
                        !AntiAfkConfig.isRotateEnabled()
                    );

                    rotateButton.setMessage(getRotateText());
                }
            )
            .bounds(centerX - 100, startY + 50, 200, 20)
            .build()
        );

        walkButton = this.addRenderableWidget(
            Button.builder(
                getWalkText(),
                button -> {
                    AntiAfkConfig.setWalkEnabled(
                        !AntiAfkConfig.isWalkEnabled()
                    );

                    walkButton.setMessage(getWalkText());
                }
            )
            .bounds(centerX - 100, startY + 75, 200, 20)
            .build()
        );

        returnButton = this.addRenderableWidget(
            Button.builder(
                getReturnText(),
                button -> {
                    AntiAfkConfig.setReturnToOriginEnabled(
                        !AntiAfkConfig.isReturnToOriginEnabled()
                    );

                    returnButton.setMessage(getReturnText());
                }
            )
            .bounds(centerX - 100, startY + 100, 200, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Cerrar"),
                button -> this.minecraft.gui.setScreen(null)
            )
            .bounds(centerX - 100, startY + 140, 200, 20)
            .build()
        );
    }

    @Override
    public void extractRenderState(
        GuiGraphicsExtractor guiGraphics,
        int mouseX,
        int mouseY,
        float partialTicks
    ) {
        guiGraphics.centeredText(
            this.font,
            this.title,
            this.width / 2,
            30,
            0xFFFFFFFF
        );

        super.extractRenderState(
            guiGraphics,
            mouseX,
            mouseY,
            partialTicks
        );
    }

    private Component getCrouchText() {
        return Component.literal(
            "Agacharse: "
            + (AntiAfkConfig.isCrouchEnabled() ? "ON" : "OFF")
        );
    }

    private Component getJumpText() {
        return Component.literal(
            "Saltar: "
            + (AntiAfkConfig.isJumpEnabled() ? "ON" : "OFF")
        );
    }

    private Component getRotateText() {
        return Component.literal(
            "Girar: "
            + (AntiAfkConfig.isRotateEnabled() ? "ON" : "OFF")
        );
    }

    private Component getWalkText() {
        return Component.literal(
            "Caminar: "
            + (AntiAfkConfig.isWalkEnabled() ? "ON" : "OFF")
        );
    }

    private Component getReturnText() {
        return Component.literal(
            "Regresar al origen: "
            + (
                AntiAfkConfig.isReturnToOriginEnabled()
                    ? "ON"
                    : "OFF"
            )
        );
    }
}