package ca.russellmania.mypage.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleWithDateRow(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    date: String = ""
) {
    Row(modifier, verticalAlignment = Alignment.Top) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(0.70F),
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle.isNotEmpty())
                Text(
                    modifier = Modifier.fillMaxWidth(0.70F),
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50F)
                )
        }
        DateCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End),
            date = date
        )
    }
}

@Composable
fun DateCard(modifier: Modifier = Modifier, date: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = date,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleWithDateRowPreview() {
    TitleWithDateRow(
        title = "Android Jetpack Compose",
        subtitle = "asdflhkjh",
        date = "Decembre 2023"
    )

}