package kit.eliza.studyorganaizer.dialog.dialogDataMessage

sealed class DialogDataMessageEvent {
    object OnCancel: DialogDataMessageEvent()
    object OnConfirm: DialogDataMessageEvent()
}