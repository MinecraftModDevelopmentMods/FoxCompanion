package zone.moddev.mc.skysfoxcompanion;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/** NeoForge entry point for Fox Companion. */
@Mod(FoxCompanion.MOD_ID)
public final class FoxCompanion {
    /** Permanent mod identifier and resource namespace. */
    public static final String MOD_ID = "skysfoxcompanion";
    /** Human-readable project name. */
    public static final String NAME = "Fox Companion";
    /** Candidate version for the initial Minecraft 26.3 scaffold. */
    public static final String VERSION = "0.1.0.2603002";
    /** Project logger. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /** Creates the mod and registers development-only GameTests when present. */
    public FoxCompanion(IEventBus modBus) {
        registerGameTests(modBus);
        LOGGER.info("Loading {} {}", NAME, VERSION);
    }

    private static void registerGameTests(IEventBus modBus) {
        try {
            Class<?> bootstrap = Class.forName(
                    "zone.moddev.mc.skysfoxcompanion.gametest.GameTestBootstrap");
            bootstrap.getMethod("register", IEventBus.class).invoke(null, modBus);
        } catch (ClassNotFoundException exception) {
            String enabledNamespaces = System.getProperty(
                    "neoforge.enabledGameTestNamespaces", "");
            if (Arrays.stream(enabledNamespaces.split(","))
                    .map(String::trim)
                    .anyMatch(MOD_ID::equals)) {
                throw new IllegalStateException(
                        "GameTest source set is missing from the development run", exception);
            }
        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException exception) {
            throw new IllegalStateException("Could not register Fox Companion GameTests", exception);
        }
    }
}
