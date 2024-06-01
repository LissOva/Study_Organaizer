package kit.eliza.studyorganaizer.dialog

import androidx.compose.runtime.MutableState
import com.example.organizerstudy.dialog.DialogMessageEvent

interface DialogMessageController {

    val openMessage: MutableState<Boolean>
    fun onMessageEvent(event: DialogMessageEvent)
}