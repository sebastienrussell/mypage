package ca.russellmania.mypage.ui.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.assertCountEquals
import ca.russellmania.mypage.ui.theme.MyPageTheme
import org.junit.Rule
import org.junit.Test

class WorkingExperienceSectionTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun currentYearAndContiguousBoundariesAreDisplayed() {
        composeRule.setContent {
            MyPageTheme {
                WorkingExperienceSection(
                    workExperience = mapOf(
                        "Entreprise" to listOf(
                            JobInfo("Rôle actuel", startYear = 2021, endYear = ExperienceEnd.Current),
                            JobInfo("Rôle précédent", startYear = 2018, endYear = ExperienceEnd.Year(2021)),
                            JobInfo("Premier rôle", startYear = 2016, endYear = ExperienceEnd.Year(2018))
                        )
                    ),
                    previewCurrentYear = 2026
                )
            }
        }

        composeRule.onAllNodesWithText("2026").assertCountEquals(1)
        composeRule.onAllNodesWithText("2021").assertCountEquals(1)
        composeRule.onAllNodesWithText("2018").assertCountEquals(1)
        composeRule.onAllNodesWithText("2016").assertCountEquals(1)
        composeRule.onAllNodesWithContentDescription("Rôle actuel, de 2021 à 2026").assertCountEquals(1)
        composeRule.onAllNodesWithText("2021 - Présent").assertCountEquals(0)
    }

    @Test
    fun gapsRemainVisibleAsSeparateSegments() {
        composeRule.setContent {
            MyPageTheme {
                WorkingExperienceSection(
                    workExperience = mapOf(
                        "Entreprise" to listOf(
                            JobInfo("Rôle récent", startYear = 2021, endYear = ExperienceEnd.Current),
                            JobInfo("Rôle séparé", startYear = 2018, endYear = ExperienceEnd.Year(2020)),
                        )
                    ),
                    previewCurrentYear = 2026
                )
            }
        }

        composeRule.onAllNodesWithText("2020").assertCountEquals(1)
    }

    @Test
    fun sameYearStartAndEndRemainDistinctNodes() {
        composeRule.setContent {
            MyPageTheme {
                WorkingExperienceSection(
                    workExperience = mapOf(
                        "Entreprise" to listOf(
                            JobInfo("Rôle bref", startYear = 2021, endYear = ExperienceEnd.Year(2021))
                        )
                    ),
                    previewCurrentYear = 2026
                )
            }
        }

        composeRule.onAllNodesWithText("2021").assertCountEquals(2)
    }
}
