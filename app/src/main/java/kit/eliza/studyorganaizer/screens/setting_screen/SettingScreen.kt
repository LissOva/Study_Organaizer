package kit.eliza.studyorganaizer.screens.setting_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.SettingEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController,
    vm: SettingViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = 6.dp) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    title = {
                        Text(stringResource(id = R.string.BarItemSettings))

                    }
                )
            }
        },
    ) { innerPadding ->
        vm.onEvent(SettingEvent.GetData)
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(modifier = Modifier.padding(innerPadding))
            Text(text = "Данные")
            Button(onClick = {
                vm.onEvent(SettingEvent.DoJsonFile)
            }) {
                Text(text = "Скачать")
            }
            Button(onClick = {
                vm.onEvent(SettingEvent.UseJsonFile)
            }) {
                Text(text = "Выкачать")
            }
        }
    }
}

