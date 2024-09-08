package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.russellmania.mypage.ui.utils.BulletList
import ca.russellmania.mypage.ui.utils.SectionTitle

@Composable
@Preview(showBackground = true)
fun QualificationsSection(
    modifier: Modifier = Modifier,
    qualifications: List<String> = listOf(
        "Analyser et réaliser des fonctionnalités à partir d’un besoin",
        "Guider, vulgariser, questionner et comprendre mes collègues de travail pour réaliser des fonctionnalités moderne et simple",
        "Assurer la qualité des fonctionnalités codées",
        "Contribuer aux revues de code en donnant des pistes d’amélioration",
        "Préparer des ateliers d’amélioration de processus interne ou d’apprentissage collectif",
        "Désir de toujours m’améliorer et me re-questionner pour peaufiner mes façons de travailler",
        "Spécialisation dans le développement mobile (Android, Kotlin)"
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Sommaire des qualifications")
        BulletList(list = qualifications)
    }
}
