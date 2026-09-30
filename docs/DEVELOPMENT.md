# Development

Fox Companion keeps each Minecraft/loader target in its own checkout. This
branch is `master-26.3-neo` and uses:

- Minecraft 26.3;
- NeoForge 26.3.0.23-beta;
- NeoGradle userdev 7.1.39;
- Gradle 9.2.1;
- Temurin Java 25.0.3+9.

NeoForge development uses the official NeoGradle MDK. A NeoForge installer is
for packaged client/server verification and is not a development scaffold.
Forge and NeoForge lines must remain separate because their Gradle plugins,
metadata, events, registries, artifacts, and validation evidence differ.

Run one Gradle process at a time. Select the required Java runtime and a local
Gradle user home, then run:

```powershell
.\gradlew.bat clean --no-daemon
.\gradlew.bat check build javadoc verifyReleaseArtifacts writeReleaseChecksums --no-daemon --stacktrace
.\gradlew.bat verifyEclipseProductionClasspath --no-daemon --stacktrace
.\gradlew.bat runGameTestServer -PfoxCompanionGameTestRunDirectory=build/game-test-run --no-daemon --stacktrace
```

`eclipse` is the NeoGradle IDE setup task. Do not use ForgeGradle's
`genEclipseRuns` task on this branch. Import or refresh the generated project
through Eclipse Buildship and run the Gradle `runClient` or `runServer` tasks
for development launches.

The permanent identities established by this scaffold are:

- mod/resource namespace: `skysfoxcompanion`;
- package and Maven group: `zone.moddev.mc.skysfoxcompanion`;
- artifact base name: `FoxCompanion`.

No gameplay API, registry IDs, configuration keys, or saved-data schemas are
defined yet. Treat each of those as a compatibility decision when introduced.
