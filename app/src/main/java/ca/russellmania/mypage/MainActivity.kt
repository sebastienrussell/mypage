package ca.russellmania.mypage

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ca.russellmania.mypage.ui.home.EducationSection
import ca.russellmania.mypage.ui.home.FormationsSection
import ca.russellmania.mypage.ui.home.InfoSection
import ca.russellmania.mypage.ui.home.QualificationsSection
import ca.russellmania.mypage.ui.home.WorkingExperienceSection
import ca.russellmania.mypage.ui.theme.MyPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectUnsafeIntentLaunch()
                .build()
        )
        enableEdgeToEdge()
        setContent {
            HomeScreen(
                onClickPhoneNumber = { phoneNumber ->
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                },
                onClickEmailAddress = { emailAddress ->
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$emailAddress")
                    }
                    startActivity(intent)
                },
                onClickAddress = { address ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("geo:0,0?q=${address.substringBeforeLast(',')}")
                    }
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape", group = "landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickPhoneNumber: (phoneNumber: String) -> Unit = {},
    onClickEmailAddress: (emailAddress: String) -> Unit = {},
    onClickAddress: (address: String) -> Unit = {},
) {
    MyPageTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.statusBars
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.consumeWindowInsets(innerPadding),
                contentPadding = innerPadding,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    InfoSection(
                        onClickPhoneNumber = onClickPhoneNumber,
                        onClickEmailAddress = onClickEmailAddress,
                        onClickAddress = onClickAddress
                    )
                    QualificationsSection()
                    WorkingExperienceSection()
                    EducationSection()
                    FormationsSection()
                }
            }
        }
    }
}