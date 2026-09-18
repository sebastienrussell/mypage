package ca.russellmania.mypage

import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import ca.russellmania.mypage.ui.theme.MyPageTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [36],
    qualifiers = "fr-rCA-${RobolectricDeviceQualifiers.Pixel9}",
)
class HomeScreenScreenshotTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeScreenLightMatchesApprovedAppearance() {
        setScreenContent(darkTheme = false)
        composeRule.onRoot().captureExactRoboImage()
    }

    @Test
    fun homeScreenDarkMatchesApprovedAppearance() {
        setScreenContent(darkTheme = true)
        composeRule.onRoot().captureExactRoboImage()
    }

    private fun setScreenContent(darkTheme: Boolean) {
        composeRule.setContent {
            MyPageTheme(
                darkTheme = darkTheme,
                dynamicColor = false,
            ) {
                HomeScreenContent(workingExperienceCurrentYear = 2026)
            }
        }
    }
}
