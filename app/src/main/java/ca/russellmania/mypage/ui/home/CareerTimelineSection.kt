package ca.russellmania.mypage.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import ca.russellmania.mypage.ui.theme.MyPageTheme
import ca.russellmania.mypage.ui.utils.BulletList
import ca.russellmania.mypage.ui.utils.SectionTitle
import java.time.LocalDate

@Composable
fun CareerTimelineSection(
    modifier: Modifier = Modifier,
    workExperience: Map<String, List<JobInfo>> = defaultWorkExperience,
    educationInfo: EducationInfo = defaultEducation,
    previewCurrentYear: Int? = null,
) {
    val currentYear = previewCurrentYear ?: rememberCurrentYear()
    val contentDirection = LocalLayoutDirection.current
    val periods = buildList {
        workExperience.forEach { (company, jobs) ->
            jobs.forEachIndexed { index, job ->
                add(CareerPeriod.Employment(company, job, showCompany = index == 0))
            }
        }
        add(CareerPeriod.Education(educationInfo))
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Parcours professionnel et scolaire")
        if (periods.isNotEmpty()) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                CareerTimeline(
                    periods = periods,
                    currentYear = currentYear,
                    contentDirection = contentDirection,
                )
            }
        }
    }
}

@Composable
private fun CareerTimeline(
    periods: List<CareerPeriod>,
    currentYear: Int,
    contentDirection: LayoutDirection,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        periods.forEachIndexed { index, period ->
            val nextPeriod = periods.getOrNull(index + 1)
            val hasSharedBoundary = nextPeriod != null &&
                period.startYear == nextPeriod.endYear(currentYear) &&
                !period.isZeroDuration(currentYear) &&
                !nextPeriod.isZeroDuration(currentYear)
            val previousPeriod = periods.getOrNull(index - 1)
            val hasSharedBoundaryWithPrevious = previousPeriod != null &&
                previousPeriod.startYear == period.endYear(currentYear) &&
                !previousPeriod.isZeroDuration(currentYear) &&
                !period.isZeroDuration(currentYear)

            TimelinePeriod(
                period = period,
                currentYear = currentYear,
                showTopNode = index == 0 || !hasSharedBoundaryWithPrevious,
                contentDirection = contentDirection,
            )
            if (nextPeriod != null && !hasSharedBoundary) {
                TimelineGap()
            }
        }
    }
}

@Composable
private fun TimelinePeriod(
    period: CareerPeriod,
    currentYear: Int,
    showTopNode: Boolean,
    contentDirection: LayoutDirection,
) {
    val nodeSize = timelineNodeSize()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .semantics { contentDescription = period.description(currentYear) },
        verticalAlignment = Alignment.Top
    ) {
        TimelineRail(
            topYear = if (showTopNode) period.endYear(currentYear) else null,
            bottomYear = period.startYear,
        )
        CompositionLocalProvider(LocalLayoutDirection provides contentDirection) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (showTopNode) nodeSize + 8.dp else 8.dp,
                        bottom = nodeSize + 8.dp
                ),
                verticalArrangement = Arrangement.Top
            ) {
                CareerPeriodContent(period)
            }
        }
    }
}

@Composable
private fun CareerPeriodContent(period: CareerPeriod) {
    when (period) {
        is CareerPeriod.Employment -> {
            if (period.showCompany) {
                Text(
                    text = period.company.uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            Text(
                text = period.job.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (period.job.subtitle.isNotEmpty()) {
                Text(
                    text = period.job.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50f),
                )
            }
            BulletList(
                modifier = Modifier.padding(top = 8.dp),
                list = period.job.tasks,
            )
        }

        is CareerPeriod.Education -> {
            Text(
                text = period.info.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = period.info.school,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50f),
            )
        }
    }
}

@Composable
private fun TimelineGap() {
    Row(modifier = Modifier.fillMaxWidth()) {
        TimelineConnector(dashed = true)
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun rememberCurrentYear(): Int {
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentYear by remember { mutableIntStateOf(LocalDate.now().year) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentYear = LocalDate.now().year
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    return currentYear
}

private val defaultWorkExperience = mapOf(
    "Groupe Technologie Desjardins" to listOf(
        JobInfo(
            title = "Analyste-programmeur",
            subtitle = "Application AccèsD Android",
            startYear = 2021,
            endYear = ExperienceEnd.Current,
            tasks = listOf(
                "Développer mes compétences Android et Kotlin",
                "Réaliser et maintenir plusieurs solutions critiques pour les membres et clients comme Interac, Détail EOP, Détail CC, liste centralisées, ADN, authentification multi-facteur et DSD",
                "Sécurisation du code mobile en restant à l’affût de nouvelle technologie",
                "Contribution dans une librairie externe pour corriger un problème de code que nous avions depuis la pandémie",
                "Travailler avec Kotlin, Android, Robolectric, Jetpack Compose et Material Design",
                "Encadrer, soutenir et former les escouades de notre direction dans le développement de composantes « custom » en Android",
                "Animer des rencontres pour inviter les employés du Mouvement à discuter entre eux pour trouver des solutions à des problèmes techniques"
            )
        ),
        JobInfo(
            title = "Analyste-programmeur",
            subtitle = "Développement AccèsD Backend",
            startYear = 2018,
            endYear = ExperienceEnd.Year(2021),
            tasks = listOf(
                "Développer et maintenir des services en Java dans l’infonuagique de Desjardins",
                "Conceptualiser des bases de données applicatives pour les services développés",
                "Comprendre et améliorer la réalité des consommateurs de nos services pour répondre à leur(s) besoin",
                "Coder en « Test Driven Developpement » (TDD)",
                "Tester l’ensemble du développement réalisé avec JUnit, Mokito, Powermock et Wiremock",
                "Travailler avec SpringBoot, Redis, Swagger, Bouncy Castle, Postman, Angular et Java"
            )
        ),
        JobInfo(
            title = "Programmeur",
            subtitle = "Support AccèsD",
            startYear = 2016,
            endYear = ExperienceEnd.Year(2018),
            tasks = listOf(
                "Développer une autonomie pour la correction d’incident / problème dans AccèsD",
                "Supporter plusieurs services de Desjardins 24h / 7jours de manière autonome",
                "Discuter, vulgariser, comprendre et investiguer activement sur des ponts téléphoniques pour résoudre une problématique en urgence (P1-P2)",
                "Aider une équipe de développement pour raffiner un processus de conciliation avec Interac",
                "Développer mon réseau de contacts sur l’ensemble des produits offerts chez Desjardins",
                "Travailler avec HPSM, Dynatrace, Java, Weblogic, Synapse, Portail Caisse, Portail Mouvement et Autosys"
            )
        )
    )
)

@Preview(showBackground = true, group = "Career timeline")
@Composable
private fun CareerTimelineCurrentPreview() {
    MyPageTheme { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CareerTimelineDarkPreview() {
    MyPageTheme(darkTheme = true) { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline", fontScale = 2.0f)
@Composable
private fun CareerTimelineLargeTextPreview() {
    MyPageTheme { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline", widthDp = 320)
@Composable
private fun CareerTimelineNarrowPreview() {
    MyPageTheme { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline", widthDp = 891, heightDp = 411)
@Composable
private fun CareerTimelineLandscapePreview() {
    MyPageTheme { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline", locale = "ar")
@Composable
private fun CareerTimelineRtlPreview() {
    MyPageTheme { CareerTimelineSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Career timeline - multiple companies")
@Composable
private fun CareerTimelineMultipleCompaniesPreview() {
    MyPageTheme {
        CareerTimelineSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise A" to listOf(JobInfo("Rôle A", startYear = 2023, endYear = ExperienceEnd.Current)),
                "Entreprise B" to listOf(JobInfo("Rôle B", startYear = 2020, endYear = ExperienceEnd.Year(2023)))
            )
        )
    }
}

@Preview(showBackground = true, group = "Career timeline - gap")
@Composable
private fun CareerTimelineGapPreview() {
    MyPageTheme {
        CareerTimelineSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise" to listOf(
                    JobInfo("Rôle récent", startYear = 2021, endYear = ExperienceEnd.Current),
                    JobInfo("Rôle précédent", startYear = 2018, endYear = ExperienceEnd.Year(2020))
                )
            )
        )
    }
}

@Preview(showBackground = true, group = "Career timeline - repeated year")
@Composable
private fun CareerTimelineRepeatedYearPreview() {
    MyPageTheme {
        CareerTimelineSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise" to listOf(
                    JobInfo("Rôle bref", startYear = 2021, endYear = ExperienceEnd.Year(2021))
                )
            )
        )
    }
}

@Preview(showBackground = true, group = "Career timeline - single period")
@Composable
private fun CareerTimelineSinglePeriodPreview() {
    MyPageTheme {
        CareerTimelineSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise" to listOf(JobInfo("Rôle unique", startYear = 2024, endYear = ExperienceEnd.Current))
            )
        )
    }
}

data class JobInfo(
    val title: String = "",
    val subtitle: String = "",
    val startYear: Int = 0,
    val endYear: ExperienceEnd = ExperienceEnd.Year(0),
    val tasks: List<String> = emptyList()
)

private sealed interface CareerPeriod {
    val startYear: Int

    fun endYear(currentYear: Int): Int

    fun description(currentYear: Int): String

    data class Employment(
        val company: String,
        val job: JobInfo,
        val showCompany: Boolean,
    ) : CareerPeriod {
        override val startYear: Int = job.startYear

        override fun endYear(currentYear: Int): Int = job.endYear.resolve(currentYear)

        override fun description(currentYear: Int): String =
            "${job.title}, de ${job.startYear} à ${job.endYear.resolve(currentYear)}"
    }

    data class Education(val info: EducationInfo) : CareerPeriod {
        override val startYear: Int = info.startYear

        override fun endYear(currentYear: Int): Int = info.endYear

        override fun description(currentYear: Int): String =
            "${info.title}, de ${info.startYear} à ${info.endYear}"
    }
}

private fun CareerPeriod.isZeroDuration(currentYear: Int): Boolean =
    startYear == endYear(currentYear)

sealed interface ExperienceEnd {
    fun resolve(currentYear: Int): Int

    data class Year(val value: Int) : ExperienceEnd {
        override fun resolve(currentYear: Int): Int = value
    }

    data object Current : ExperienceEnd {
        override fun resolve(currentYear: Int): Int = currentYear
    }
}
