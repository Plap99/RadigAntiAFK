package com.radig.antiafk;

import net.minecraftforge.fml.common.Mod;

import com.radig.antiafk.config.AntiAfkForgeConfig;

import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RadigAntiAFK.MOD_ID)
public final class RadigAntiAFK {

    public static final String MOD_ID = "radigantiafk";

    public RadigAntiAFK(FMLJavaModLoadingContext context) {
        context.registerConfig(
                ModConfig.Type.CLIENT,
                AntiAfkForgeConfig.SPEC,
                "radigantiafk-client.toml");

        System.out.println("[Radig Anti-AFK] Mod iniciado");
    }
}