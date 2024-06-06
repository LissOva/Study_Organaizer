package kit.eliza.studyorganaizer.dialog.dialogMessage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun DialogMessage(
    controller: DialogMessageController
) {
    if (controller.openMessage.value) {
        AlertDialog(
            onDismissRequest = {
                controller.onMessageEvent(DialogMessageEvent.OnCancel)
            },
            title = null,
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Вы действительно хотите удалить выбранный элемент?",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    controller.onMessageEvent(DialogMessageEvent.OnConfirm)
                }) {
                    Text(
                        text = "Удалить",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    controller.onMessageEvent(DialogMessageEvent.OnCancel)
                }) {
                    Text(
                        text = "Отмена"
                    )
                }
            }
        )
    }
}