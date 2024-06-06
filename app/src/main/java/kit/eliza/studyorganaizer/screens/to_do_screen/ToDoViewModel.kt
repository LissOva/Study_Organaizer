package kit.eliza.studyorganaizer.screens.to_do_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.screens.ToDoEvent
import kit.eliza.studyorganaizer.data.to_do.ToDo
import kit.eliza.studyorganaizer.data.to_do.ToDoRepository
import kit.eliza.studyorganaizer.dialog.dialog.DialogController
import kit.eliza.studyorganaizer.dialog.dialog.DialogEvent
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessageController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoRep: ToDoRepository
): ViewModel(), DialogController, DialogMessageController {

    var toDo: ToDo? = null

    var listFavouriteToDo: Flow<List<ToDo>>? = null
    var listToDo: Flow<List<ToDo>>? = null
    var listCompleteToDo: Flow<List<ToDo>>? = null

    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var deleteButton = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set
    override var showSort = mutableStateOf(true)
        private set
    override var currentRoute = mutableStateOf(Routes.SECTION_SCREEN)
        private set

    override var openMessage =  mutableStateOf(false)
        private set

    fun onEvent(event: ToDoEvent){
        when(event) {
            is ToDoEvent.GetToDo -> {
                listFavouriteToDo = toDoRep.getFavouriteToDo()
                listToDo = toDoRep.getToDo()
                listCompleteToDo = toDoRep.getCompleteToDo()
            }
            is ToDoEvent.OnToDoEventInsert -> {
                if (event.toDo.name != "") {
                    viewModelScope.launch {
                        toDoRep.insertToDo(event.toDo)
                    }
                }
                onEvent(ToDoEvent.GetToDo)
            }
            is ToDoEvent.OnToDoEventNameChange -> TODO()
            is ToDoEvent.OnPressToDoEvent -> {
                showSort.value = false
                toDo = event.toDo
                showEditableText.value = true
                openDialog.value = true
                editableText.value = toDo!!.name
                deleteButton.value = true
                currentRoute.value = Routes.TODO_SCREEN
            }

            is ToDoEvent.OnToDoEventDelete -> {
                viewModelScope.launch {
                    toDoRep.deleteToDo(toDo!!)
                }
            }

            is ToDoEvent.OnToDoEventUpdate -> {
                if (event.toDo.name != "") {
                    viewModelScope.launch {
                        toDoRep.deleteToDo(toDo!!)
                        toDoRep.insertToDo(event.toDo)
                    }
                }
            }
        }
    }


    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value) {
                    var newTodo = toDo!!
                    newTodo.name = editableText.value
                    onEvent(ToDoEvent.OnToDoEventUpdate(toDo!!))
                }
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnDelete -> {
                openMessage.value = true
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }

            DialogEvent.OnSort -> TODO()
            DialogEvent.OnSortAlphabet -> TODO()
            DialogEvent.OnSortAlphabetRevers -> TODO()
            DialogEvent.OnSortRevers -> TODO()
        }
    }

    override fun onMessageEvent(event: DialogMessageEvent) {
        when(event) {
            DialogMessageEvent.OnCancel -> {
                openMessage.value = false
            }
            DialogMessageEvent.OnConfirm -> {
                onEvent(ToDoEvent.OnToDoEventDelete)
                openMessage.value = false
                openDialog.value = false
                editableText.value = ""
            }
        }
    }
}