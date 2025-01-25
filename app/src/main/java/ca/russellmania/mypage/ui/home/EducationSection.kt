package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.russellmania.mypage.ui.utils.SectionTitle
import ca.russellmania.mypage.ui.utils.TitleWithDateRow

@Composable
@Preview(showBackground = true)
fun EducationSection(
    modifier: Modifier = Modifier,
    educationInfo: EducationInfo = EducationInfo(
        title = "DEC en technique de l'informatique de gestion",
        date = "2013 - 2016",
        school = "CÉGEP André-Laurendeau, Montréal, QC"
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SectionTitle(title = "Éducation")
        TitleWithDateRow(
            title = educationInfo.title,
            subtitle = educationInfo.school,
            date = educationInfo.date
        )
    }
}

data class EducationInfo(
    val title: String = "",
    val date: String = "",
    val school: String = "",
)
