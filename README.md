# Radig Anti-AFK — Fabric 26.3 (port en pruebas)

Edición Fabric **cliente** de Radig Anti-AFK para Minecraft Java **26.3**, Java **25**, Fabric Loader **0.19.5+** y **Fabric API**.

## Funciones
- Acciones anti-AFK aleatorias: agacharse, saltar, caminar y girar suavemente.
- Intervalo configurable, opciones para activar/desactivar acciones, duración y ángulos.
- Regreso aproximado mediante movimiento inverso (no garantiza volver a la coordenada exacta).
- HUD con contador de tiempo para la próxima acción.
- Pantalla de opciones en dos páginas.
- Persistencia en `config/radigantiafk.json` e importación opcional de `radigantiafk-client.toml` si existe.

## Controles
- **F9**: activar/desactivar Anti-AFK.
- **F8**: abrir configuración. En Forge era F7, pero aquí usamos F8 para evitar conflicto con RecordCraft.
- Son reasignables en Controles de Minecraft.

## Desarrollo
En una instalación de **JDK 25**, abrir este directorio en VS Code y ejecutar:

```powershell
.\gradlew.bat --version
.\gradlew.bat clean build
.\gradlew.bat runClient
```

El archivo de distribución esperado está en `build/libs/radigantiafk-fabric-mc26.3-1.0.0.jar`.

**Advertencia:** este port aún requiere compilarse y probarse localmente antes de publicarse.

## Notas
- No requiere instalarse en el servidor; solo se usa del lado cliente.
- Algunos servidores no permiten herramientas anti-AFK. Consulta sus reglas.
- Este mod no garantiza evitar el sistema AFK de todos los servidores.
- Mantener la rama Forge existente intacta; trabajar exclusivamente en `fabric-26.3`.
- El ID `radigantiafk` se mantiene igual, pero los JAR de Forge y Fabric **no deben instalarse juntos**.

## Licencia
All Rights Reserved. Ver `LICENSE.txt`.
