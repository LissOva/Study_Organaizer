package kit.eliza.studyorganaizer.screens.setting_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kit.eliza.studyorganaizer.FileName
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.dialog.dialogDataMessage.DialogDataMessage
import kit.eliza.studyorganaizer.screens.SettingEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    vm: SettingViewModel = hiltViewModel()
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    vm.context = context
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
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Spacer(modifier = Modifier.padding(innerPadding))
            Row(Modifier.padding(start = 12.dp)) {
                Icon(
                    imageVector = Icons.Default.FileUpload,
                    contentDescription = "file",
                    Modifier.padding(top = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier
                        .clickable(onClick = {
                            vm.onEvent(SettingEvent.OnDoJsonFile)
                        })
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp)
                ) {
                    Text(
                        text = "Экспорт данных ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "В Download/${FileName.FOLDER}/${FileName.FILE} ",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp), color = MaterialTheme.colorScheme.secondaryContainer
            ) {}
            Row(Modifier.padding(start = 12.dp)) {
                Icon(
                    imageVector = Icons.Default.FileDownload,
                    contentDescription = "file",
                    Modifier.padding(top = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier
                        .clickable(onClick = {
                            vm.onEvent(SettingEvent.OnGetData)
                        })
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp)
                ) {
                    Text(text = "Импорт данных ", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Из Download/${FileName.FOLDER}/${FileName.FILE} ",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp), color = MaterialTheme.colorScheme.secondaryContainer
            ) {}
            Text(
                text = "Developed by Kolygina Elizaveta",
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 510.dp)
                    .fillMaxWidth()
                    .clickable {
                        uriHandler.openUri("https://vk.com/lizikssova")
                    }
                ,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Image by pikisuperstar on Freepik",
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().clickable {
                    uriHandler.openUri("https://www.freepik.com/free-vector/study-time-stickers-collection_13561277.htm")
                },
                color = MaterialTheme.colorScheme.secondary
            )

        }
    }
    DialogDataMessage(controller = vm)
}

