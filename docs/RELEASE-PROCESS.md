# Release process

Local validation, hosted CI, MMD merge, manual acceptance, artifact upload,
and public indexing are separate evidence states. Do not describe an earlier
state as proof of a later one.

For ordinary work:

1. Create a focused topic branch from the matching stable branch.
2. Reproduce changes locally with focused tests and finish the complete local
   gate before publication work.
3. Push only to the maintainer fork and open an MMD pull request.
4. Verify hosted checks against the exact candidate commit.
5. Merge only the accepted candidate and build release artifacts from the
   exact merged MMD commit.
6. Tag or publish only after fresh approval naming the exact version and
   action.

This scaffold intentionally provides no deployment workflow and no
distribution project ID. Before the first public release, create and verify the
distribution project, add the required organization secrets, adapt the
established MMD Maven/CurseForge/GitHub workflow, and retain a manual live-
publication confirmation. Upload success, marketplace approval, public
indexing, and downloaded artifact checksum verification must be reported
separately.
