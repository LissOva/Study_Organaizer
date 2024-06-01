package kit.eliza.studyorganaizer.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val deleteButton: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>
    val showSort: MutableState<Boolean>
    val currentRoute: MutableState<String>
    fun onDialogEvent(event: DialogEvent)
}