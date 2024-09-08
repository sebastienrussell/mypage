package ca.russellmania.mypage.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun BulletList(modifier: Modifier = Modifier, list: List<String>) {
    val paragraphStyle =
        ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            list.forEach {
                withStyle(style = paragraphStyle) {
                    append("\u2022\t\t")
                    append(it)
                }
            }
        },
        style = MaterialTheme.typography.bodyMedium
    )
}