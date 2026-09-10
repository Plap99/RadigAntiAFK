package com.radig.antiafk;

import net.minecraftforge.fml.common.Mod;

import com.radig.antiafk.config.AntiAfkForgeConfig;

import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.radig.antiafk.config.AntiAfkConfigManager;

import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod(RadigAntiAFK.MOD_ID)
public final class RadigAntiAFK {

    public static final String MOD_ID = "radigantiafk";

    public RadigAntiAFK(FMLJavaModLoadingContext context) {

        context.registerConfig(
                ModConfig.Type.CLIENT,
                AntiAfkForgeConfig.SPEC,
                "radigantiafk-client.toml");

        BusGroup modBusGroup = context.getModBusGroup();

        ModConfigEvent.Loading
                .getBus(modBusGroup)
                .addListener(event -> {

                    if (event.getConfig().getSpec() == AntiAfkForgeConfig.SPEC) {

                        AntiAfkConfigManager.load();

                        System.out.println(
                                "[Radig Anti-AFK] Configuración cargada");
                    }
                });

        System.out.println(
                "[Radig Anti-AFK] Mod iniciado");
    }
}