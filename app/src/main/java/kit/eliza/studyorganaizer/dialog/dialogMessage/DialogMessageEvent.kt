package kit.eliza.studyorganaizer.dialog.dialogMessage

sealed class DialogMessageEvent {
    object OnCancel: DialogMessageEvent()
    object OnConfirm: DialogMessageEvent()
}