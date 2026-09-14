package ca.russellmania.mypage

import androidx.compose.ui.test.SemanticsNodeInteraction
import com.dropbox.differ.SimpleImageComparator
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.provideRoborazziContext

@OptIn(ExperimentalRoborazziApi::class)
internal fun SemanticsNodeInteraction.captureExactRoboImage() {
    val options = provideRoborazziContext().options
    captureRoboImage(
        roborazziOptions = options.copy(
            compareOptions = options.compareOptions.copy(
                imageComparator = SimpleImageComparator(
                    maxDistance = 0f,
                    hShift = 0,
                    vShift = 0,
                )
            )
        )
    )
}
