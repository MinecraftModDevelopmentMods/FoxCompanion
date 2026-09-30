package zone.moddev.mc.skysfoxcompanion.gametest;

import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.RegisterEvent;
import zone.moddev.mc.skysfoxcompanion.FoxCompanion;

/** Registers test-only functions without including them in the production jar. */
public final class GameTestBootstrap {
    private static final Identifier LOAD_TEST = Identifier.fromNamespaceAndPath(
            FoxCompanion.MOD_ID, "load/scaffold_loads");
    private static final Map<Identifier, Consumer<GameTestHelper>> TESTS = Map.of(
            LOAD_TEST, ScaffoldGameTests::scaffoldLoads);

    private GameTestBootstrap() {
    }

    /** Adds the test-function registry callback to the mod event bus. */
    public static void register(IEventBus modBus) {
        modBus.addListener(GameTestBootstrap::registerTestFunctions);
    }

    private static void registerTestFunctions(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.TEST_FUNCTION) {
            TESTS.forEach((id, test) -> event.register(Registries.TEST_FUNCTION, id, () -> test));
        }
    }
}
