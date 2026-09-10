package com.radig.antiafk.client;

import com.radig.antiafk.config.AntiAfkConfig;
import com.radig.antiafk.config.AntiAfkConfigManager;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class AntiAfkConfigScreen extends Screen {

    private int currentPage = 0;

    private Button crouchButton;
    private Button jumpButton;
    private Button rotateButton;
    private Button walkButton;

    private Button minDelayButton;
    private Button maxDelayButton;

    private Button minRotateButton;
    private Button maxRotateButton;

    private Button minWalkButton;
    private Button maxWalkButton;

    private Button returnButton;

    public AntiAfkConfigScreen() {
        super(Component.literal("Radig Anti-AFK"));
    }

    @Override
    protected void init() {

        if (currentPage == 0) {
            initGeneralPage();
        } else {
            initAdvancedPage();
        }

        initBottomButtons();
    }

    // =========================================================
    // PÁGINA 1
    // =========================================================

    private void initGeneralPage() {

        int centerX = this.width / 2;
        int startY = 50;

        // ---------- INTERVALO MÍNIMO ----------

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMinActionDelaySeconds(
                        AntiAfkConfig.getMinActionDelaySeconds() - 5
                    );

                    updateDelayButtons();
                }
            )
            .bounds(centerX - 100, startY, 30, 20)
            .build()
        );

        minDelayButton = this.addRenderableWidget(
            Button.builder(
                getMinDelayText(),
                button -> {}
            )
            .bounds(centerX - 65, startY, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMinActionDelaySeconds(
                        AntiAfkConfig.getMinActionDelaySeconds() + 5
                    );

                    updateDelayButtons();
                }
            )
            .bounds(centerX + 70, startY, 30, 20)
            .build()
        );

        // ---------- INTERVALO MÁXIMO ----------

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMaxActionDelaySeconds(
                        AntiAfkConfig.getMaxActionDelaySeconds() - 5
                    );

                    updateDelayButtons();
                }
            )
            .bounds(centerX - 100, startY + 25, 30, 20)
            .build()
        );

        maxDelayButton = this.addRenderableWidget(
            Button.builder(
                getMaxDelayText(),
                button -> {}
            )
            .bounds(centerX - 65, startY + 25, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMaxActionDelaySeconds(
                        AntiAfkConfig.getMaxActionDelaySeconds() + 5
                    );

                    updateDelayButtons();
                }
            )
            .bounds(centerX + 70, startY + 25, 30, 20)
            .build()
        );

        // ---------- ACCIONES ----------

        int actionsY = startY + 60;

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
            .bounds(centerX - 100, actionsY, 200, 20)
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
            .bounds(centerX - 100, actionsY + 22, 200, 20)
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
            .bounds(centerX - 100, actionsY + 44, 200, 20)
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
            .bounds(centerX - 100, actionsY + 66, 200, 20)
            .build()
        );
    }

    // =========================================================
    // PÁGINA 2
    // =========================================================

    private void initAdvancedPage() {

        int centerX = this.width / 2;

        // Si la ventana tiene poco alto, compactamos automáticamente.
        boolean compact = this.height < 300;

        int startY = compact ? 48 : 55;
        int rowGap = compact ? 22 : 25;

        // =====================================================
        // GIRO - ÁNGULO MÍNIMO
        // =====================================================

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMinRotateDegrees(
                        AntiAfkConfig.getMinRotateDegrees() - 5.0f
                    );

                    updateRotateButtons();
                }
            )
            .bounds(centerX - 100, startY, 30, 20)
            .build()
        );

        minRotateButton = this.addRenderableWidget(
            Button.builder(
                getMinRotateText(),
                button -> {}
            )
            .bounds(centerX - 65, startY, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMinRotateDegrees(
                        AntiAfkConfig.getMinRotateDegrees() + 5.0f
                    );

                    updateRotateButtons();
                }
            )
            .bounds(centerX + 70, startY, 30, 20)
            .build()
        );

        // =====================================================
        // GIRO - ÁNGULO MÁXIMO
        // =====================================================

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMaxRotateDegrees(
                        AntiAfkConfig.getMaxRotateDegrees() - 5.0f
                    );

                    updateRotateButtons();
                }
            )
            .bounds(centerX - 100, startY + rowGap, 30, 20)
            .build()
        );

        maxRotateButton = this.addRenderableWidget(
            Button.builder(
                getMaxRotateText(),
                button -> {}
            )
            .bounds(centerX - 65, startY + rowGap, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMaxRotateDegrees(
                        AntiAfkConfig.getMaxRotateDegrees() + 5.0f
                    );

                    updateRotateButtons();
                }
            )
            .bounds(centerX + 70, startY + rowGap, 30, 20)
            .build()
        );

        // =====================================================
        // CAMINATA
        // =====================================================

        int walkY = startY + (compact ? 65 : 70);

        // ---------- TIEMPO MÍNIMO ----------

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMinWalkDurationMilliseconds(
                        AntiAfkConfig.getMinWalkDurationMilliseconds() - 100
                    );

                    updateWalkButtons();
                }
            )
            .bounds(centerX - 100, walkY, 30, 20)
            .build()
        );

        minWalkButton = this.addRenderableWidget(
            Button.builder(
                getMinWalkText(),
                button -> {}
            )
            .bounds(centerX - 65, walkY, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMinWalkDurationMilliseconds(
                        AntiAfkConfig.getMinWalkDurationMilliseconds() + 100
                    );

                    updateWalkButtons();
                }
            )
            .bounds(centerX + 70, walkY, 30, 20)
            .build()
        );

        // ---------- TIEMPO MÁXIMO ----------

        this.addRenderableWidget(
            Button.builder(
                Component.literal("-"),
                button -> {
                    AntiAfkConfig.setMaxWalkDurationMilliseconds(
                        AntiAfkConfig.getMaxWalkDurationMilliseconds() - 100
                    );

                    updateWalkButtons();
                }
            )
            .bounds(centerX - 100, walkY + rowGap, 30, 20)
            .build()
        );

        maxWalkButton = this.addRenderableWidget(
            Button.builder(
                getMaxWalkText(),
                button -> {}
            )
            .bounds(centerX - 65, walkY + rowGap, 130, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.literal("+"),
                button -> {
                    AntiAfkConfig.setMaxWalkDurationMilliseconds(
                        AntiAfkConfig.getMaxWalkDurationMilliseconds() + 100
                    );

                    updateWalkButtons();
                }
            )
            .bounds(centerX + 70, walkY + rowGap, 30, 20)
            .build()
        );

        // =====================================================
        // REGRESAR AL ORIGEN
        // =====================================================

        int returnY =
            walkY
            + rowGap
            + (compact ? 23 : 27);

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
            .bounds(
                centerX - 100,
                returnY,
                200,
                20
            )
            .build()
        );

        // =====================================================
        // RESTABLECER
        // =====================================================

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Restablecer valores predeterminados"),
                button -> {
                    AntiAfkConfigManager.resetToDefaults();

                    this.clearWidgets();
                    this.init();
                }
            )
            .bounds(
                centerX - 100,
                this.height - 55,
                200,
                20
            )
            .build()
        );
    }

    // =========================================================
    // BOTONES FIJOS
    // =========================================================

    private void initBottomButtons() {

        int centerX = this.width / 2;

        int buttonWidth = 97;
        int gap = 6;
        int bottomY = this.height - 30;

        // Navegación
        this.addRenderableWidget(
            Button.builder(
                Component.literal(
                    currentPage == 0
                        ? "Siguiente >"
                        : "< Anterior"
                ),
                button -> changePage(
                    currentPage == 0 ? 1 : 0
                )
            )
            .bounds(
                centerX - 100,
                bottomY,
                buttonWidth,
                20
            )
            .build()
        );

        // Cerrar
        this.addRenderableWidget(
            Button.builder(
                Component.literal("Cerrar"),
                button -> this.onClose()
            )
            .bounds(
                centerX - 100 + buttonWidth + gap,
                bottomY,
                buttonWidth,
                20
            )
            .build()
        );
    }

    // =========================================================
    // CAMBIO DE PÁGINA
    // =========================================================

    private void changePage(int page) {

        currentPage = page;

        this.clearWidgets();
        this.init();
    }

    // =========================================================
    // RENDER
    // =========================================================

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
            20,
            0xFFFFFFFF
        );

        if (currentPage == 0) {

            guiGraphics.centeredText(
                this.font,
                Component.literal("Intervalo entre acciones"),
                this.width / 2,
                40,
                0xFFAAAAAA
            );

            guiGraphics.centeredText(
                this.font,
                Component.literal("Acciones"),
                this.width / 2,
                100,
                0xFFAAAAAA
            );

        } else {

            boolean compact = this.height < 300;

            guiGraphics.centeredText(
                this.font,
                Component.literal("Giro"),
                this.width / 2,
                compact ? 38 : 45,
                0xFFAAAAAA
            );

            guiGraphics.centeredText(
                this.font,
                Component.literal("Caminata"),
                this.width / 2,
                compact ? 103 : 110,
                0xFFAAAAAA
            );
        }

        super.extractRenderState(
            guiGraphics,
            mouseX,
            mouseY,
            partialTicks
        );
    }

    // =========================================================
    // GUARDAR
    // =========================================================

    @Override
    public void onClose() {

        AntiAfkConfigManager.save();

        super.onClose();
    }

    // =========================================================
    // TEXTOS
    // =========================================================

    private Component getCrouchText() {
        return Component.literal(
            "Agacharse: "
            + (
                AntiAfkConfig.isCrouchEnabled()
                    ? "ON"
                    : "OFF"
            )
        );
    }

    private Component getJumpText() {
        return Component.literal(
            "Saltar: "
            + (
                AntiAfkConfig.isJumpEnabled()
                    ? "ON"
                    : "OFF"
            )
        );
    }

    private Component getRotateText() {
        return Component.literal(
            "Girar: "
            + (
                AntiAfkConfig.isRotateEnabled()
                    ? "ON"
                    : "OFF"
            )
        );
    }

    private Component getWalkText() {
        return Component.literal(
            "Caminar: "
            + (
                AntiAfkConfig.isWalkEnabled()
                    ? "ON"
                    : "OFF"
            )
        );
    }

    private Component getMinDelayText() {
        return Component.literal(
            "Mínimo: "
            + AntiAfkConfig.getMinActionDelaySeconds()
            + " s"
        );
    }

    private Component getMaxDelayText() {
        return Component.literal(
            "Máximo: "
            + AntiAfkConfig.getMaxActionDelaySeconds()
            + " s"
        );
    }

    private Component getMinRotateText() {
        return Component.literal(
            "Mínimo: "
            + Math.round(
                AntiAfkConfig.getMinRotateDegrees()
            )
            + "°"
        );
    }

    private Component getMaxRotateText() {
        return Component.literal(
            "Máximo: "
            + Math.round(
                AntiAfkConfig.getMaxRotateDegrees()
            )
            + "°"
        );
    }

    private Component getMinWalkText() {
        return Component.literal(
            "Mínimo: "
            + String.format(
                "%.1f s",
                AntiAfkConfig
                    .getMinWalkDurationMilliseconds()
                    / 1000.0
            )
        );
    }

    private Component getMaxWalkText() {
        return Component.literal(
            "Máximo: "
            + String.format(
                "%.1f s",
                AntiAfkConfig
                    .getMaxWalkDurationMilliseconds()
                    / 1000.0
            )
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

    // =========================================================
    // ACTUALIZAR BOTONES
    // =========================================================

    private void updateDelayButtons() {

        minDelayButton.setMessage(
            getMinDelayText()
        );

        maxDelayButton.setMessage(
            getMaxDelayText()
        );
    }

    private void updateRotateButtons() {

        minRotateButton.setMessage(
            getMinRotateText()
        );

        maxRotateButton.setMessage(
            getMaxRotateText()
        );
    }

    private void updateWalkButtons() {

        minWalkButton.setMessage(
            getMinWalkText()
        );

        maxWalkButton.setMessage(
            getMaxWalkText()
        );
    }
}