package kit.eliza.studyorganaizer.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kit.eliza.studyorganaizer.Routes

@Composable
fun DialogUI(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        var title = ""
        var label = ""
        when (dialogController.currentRoute.value) {
            Routes.SUBJECT_SCREEN -> {
                title = "Предмет"
                label = "предмета"
            }

            Routes.SECTION_SCREEN -> {
                title = "Раздел"
                label = "раздела"
            }
            Routes.TODO_SCREEN -> {
                title = "Задача"
                label = "задачи"
            }
        }
        if (dialogController.showSort.value) {
            title = "Сортировка"
        }
        AlertDialog(
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
            title = null,
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (dialogController.showEditableText.value)
                        TextField(
                            value = dialogController.editableText.value,

                            onValueChange = { text ->
                                dialogController.onDialogEvent(DialogEvent.OnTextChange(text))
                            },
                            label = {

                                Text(text = "Название ".plus(label))
                            }
                        )
                    if (dialogController.showSort.value) {

                        Surface(
                            onClick = { dialogController.onDialogEvent(DialogEvent.OnSortRevers) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "Сначала новые",
                                    style = MaterialTheme.typography.titleLarge,
                                )

                            }
                        }
                        Surface(
                            onClick = { dialogController.onDialogEvent(DialogEvent.OnSort) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "Сначала старые",
                                    style = MaterialTheme.typography.titleLarge,
                                )

                            }
                        }
                        Surface(
                            onClick = { dialogController.onDialogEvent(DialogEvent.OnSortAlphabet) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "А-Я",
                                    style = MaterialTheme.typography.titleLarge,
                                )

                            }
                        }
                        Surface(
                            onClick = { dialogController.onDialogEvent(DialogEvent.OnSortAlphabetRevers) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = "Я-А",
                                    style = MaterialTheme.typography.titleLarge,
                                )

                            }
                        }
                    }
                }
            },
            confirmButton = {
                if (dialogController.showEditableText.value) {
                    Row {
                        if (dialogController.deleteButton.value) {

                            TextButton(onClick = {
                                dialogController.onDialogEvent(DialogEvent.OnDelete)
                            }) {
                                Text(
                                    text = "Удалить",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                        }
                        TextButton(onClick = {
                            dialogController.onDialogEvent(DialogEvent.OnConfirm)
                        }) {
                            Text(text = "Готово")
                        }
                    }
                }
            }
        )
    }
}