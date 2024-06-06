package kit.eliza.studyorganaizer.dialog.dialogDataMessage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kit.eliza.studyorganaizer.FileName


@Composable
fun DialogDataMessage(
    controller: DialogDataMessageController
) {
    if (controller.openMessage.value) {
        if(controller.modeData.value == ModeData.GET) {
            AlertDialog(
                onDismissRequest = {
                    controller.onDataMessageEvent(DialogDataMessageEvent.OnCancel)
                },
                title = null,
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Данные ваших записей будут сохранены на устройстве.\nПуть файла:\nDownload/${FileName.FOLDER}/${FileName.FILE}\nЕсли файл уже существует, он перезапишется.\nСохранить данные?",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        controller.onDataMessageEvent(DialogDataMessageEvent.OnConfirm)
                    }) {
                        Text(
                            text = "Сохранить",
                            //color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        controller.onDataMessageEvent(DialogDataMessageEvent.OnCancel)
                    }) {
                        Text(
                            text = "Отмена"
                        )
                    }
                }
            )
        }

        if(controller.modeData.value == ModeData.PUT) {
            AlertDialog(
                onDismissRequest = {
                    controller.onDataMessageEvent(DialogDataMessageEvent.OnCancel)
                },
                title = null,
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Разместите ваш файл ${FileName.FILE} в папке:\nDownload/${FileName.FOLDER}\nВаши данные будут перезаписаны, текущие записи будут потеряны.\nЗагрузить данные?",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        controller.onDataMessageEvent(DialogDataMessageEvent.OnConfirm)
                    }) {
                        Text(
                            text = "Загрузить",
                            //color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        controller.onDataMessageEvent(DialogDataMessageEvent.OnCancel)
                    }) {
                        Text(
                            text = "Отмена"
                        )
                    }
                }
            )
        }
    }
}