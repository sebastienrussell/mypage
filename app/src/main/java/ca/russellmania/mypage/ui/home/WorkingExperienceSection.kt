package ca.russellmania.mypage.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import ca.russellmania.mypage.ui.theme.MyPageTheme
import ca.russellmania.mypage.ui.utils.BulletList
import ca.russellmania.mypage.ui.utils.SectionTitle
import java.time.LocalDate

private const val RailWidth = 56
private const val NodeSize = 52

@Composable
fun WorkingExperienceSection(
    modifier: Modifier = Modifier,
    workExperience: Map<String, List<JobInfo>> = defaultWorkExperience,
    previewCurrentYear: Int? = null
) {
    val currentYear = previewCurrentYear ?: rememberCurrentYear()
    val contentDirection = LocalLayoutDirection.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Expérience de travail")
        workExperience.forEach { (company, jobs) ->
            Text(
                text = company.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            if (jobs.isNotEmpty()) {
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    CompanyTimeline(
                        jobs = jobs,
                        currentYear = currentYear,
                        contentDirection = contentDirection
                    )
                }
            }
        }
    }
}

@Composable
private fun CompanyTimeline(
    jobs: List<JobInfo>,
    currentYear: Int,
    contentDirection: LayoutDirection
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        jobs.forEachIndexed { index, job ->
            val nextJob = jobs.getOrNull(index + 1)
            val hasSharedBoundary = nextJob != null &&
                job.startYear == nextJob.endYear.resolve(currentYear) &&
                !job.isZeroDuration(currentYear) &&
                !nextJob.isZeroDuration(currentYear)
            val hasGap = nextJob != null && !hasSharedBoundary
            val previousJob = jobs.getOrNull(index - 1)
            val hasSharedBoundaryWithPrevious = previousJob != null &&
                previousJob.startYear == job.endYear.resolve(currentYear) &&
                !previousJob.isZeroDuration(currentYear) &&
                !job.isZeroDuration(currentYear)

            TimelinePeriod(
                job = job,
                currentYear = currentYear,
                showTopNode = index == 0 || !hasSharedBoundaryWithPrevious,
                contentDirection = contentDirection
            )
            if (hasGap) {
                TimelineGap()
            }
        }
    }
}

@Composable
private fun TimelinePeriod(
    job: JobInfo,
    currentYear: Int,
    showTopNode: Boolean,
    contentDirection: LayoutDirection
) {
    val nodeSize = timelineNodeSize()
    val periodDescription = "${job.title}, de ${job.startYear} à ${job.endYear.resolve(currentYear)}"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .semantics { contentDescription = periodDescription },
        verticalAlignment = Alignment.Top
    ) {
        TimelineRail(
            topYear = if (showTopNode) job.endYear.resolve(currentYear) else null,
            bottomYear = job.startYear
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
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (job.subtitle.isNotEmpty()) {
                    Text(
                        text = job.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50f)
                    )
                }
                BulletList(
                    modifier = Modifier.padding(top = 8.dp),
                    list = job.tasks
                )
            }
        }
    }
}

@Composable
private fun TimelineGap() {
    Row(modifier = Modifier.fillMaxWidth()) {
        TimelineRail(
            topYear = null,
            bottomYear = null,
            dashed = true,
        )
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun TimelineRail(
    topYear: Int?,
    bottomYear: Int?,
    modifier: Modifier = Modifier,
    dashed: Boolean = false
) {
    val lineColor = MaterialTheme.colorScheme.primary
    val nodeSize = timelineNodeSize()
    Box(
        modifier = modifier
            .width(maxOf(RailWidth.dp, nodeSize))
            .fillMaxHeight()
            .drawBehind {
                val effect = if (dashed) {
                    androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        floatArrayOf(6.dp.toPx(), 6.dp.toPx())
                    )
                } else {
                    null
                }
                drawLine(
                    color = lineColor,
                    start = androidx.compose.ui.geometry.Offset(size.width / 2, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height),
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = effect
                )
            },
        contentAlignment = Alignment.TopCenter
    ) {
        if (topYear != null) {
            TimelineBoundaryNode(year = topYear, nodeSize = nodeSize)
        }
        if (bottomYear != null) {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                TimelineBoundaryNode(year = bottomYear, nodeSize = nodeSize)
            }
        }
    }
}

@Composable
private fun TimelineBoundaryNode(year: Int, nodeSize: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(nodeSize)
            .semantics { contentDescription = year.toString() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.size(nodeSize),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun timelineNodeSize(): Dp =
    maxOf(NodeSize.toFloat(), NodeSize * LocalDensity.current.fontScale).dp

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

@Preview(showBackground = true, group = "Working experience")
@Composable
private fun WorkingExperienceCurrentPreview() {
    MyPageTheme { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WorkingExperienceDarkPreview() {
    MyPageTheme(darkTheme = true) { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience", fontScale = 2.0f)
@Composable
private fun WorkingExperienceLargeTextPreview() {
    MyPageTheme { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience", widthDp = 320)
@Composable
private fun WorkingExperienceNarrowPreview() {
    MyPageTheme { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience", widthDp = 891, heightDp = 411)
@Composable
private fun WorkingExperienceLandscapePreview() {
    MyPageTheme { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience", locale = "ar")
@Composable
private fun WorkingExperienceRtlPreview() {
    MyPageTheme { WorkingExperienceSection(previewCurrentYear = 2026) }
}

@Preview(showBackground = true, group = "Working experience - multiple companies")
@Composable
private fun WorkingExperienceMultipleCompaniesPreview() {
    MyPageTheme {
        WorkingExperienceSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise A" to listOf(JobInfo("Rôle A", startYear = 2023, endYear = ExperienceEnd.Current)),
                "Entreprise B" to listOf(JobInfo("Rôle B", startYear = 2020, endYear = ExperienceEnd.Year(2023)))
            )
        )
    }
}

@Preview(showBackground = true, group = "Working experience - gap")
@Composable
private fun WorkingExperienceGapPreview() {
    MyPageTheme {
        WorkingExperienceSection(
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

@Preview(showBackground = true, group = "Working experience - repeated year")
@Composable
private fun WorkingExperienceRepeatedYearPreview() {
    MyPageTheme {
        WorkingExperienceSection(
            previewCurrentYear = 2026,
            workExperience = mapOf(
                "Entreprise" to listOf(
                    JobInfo("Rôle bref", startYear = 2021, endYear = ExperienceEnd.Year(2021))
                )
            )
        )
    }
}

@Preview(showBackground = true, group = "Working experience - single period")
@Composable
private fun WorkingExperienceSinglePeriodPreview() {
    MyPageTheme {
        WorkingExperienceSection(
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

private fun JobInfo.isZeroDuration(currentYear: Int): Boolean =
    startYear == endYear.resolve(currentYear)

sealed interface ExperienceEnd {
    fun resolve(currentYear: Int): Int

    data class Year(val value: Int) : ExperienceEnd {
        override fun resolve(currentYear: Int): Int = value
    }

    data object Current : ExperienceEnd {
        override fun resolve(currentYear: Int): Int = currentYear
    }
}
