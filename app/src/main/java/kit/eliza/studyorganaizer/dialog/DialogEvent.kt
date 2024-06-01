package kit.eliza.studyorganaizer.dialog

sealed class DialogEvent {
    data class OnTextChange(val text: String): DialogEvent()
    object OnCancel: DialogEvent()
    object OnConfirm: DialogEvent()
    object OnDelete: DialogEvent()
    object OnSort: DialogEvent()
    object OnSortRevers: DialogEvent()
    object OnSortAlphabet: DialogEvent()
    object OnSortAlphabetRevers: DialogEvent()
}