package com.radig.antiafk.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/** Persists user settings without depending on Forge's TOML configuration API. */
public final class AntiAfkConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir()
            .resolve("radigantiafk.json");
    private static final Path OLD_FORGE_CONFIG = FabricLoader.getInstance().getConfigDir()
            .resolve("radigantiafk-client.toml");

    private AntiAfkConfigManager() {}

    public static void load() {
        if (!Files.exists(CONFIG_FILE)) {
            if (Files.exists(OLD_FORGE_CONFIG)) {
                loadOldForgeConfig();
            }
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            if (parsed != null && parsed.isJsonObject()) applyJson(parsed.getAsJsonObject());
        } catch (Exception ex) {
            System.err.println("[Radig Anti-AFK] Error leyendo configuración: " + ex.getMessage());
        }
    }

    private static void applyJson(JsonObject json) {
        AntiAfkConfig.setMinActionDelaySeconds(intValue(json, "minActionDelaySeconds", 45));
        AntiAfkConfig.setMaxActionDelaySeconds(intValue(json, "maxActionDelaySeconds", 90));
        AntiAfkConfig.setCrouchEnabled(boolValue(json, "crouchEnabled", true));
        AntiAfkConfig.setJumpEnabled(boolValue(json, "jumpEnabled", true));
        AntiAfkConfig.setRotateEnabled(boolValue(json, "rotateEnabled", true));
        AntiAfkConfig.setWalkEnabled(boolValue(json, "walkEnabled", true));
        AntiAfkConfig.setReturnToOriginEnabled(boolValue(json, "returnToOriginEnabled", true));
        AntiAfkConfig.setMinRotateDegrees(floatValue(json, "minRotateDegrees", 20.0f));
        AntiAfkConfig.setMaxRotateDegrees(floatValue(json, "maxRotateDegrees", 70.0f));
        AntiAfkConfig.setMinWalkDurationMilliseconds(intValue(json, "minWalkDurationMilliseconds", 500));
        AntiAfkConfig.setMaxWalkDurationMilliseconds(intValue(json, "maxWalkDurationMilliseconds", 1500));
    }

    private static int intValue(JsonObject obj, String key, int fallback) {
        try { return obj.has(key) ? obj.get(key).getAsInt() : fallback; }
        catch (Exception ex) { return fallback; }
    }
    private static float floatValue(JsonObject obj, String key, float fallback) {
        try { return obj.has(key) ? obj.get(key).getAsFloat() : fallback; }
        catch (Exception ex) { return fallback; }
    }
    private static boolean boolValue(JsonObject obj, String key, boolean fallback) {
        try { return obj.has(key) ? obj.get(key).getAsBoolean() : fallback; }
        catch (Exception ex) { return fallback; }
    }

    /** One-time, non-destructive import of matching keys from old Forge's config file. */
    private static void loadOldForgeConfig() {
        Map<String, String> found = new HashMap<>();
        try {
            for (String line : Files.readAllLines(OLD_FORGE_CONFIG, StandardCharsets.UTF_8)) {
                String clean = line.strip();
                if (clean.startsWith("#") || clean.startsWith("[") || !clean.contains("=")) continue;
                String[] parts = clean.split("=", 2);
                found.put(parts[0].trim(), parts[1].split("#", 2)[0].trim());
            }
            JsonObject values = new JsonObject();
            for (Map.Entry<String, String> entry : found.entrySet()) {
                String v = entry.getValue();
                if (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("false"))
                    values.addProperty(entry.getKey(), Boolean.parseBoolean(v));
                else {
                    try { values.addProperty(entry.getKey(), Double.parseDouble(v)); }
                    catch (NumberFormatException ignored) { /* Unrecognized field */ }
                }
            }
            applyJson(values);
            System.out.println("[Radig Anti-AFK] Configuración Forge importada a Fabric.");
        } catch (Exception ex) {
            System.err.println("[Radig Anti-AFK] No se pudo importar TOML previo: " + ex.getMessage());
        }
    }

    public static void save() {
        JsonObject values = new JsonObject();
        values.addProperty("minActionDelaySeconds", AntiAfkConfig.getMinActionDelaySeconds());
        values.addProperty("maxActionDelaySeconds", AntiAfkConfig.getMaxActionDelaySeconds());
        values.addProperty("crouchEnabled", AntiAfkConfig.isCrouchEnabled());
        values.addProperty("jumpEnabled", AntiAfkConfig.isJumpEnabled());
        values.addProperty("rotateEnabled", AntiAfkConfig.isRotateEnabled());
        values.addProperty("walkEnabled", AntiAfkConfig.isWalkEnabled());
        values.addProperty("returnToOriginEnabled", AntiAfkConfig.isReturnToOriginEnabled());
        values.addProperty("minRotateDegrees", AntiAfkConfig.getMinRotateDegrees());
        values.addProperty("maxRotateDegrees", AntiAfkConfig.getMaxRotateDegrees());
        values.addProperty("minWalkDurationMilliseconds", AntiAfkConfig.getMinWalkDurationMilliseconds());
        values.addProperty("maxWalkDurationMilliseconds", AntiAfkConfig.getMaxWalkDurationMilliseconds());
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            Path temporary = CONFIG_FILE.resolveSibling(CONFIG_FILE.getFileName() + ".tmp");
            Files.writeString(temporary, GSON.toJson(values), StandardCharsets.UTF_8);
            try {
                Files.move(temporary, CONFIG_FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicMoveFailed) {
                Files.move(temporary, CONFIG_FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.err.println("[Radig Anti-AFK] No se pudo guardar configuración: " + ex.getMessage());
        }
    }

    public static void resetToDefaults() {
        AntiAfkConfig.setMinActionDelaySeconds(45);
        AntiAfkConfig.setMaxActionDelaySeconds(90);
        AntiAfkConfig.setCrouchEnabled(true);
        AntiAfkConfig.setJumpEnabled(true);
        AntiAfkConfig.setRotateEnabled(true);
        AntiAfkConfig.setWalkEnabled(true);
        AntiAfkConfig.setReturnToOriginEnabled(true);
        AntiAfkConfig.setMinRotateDegrees(20.0f);
        AntiAfkConfig.setMaxRotateDegrees(70.0f);
        AntiAfkConfig.setMinWalkDurationMilliseconds(500);
        AntiAfkConfig.setMaxWalkDurationMilliseconds(1500);
        save();
    }
}
