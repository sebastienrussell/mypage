package ca.russellmania.mypage

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ca.russellmania.mypage.ui.home.EducationSection
import ca.russellmania.mypage.ui.home.FormationsSection
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
                    val intent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
                    startActivity(intent)
                },
                onClickEmailAddress = { emailAddress ->
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:$emailAddress".toUri()
                    }
                    startActivity(intent)
                },
                onClickAddress = { address ->
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = "geo:0,0?q=${address.substringBeforeLast(',')}".toUri()
                    }
                    startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape,cutout=punch_hole",
    group = "landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickPhoneNumber: (phoneNumber: String) -> Unit = {},
    onClickEmailAddress: (emailAddress: String) -> Unit = {},
    onClickAddress: (address: String) -> Unit = {},
) {
    MyPageTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Sebastien Russell", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    },
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    actions = {
                        IconButton(onClick = { onClickAddress("Mercier, QC, J6R 0G2") }) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = { onClickEmailAddress("russell.sebas@gmail.com") }) {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = { onClickPhoneNumber("(438) 403-9294") }) {
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .navigationBarsPadding()
                    .consumeWindowInsets(innerPadding),
                contentPadding = innerPadding,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val spacersModifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
//                item {
//                    InfoSection(
//                        onPhoneNumberClick = onClickPhoneNumber,
//                        onEmailAddressClick = onClickEmailAddress,
//                        onAddressClick = onClickAddress
//                    )
//                }
                item { Spacer(modifier = spacersModifier) }
                item { QualificationsSection() }
                item { Spacer(modifier = spacersModifier) }
                item { WorkingExperienceSection() }
                item { Spacer(modifier = spacersModifier) }
                item { EducationSection() }
                item { Spacer(modifier = spacersModifier) }
                item { FormationsSection() }
            }
        }
    }
}
