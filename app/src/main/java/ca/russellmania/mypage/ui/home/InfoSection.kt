package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun InfoSection(
    modifier: Modifier = Modifier,
    personalInfo: PersonalInfo = PersonalInfo(
        name = "Sebastien Russell",
        address = "Mercier, QC, J6R 0G2",
        phone = "(438) 403-9294",
        email = "russell.sebas@gmail.com"
    )
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = personalInfo.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = personalInfo.address, style = MaterialTheme.typography.titleSmall)
        Text(
            text = "${personalInfo.phone} | ${personalInfo.email}",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

data class PersonalInfo(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = ""

)