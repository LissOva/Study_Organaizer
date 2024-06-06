package kit.eliza.studyorganaizer.screens.subject_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.screens.SubjectEvent
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.data.subject.Subject
import kit.eliza.studyorganaizer.data.subject.SubjectRepository
import kit.eliza.studyorganaizer.dialog.dialog.DialogController
import kit.eliza.studyorganaizer.dialog.dialog.DialogEvent
import kit.eliza.studyorganaizer.dialog.dialogMessage.DialogMessageController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRep: SubjectRepository
): ViewModel(), DialogController, DialogMessageController {

    private val _listSubject = MutableStateFlow(emptyList<Subject>())
    val listSubject: StateFlow<List<Subject>> = _listSubject

    private var currentSort: SubjectEvent = SubjectEvent.SubjectSortReversEvent

    init {
        viewModelScope.launch {
            onEvent(currentSort)
        }
    }

    private var subject: Subject? = null

    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var deleteButton = mutableStateOf(false)
        private set
    override var showSort = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set
    override var currentRoute = mutableStateOf(Routes.SUBJECT_SCREEN)
        private set

    override var openMessage =  mutableStateOf(false)
        private set

    fun onEvent(event: SubjectEvent){
        when(event){
            is SubjectEvent.AddSubjectEvent ->{
                subject = null
                openDialog.value = true
                showEditableText.value = true
                showSort.value = false
                deleteButton.value = false
            }
            is SubjectEvent.OnSubjectEventSort ->{
                openDialog.value = true
                deleteButton.value = false
                showEditableText.value = false
                showSort.value = true
                currentSort = event
            }
            is SubjectEvent.OnLongPressSubjectEvent ->{
                showSort.value = false
                subject = event.subject
                showEditableText.value = true
                openDialog.value = true
                editableText.value = subject!!.name
                deleteButton.value = true
            }
            is SubjectEvent.OnSubjectEventInsert ->{
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    subjectRep.insertSubject(
                        Subject(
                            subject?.id,
                            editableText.value,
                        )
                    )
                }
            }
            is SubjectEvent.OnSubjectEventUpdate -> {
                viewModelScope.launch {
                    subjectRep.updateSubject(
                        Subject(
                            subject?.id,
                            editableText.value,
                        )
                    )
                }
            }
            is SubjectEvent.OnSubjectEventDelete ->{
                viewModelScope.launch {
                    subjectRep.deleteSubject(subject!!)
                }
            }

            is SubjectEvent.SubjectSortEvent -> {
                viewModelScope.launch {
                    subjectRep.getAllSubject().collect {
                        _listSubject.value = it
                    }
                }
                currentSort = event
            }
            is SubjectEvent.SubjectSortAlphabetEvent -> {
                viewModelScope.launch {
                    subjectRep.getAllSubjectAlphabet().collect {
                        _listSubject.value = it
                    }
                }
                currentSort = event
            }
            is SubjectEvent.SubjectSortAlphabetReversEvent -> {
                viewModelScope.launch {
                    subjectRep.getAllSubjectAlphabetRevers().collect {
                        _listSubject.value = it
                    }
                }
                currentSort = event
            }
            is SubjectEvent.SubjectSortReversEvent -> {
                viewModelScope.launch {
                    subjectRep.getAllSubjectRevers().collect {
                        _listSubject.value = it
                    }
                }
                currentSort = event
            }
            is SubjectEvent.OnSubjectEventSearch ->{
                if(event.searchName.isNotEmpty()) {
                    viewModelScope.launch {
                        val search = "%" + event.searchName + "%"
                        subjectRep.getAllSubjectByName(search).collect {
                            _listSubject.value = it
                        }
                    }
                } else {
                    onEvent(currentSort)
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
                    if(!deleteButton.value) {
                        onEvent(SubjectEvent.OnSubjectEventInsert)
                    } else {
                        onEvent(SubjectEvent.OnSubjectEventUpdate)
                    }
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
            DialogEvent.OnSort -> {
                onEvent(SubjectEvent.SubjectSortEvent)
                openDialog.value = false
            }
            DialogEvent.OnSortAlphabet -> {
                onEvent(SubjectEvent.SubjectSortAlphabetEvent)

                openDialog.value = false
            }
            DialogEvent.OnSortAlphabetRevers -> {
                onEvent(SubjectEvent.SubjectSortAlphabetReversEvent)

                openDialog.value = false
            }
            DialogEvent.OnSortRevers -> {
                onEvent(SubjectEvent.SubjectSortReversEvent)
                openDialog.value = false
            }
        }
    }

    override fun onMessageEvent(event: DialogMessageEvent) {
        when(event) {
            DialogMessageEvent.OnCancel -> {
                openMessage.value = false
            }
            DialogMessageEvent.OnConfirm -> {
                onEvent(SubjectEvent.OnSubjectEventDelete)
                openMessage.value = false
                openDialog.value = false
                editableText.value = ""
            }
        }
    }
}
