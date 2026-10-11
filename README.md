# Radig Anti-AFK — Fabric 26.2

Versión **Fabric de cliente** de Radig Anti-AFK para **Minecraft Java 26.2**, **Java 25**, **Fabric Loader 0.19.5+** y **Fabric API 0.160.0+26.2**.

## Funcionalidades

- Acciones Anti-AFK aleatorias configurables: agacharse, saltar, caminar y girar suavemente la cámara.
- Intervalo configurable entre acciones, opciones individuales y duración/ángulos ajustables.
- Regreso aproximado mediante movimiento inverso después de caminar (no garantiza regresar exactamente a la posición inicial).
- HUD con tiempo hasta la próxima acción.
- Configuración en dos páginas y persistencia en `config/radigantiafk.json`.
- Importación opcional del antiguo `config/radigantiafk-client.toml` de Forge, si existe.
- Desactivación automática al detectar movimiento del usuario.

## Teclas predeterminadas

- **F9** — Activar/desactivar Anti-AFK.
- **F8** — Abrir configuración.

Ambas pueden reasignarse desde los controles de Minecraft.

## Compilar / desarrollar

Requiere JDK 25 y acceso a las dependencias de Fabric. Desde la raíz del repositorio:

```powershell
.\gradlew.bat clean build
.\gradlew.bat runClient
```

JAR de distribución esperado:

`build/libs/radigantiafk-fabric-mc26.2-1.0.0.jar`

**Estado:** adaptación desde Fabric 26.3. Es necesario comprobar la compilación y realizar pruebas funcionales en Minecraft 26.2 antes de publicarla.

## Compatibilidad / consideraciones

- Es un **mod de cliente**; no hace falta instalarlo en el servidor para usar sus funciones locales.
- Mantiene el ID interno `radigantiafk`, y su configuración existente, para preservar compatibilidad.
- No mezcles archivos JAR de Forge y Fabric en la misma instalación.
- Las reglas de algunos servidores prohíben herramientas anti-AFK y no se garantiza compatibilidad con todos los sistemas de desconexión.
- La rama `fabric-26.3` se conserva separada; para esta edición se utiliza `fabric-26.2`.

## Licencia

All Rights Reserved. Consulta `LICENSE.txt`.
