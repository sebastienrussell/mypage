package ca.russellmania.mypage.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val RailWidth = 56
private const val NodeSize = 52

@Composable
internal fun TimelineRail(
    topYear: Int?,
    bottomYear: Int?,
    modifier: Modifier = Modifier,
    dashed: Boolean = false,
) {
    val lineColor = MaterialTheme.colorScheme.primary
    val nodeSize = timelineNodeSize()
    Box(
        modifier = modifier
            .width(maxOf(RailWidth.dp, nodeSize))
            .fillMaxHeight()
            .drawBehind {
                drawLine(
                    color = lineColor,
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = if (dashed) {
                        PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()))
                    } else {
                        null
                    },
                )
            },
        contentAlignment = Alignment.TopCenter,
    ) {
        if (topYear != null) {
            TimelineBoundaryNode(year = topYear, nodeSize = nodeSize)
        }
        if (bottomYear != null) {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                TimelineBoundaryNode(year = bottomYear, nodeSize = nodeSize)
            }
        }
    }
}

@Composable
internal fun TimelineConnector(
    modifier: Modifier = Modifier,
    dashed: Boolean,
) {
    TimelineRail(
        topYear = null,
        bottomYear = null,
        modifier = modifier.height(32.dp),
        dashed = dashed,
    )
}

@Composable
internal fun TimelineEventNode(
    year: Int,
    modifier: Modifier = Modifier,
) {
    val nodeSize = timelineNodeSize()
    Box(
        modifier = modifier.width(maxOf(RailWidth.dp, nodeSize)),
        contentAlignment = Alignment.TopCenter,
    ) {
        TimelineBoundaryNode(year = year, nodeSize = nodeSize)
    }
}

@Composable
internal fun TimelineBoundaryNode(
    year: Int,
    nodeSize: Dp = timelineNodeSize(),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(nodeSize)
            .semantics { contentDescription = year.toString() },
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier.size(nodeSize),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
internal fun timelineNodeSize(): Dp =
    maxOf(NodeSize.toFloat(), NodeSize * LocalDensity.current.fontScale).dp
