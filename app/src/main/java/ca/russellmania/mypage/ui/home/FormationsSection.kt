package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.russellmania.mypage.ui.utils.SectionTitle
import ca.russellmania.mypage.ui.utils.TitleWithDateRow

@Composable
@Preview(showBackground = true)
fun FormationsSection(
    modifier: Modifier = Modifier,
    formations: List<FormationInfo> = listOf(
        FormationInfo(
            title = "Android Jetpack Compose",
            date = "Decembre 2023"
        ),
        FormationInfo(
            title = "AngularJS",
            date = "Avril 2020"
        )
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Formations")
        formations.forEach {
            TitleWithDateRow(
                modifier = Modifier.padding(4.dp),
                title = it.title,
                date = it.date
            )
        }
    }
}

data class FormationInfo(
    val title: String = "",
    val date: String = ""
)

