# Visual regression testing

The visual regression suite compares rendered application content with approved PNG baselines. It uses Roborazzi 1.74.0, Robolectric 4.16.1, Android API 36, native graphics, the Pixel 9 device profile, and the `fr-CA` locale.

The production app still follows the device's dynamic color and current year. Tests disable dynamic color, choose light or dark mode explicitly, and pin the work-experience year to 2026.

## Verify baselines

Run:

```shell
./gradlew :app:verifyRoborazziDebug
```

An exact pixel mismatch fails the task. Generated actual and comparison images are written under the app module's build output. HTML reports and machine-readable results are also kept under generated build directories.

Roborazzi does not guarantee identical rendering across operating systems. Local verification is useful for diagnosis, but Ubuntu 24.04 CI is authoritative.

## Review a mismatch

1. Open the failed GitHub Actions run.
2. Download its visual-test artifact.
3. Compare the approved, actual, and difference images.
4. Fix an unintended change and rerun verification.
5. For an intentional change, generate canonical candidate baselines instead of accepting local output.

To render comparison images without failing on differences, run:

```shell
./gradlew :app:compareRoborazziDebug
```

Comparison images and the HTML report are written under the app module's generated build output.

## Update approved baselines

1. Push the revision that contains the intentional UI change to a branch.
2. Manually run the **Record visual baselines** workflow and enter that branch or commit as the ref.
3. Download the `candidate-visual-baselines` artifact.
4. Inspect every candidate PNG.
5. Replace the approved images in the local test screenshot directory.
6. Commit the reviewed PNG changes with the corresponding UI change.
7. Let normal pull-request CI verify the committed images.

The recording workflow only uploads candidates. It never commits, pushes, merges, or approves them.

For a non-canonical local recording during test development, run:

```shell
./gradlew :app:recordRoborazziDebug
```

Do not approve baselines generated on Windows or macOS.

## Initial coverage

The suite contains eight images:

1. Home-screen viewport in light mode.
2. Home-screen viewport in dark mode.
3. Qualifications section in light mode.
4. Work-experience section in light mode.
5. Education section in light mode.
6. Formations section in light mode.
7. A deterministic single-job work-experience section at the default font scale.
8. The same single-job section at 2x font scale.

System bars, scrolling or stitched full-page images, landscape, tablets, API 37 rendering, and the unused information section are outside this suite.

Focused section captures retain the Pixel 9 width and density. The work-experience section uses a taller test canvas so its complete timeline is captured in one image; only the home-screen captures are constrained to the Pixel 9 viewport height.

## Add coverage

Add a baseline only for a newly supported device, theme, state, or accessibility requirement, or to prevent recurrence of a demonstrated visual defect. Prefer the highest existing public composable seam, give the test a behavior-oriented name, pin all changing inputs, and avoid adding a tolerance unless a reproducible renderer difference proves one is necessary.
