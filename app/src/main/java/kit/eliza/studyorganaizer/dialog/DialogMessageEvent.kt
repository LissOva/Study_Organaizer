package com.example.organizerstudy.dialog

sealed class DialogMessageEvent {
    object OnCancel: DialogMessageEvent()
    object OnConfirm: DialogMessageEvent()
}