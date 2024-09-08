package ca.russellmania.mypage

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.russellmania.mypage.ui.home.EducationSection
import ca.russellmania.mypage.ui.home.FormationsSection
import ca.russellmania.mypage.ui.home.InfoSection
import ca.russellmania.mypage.ui.home.QualificationsSection
import ca.russellmania.mypage.ui.home.WorkingExperienceSection
import ca.russellmania.mypage.ui.theme.MyPageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
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
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
fun HomeScreen(modifier: Modifier = Modifier) {
    MyPageTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoSection()
                QualificationsSection()
                WorkingExperienceSection()
                EducationSection()
                FormationsSection()
            }
        }
    }
}