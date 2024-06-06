package kit.eliza.studyorganaizer.dialog.dialogMessage

import androidx.compose.runtime.MutableState

interface DialogMessageController {

    val openMessage: MutableState<Boolean>
    fun onMessageEvent(event: DialogMessageEvent)
}