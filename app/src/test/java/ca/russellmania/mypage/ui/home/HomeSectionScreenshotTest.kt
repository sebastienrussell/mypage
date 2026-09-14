package ca.russellmania.mypage.ui.home

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Density
import androidx.test.ext.junit.runners.AndroidJUnit4
import ca.russellmania.mypage.captureExactRoboImage
import ca.russellmania.mypage.ui.theme.MyPageTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

private const val ScreenshotTag = "screenshot-subject"

private val singleJobExperience = mapOf(
    "Entreprise" to listOf(
        JobInfo(
            title = "Développeur Android",
            subtitle = "Application mobile",
            startYear = 2022,
            endYear = ExperienceEnd.Year(2026),
            tasks = listOf(
                "Développer des interfaces accessibles",
                "Assurer la qualité visuelle de l’application",
            ),
        )
    )
)

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [36],
    qualifiers = "fr-rCA-${RobolectricDeviceQualifiers.Pixel9}",
)
class HomeSectionScreenshotTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun qualificationsSectionMatchesApprovedAppearance() {
        setSectionContent {
            QualificationsSection()
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    @Test
    fun educationSectionMatchesApprovedAppearance() {
        setSectionContent {
            EducationSection()
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    @Test
    fun formationsSectionMatchesApprovedAppearance() {
        setSectionContent {
            FormationsSection()
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    @Test
    @Config(qualifiers = "+h2400dp")
    fun workingExperienceSectionMatchesApprovedAppearance() {
        setSectionContent {
            WorkingExperienceSection(previewCurrentYear = 2026)
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    @Test
    fun singleJobExperienceMatchesApprovedAppearance() {
        setSectionContent {
            WorkingExperienceSection(
                workExperience = singleJobExperience,
                previewCurrentYear = 2026,
            )
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    @Test
    fun singleJobExperienceAtTwoTimesFontScaleMatchesApprovedAppearance() {
        setSectionContent(fontScale = 2f) {
            WorkingExperienceSection(
                workExperience = singleJobExperience,
                previewCurrentYear = 2026,
            )
        }
        composeRule.onNodeWithTag(ScreenshotTag).captureExactRoboImage()
    }

    private fun setSectionContent(
        fontScale: Float = 1f,
        content: @Composable () -> Unit,
    ) {
        composeRule.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = density.density,
                    fontScale = fontScale,
                )
            ) {
                MyPageTheme(
                    darkTheme = false,
                    dynamicColor = false,
                ) {
                    Surface(modifier = Modifier.testTag(ScreenshotTag)) {
                        content()
                    }
                }
            }
        }
    }
}
