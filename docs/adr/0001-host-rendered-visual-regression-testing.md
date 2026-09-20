# Use host-rendered visual regression tests

We use Roborazzi with stable Robolectric API 36 and a Pixel 9 device profile to compare the Compose UI on the JVM, with Ubuntu 24.04 as the canonical renderer. This avoids emulator cost and gives pull requests fast visual feedback, while deliberately accepting that these baselines do not validate API 37 platform rendering and that changing renderers may require replacing them; emulator testing and Paparazzi were rejected for the initial suite because they add operational cost or prerelease compatibility risk.
