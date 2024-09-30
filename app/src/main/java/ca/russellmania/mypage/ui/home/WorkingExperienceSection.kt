package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.russellmania.mypage.ui.utils.BulletList
import ca.russellmania.mypage.ui.utils.SectionTitle
import ca.russellmania.mypage.ui.utils.TitleWithDateRow

@Composable
@Preview(showBackground = true)
fun WorkingExperienceSection(
    modifier: Modifier = Modifier,
    workExperience: Map<String, List<JobInfo>> = mapOf(
        "Groupe Technologie Desjardins" to listOf(
            JobInfo(
                title = "Analyste-programmeur",
                subtitle = "Application AccèsD Android",
                date = "2021 - Présent",
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
                date = "2018 - 2021",
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
                date = "2016 - 2018",
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
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Expérience de travail")
        workExperience.forEach { (company, jobs) ->
            Text(
                text = company.uppercase(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            jobs.forEach { job ->
                JobInfo(jobInfo = job)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, group = "JobInfo")
@Preview(showBackground = true, group = "JobInfo", fontScale = 2.0f)
fun JobInfo(
    modifier: Modifier = Modifier,
    jobInfo: JobInfo = JobInfo(
        title = "Analyste-programmeur",
        subtitle = "Application AccèsD Android",
        date = "2021 - Présent",
        tasks = listOf(
            "Développer mes compétences Android et Kotlin",
            "Réaliser et maintenir plusieurs solutions critiques pour les membres et clients comme Interac, Détail EOP, Détail CC, liste centralisées, ADN, authentification multi-facteur et DSD",
            "Sécurisation du code mobile en restant à l’affut de nouvelle technologie",
            "Contribution dans une librairie externe pour corriger un problème de code que nous avions depuis la pandémie",
            "Travailler avec Kotlin, Android, Robolectric, Jetpack Compose et Material Design",
            "Encadrer, soutenir et former les escouades de notre direction dans le développement de composantes « custom » en Android",
            "Animer des rencontres pour inviter les employés du Mouvement à discuter entre eux pour trouver des solutions à des problèmes techniques"
        )
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        TitleWithDateRow(
            title = jobInfo.title,
            subtitle = jobInfo.subtitle,
            date = jobInfo.date
        )
        BulletList(
            modifier = Modifier.padding(top = 8.dp),
            list = jobInfo.tasks
        )
    }
}


data class JobInfo(
    val title: String = "",
    val subtitle: String = "",
    val date: String = "",
    val tasks: List<String> = emptyList()
)