package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ca.russellmania.mypage.ui.utils.SectionTitle

@Composable
@Preview(showBackground = true)
fun FormationsSection(
    modifier: Modifier = Modifier,
    formations: List<FormationInfo> = listOf(
        FormationInfo(
            title = "Android Jetpack Compose",
            month = "Décembre",
            year = 2023,
        ),
        FormationInfo(
            title = "AngularJS",
            month = "Avril",
            year = 2020,
        ),
    ),
) {
    val contentDirection = LocalLayoutDirection.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SectionTitle(title = "Formations")
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(modifier = Modifier.fillMaxWidth()) {
                formations.forEachIndexed { index, formation ->
                    FormationTimelineEvent(
                        formation = formation,
                        contentDirection = contentDirection,
                    )
                    if (index < formations.lastIndex) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TimelineConnector(dashed = true)
                            Spacer(modifier = Modifier.size(32.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FormationTimelineEvent(
    formation: FormationInfo,
    contentDirection: LayoutDirection,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .semantics {
                contentDescription = "${formation.title}, ${formation.month} ${formation.year}"
            },
        verticalAlignment = Alignment.Top,
    ) {
        TimelineEventNode(year = formation.year)
        CompositionLocalProvider(LocalLayoutDirection provides contentDirection) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
            ) {
                Text(
                    text = formation.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = formation.month,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.50f),
                )
            }
        }
    }
}

data class FormationInfo(
    val title: String = "",
    val month: String = "",
    val year: Int = 0,
)

