package kit.eliza.studyorganaizer.dialog.dialogDataMessage

import androidx.compose.runtime.MutableState


interface DialogDataMessageController {

    val openMessage: MutableState<Boolean>
    val modeData: MutableState<String>
    fun onDataMessageEvent(event: DialogDataMessageEvent)
}