package zone.moddev.mc.skysfoxcompanion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.Test;

public class ProjectContractTest {
    @Test
    public void targetAndPermanentIdentityArePinned() throws Exception {
        Properties properties = readProperties("gradle.properties");
        assertEquals("26.3", properties.getProperty("minecraft_version"));
        assertEquals("[26.3]", properties.getProperty("minecraft_version_range"));
        assertEquals("26.3.0.23-beta", properties.getProperty("neo_version"));
        assertEquals("[26.3.0.23-beta,26.4)",
                properties.getProperty("neo_version_range"));
        assertEquals("neoforge", properties.getProperty("loader_name"));
        assertEquals("2", properties.getProperty("loader_code"));
        assertEquals("25.0.3+9", properties.getProperty("java_toolchain_version"));
        assertEquals("skysfoxcompanion", properties.getProperty("mod_id"));
        assertEquals("zone.moddev.mc.skysfoxcompanion",
                properties.getProperty("mod_group_id"));
        assertEquals("0.1.0.2603002", properties.getProperty("mod_version"));
        assertEquals("LGPL-2.1-only", properties.getProperty("mod_license"));

        String wrapper = read("gradle/wrapper/gradle-wrapper.properties");
        assertTrue(wrapper.contains("gradle-9.2.1-bin.zip"));
        assertEquals("LGPL-2.1-only", read("LICENSE.spdx").trim());
    }

    @Test
    public void entryPointExposesOnlyTheInitialStableContract() throws Exception {
        String main = read("src/main/java/zone/moddev/mc/skysfoxcompanion/FoxCompanion.java");
        assertTrue(main.contains("@Mod(FoxCompanion.MOD_ID)"));
        assertTrue(main.contains("MOD_ID = \"skysfoxcompanion\""));
        assertTrue(main.contains("NAME = \"Fox Companion\""));
        assertTrue(main.contains("VERSION = \"0.1.0.2603002\""));
        assertFalse(main.contains("DeferredRegister"));
        assertFalse(main.contains("registerConfig"));
    }

    @Test
    public void metadataAndPackFormatsTargetNeoForge263() throws Exception {
        String metadata = read("src/main/resources/META-INF/neoforge.mods.toml");
        assertTrue(metadata.contains("modId=\"${mod_id}\""));
        assertTrue(metadata.contains("versionRange=\"${neo_version_range}\""));
        assertTrue(metadata.contains("versionRange=\"${minecraft_version_range}\""));
        assertTrue(metadata.contains("MinecraftModDevelopmentMods/FoxCompanion"));
        assertFalse(metadata.contains("examplemod"));

        String pack = read("src/main/resources/pack.mcmeta");
        assertTrue(pack.contains("\"max_format\": 121"));
        assertTrue(pack.contains("97"));
        assertTrue(Files.isRegularFile(Path.of(
                "src/main/resources/assets/skysfoxcompanion/lang/en_us.json")));
    }

    @Test
    public void ciTargetsStableBranchWithoutPublication() throws Exception {
        for (String workflow : new String[] {"ci.yml", "codeql-analysis.yml",
                "validate-gradle-build.yml"}) {
            String text = read(".github/workflows/" + workflow);
            assertTrue(workflow, text.contains("master-26.3-neo"));
        }
        assertFalse(Files.exists(Path.of(".github/workflows/deploy-release.yml")));
        assertFalse(read("gradle.properties").contains("curseforge_project_id"));
    }

    @Test
    public void officialMdkProvenanceIsRecorded() throws Exception {
        String baseline = read("docs/MDK-BASELINE.md");
        assertTrue(baseline.contains(
                "fe95c10fa47acfa18ef01976a82c090654e04aa7"));
        assertTrue(baseline.contains("MDK-26.3-NeoGradle"));
        assertFalse(baseline.contains("examplemod"));
    }

    private static Properties readProperties(String path) throws IOException {
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        return properties;
    }

    private static String read(String path) throws IOException {
        return Files.readString(Path.of(path), StandardCharsets.UTF_8);
    }
}
