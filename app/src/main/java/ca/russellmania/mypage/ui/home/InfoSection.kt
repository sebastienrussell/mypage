package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
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
    ),
    onClickPhoneNumber: (phoneNumber: String) -> Unit = {},
    onClickEmailAddress: (emailAddress: String) -> Unit = {},
    onClickAddress: (address: String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectionContainer {
            Text(
                text = personalInfo.name,
                style = MaterialTheme.typography.titleLarge.merge(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        }

        ClickableText(
            text = AnnotatedString(text = personalInfo.address),
            style = MaterialTheme.typography.titleMedium.merge(
                color = MaterialTheme.colorScheme.primary
            ),
            onClick = { onClickAddress(personalInfo.address) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableText(
                text = AnnotatedString(text = personalInfo.phone),
                style = MaterialTheme.typography.titleSmall.merge(
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = { onClickPhoneNumber(personalInfo.phone) }
            )
            ClickableText(
                text = AnnotatedString(text = personalInfo.email),
                style = MaterialTheme.typography.titleSmall.merge(
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = { onClickEmailAddress(personalInfo.email) }
            )
        }
    }
}

data class PersonalInfo(
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = ""

)