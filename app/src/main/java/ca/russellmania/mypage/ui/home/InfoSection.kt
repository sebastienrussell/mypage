package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    onPhoneNumberClick: (phoneNumber: String) -> Unit = {},
    onEmailAddressClick: (emailAddress: String) -> Unit = {},
    onAddressClick: (address: String) -> Unit = {},
) {
    val nameTextStyle = MaterialTheme.typography.titleLarge.merge(
        color = MaterialTheme.colorScheme.onBackground
    )
    val contactTextStyle = MaterialTheme.typography.titleMedium.merge(
        color = MaterialTheme.colorScheme.primary
    )
    val smallContactTextStyle = MaterialTheme.typography.titleSmall.merge(
        color = MaterialTheme.colorScheme.primary
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectionContainer {
            Text(
                text = AnnotatedString(text = personalInfo.name),
                style = nameTextStyle
            )
        }

        Text(
            modifier = Modifier.clickable { onAddressClick(personalInfo.address) },
            text = AnnotatedString(text = personalInfo.address),
            style = contactTextStyle
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable { onPhoneNumberClick(personalInfo.phone) },
                text = AnnotatedString(text = personalInfo.phone),
                style = smallContactTextStyle
            )
            Text(
                modifier = Modifier.clickable { onEmailAddressClick(personalInfo.email) },
                text = AnnotatedString(text = personalInfo.email),
                style = smallContactTextStyle
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
