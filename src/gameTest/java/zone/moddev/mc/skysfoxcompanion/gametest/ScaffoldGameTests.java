package zone.moddev.mc.skysfoxcompanion.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import zone.moddev.mc.skysfoxcompanion.FoxCompanion;

/** Minimal runtime proof that the production entry point loaded. */
public final class ScaffoldGameTests {
    private ScaffoldGameTests() {
    }

    /** Confirms the permanent identity and candidate version at runtime. */
    public static void scaffoldLoads(GameTestHelper helper) {
        if (!"skysfoxcompanion".equals(FoxCompanion.MOD_ID)) {
            helper.fail("Unexpected Fox Companion mod identifier");
            return;
        }
        if (!"0.1.0.2603002".equals(FoxCompanion.VERSION)) {
            helper.fail("Unexpected Fox Companion candidate version");
            return;
        }
        helper.succeed();
    }
}
