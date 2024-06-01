package kit.eliza.studyorganaizer.screens.section_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.organizerstudy.dialog.DialogMessageEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.SectionEvent
import kit.eliza.studyorganaizer.Routes
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kit.eliza.studyorganaizer.data.section.Section
import kit.eliza.studyorganaizer.data.section.SectionRepository
import kit.eliza.studyorganaizer.data.subject.SubjectRepository
import kit.eliza.studyorganaizer.dialog.DialogController
import kit.eliza.studyorganaizer.dialog.DialogEvent
import kit.eliza.studyorganaizer.dialog.DialogMessageController
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(
    private val subjectRep: SubjectRepository,
    private val sectionRep: SectionRepository,
    private val noteRep: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController, DialogMessageController {

    var sectionName = mutableStateOf("")
        private set


    var section: Section? = null

    var listSection: Flow<List<Section>>? = null
    var subjectId: Int = -1
    var subjectName: String = ""

    var list: Flow<List<Note>>? = null

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

    fun onEvent(event: SectionEvent) {
        when (event) {
            is SectionEvent.GetSubject -> {
                viewModelScope.launch {
                    subjectRep.getSubjectById(event.subjectId).collect { it ->
                        subjectName = it.name
                    }
                }
            }
            is SectionEvent.GetSections -> {
                listSection = sectionRep.getAllSectionById(subjectId)
                list = noteRep.getAllNoteById(event.subjectId)
            }
            is SectionEvent.GetNotes -> {
                list = noteRep.getAllNoteById(event.sectionId)
            }
            is SectionEvent.OnSectionEventNameChange -> {
                sectionName.value = event.sectionName
            }
            is SectionEvent.OnLongPressSectionEvent ->{
                showSort.value = false
                section = event.section
                showEditableText.value = true
                openDialog.value = true
                editableText.value = section!!.name
                deleteButton.value = true
            }
            is SectionEvent.OnSectionEventInsert -> {
                viewModelScope.launch {
                    if (subjectId == -1) return@launch
                    if (section != null) {
                        if(section!!.name.isEmpty()){
                            //sendUiEvent(UiEvent.ShowSnackBar("Name must not be empty!"))
                            return@launch
                        }
                    } else {
                        if(sectionName.value.isEmpty()){
                            //sendUiEvent(UiEvent.ShowSnackBar("Name must not be empty!"))
                            return@launch
                        }
                    }
                    sectionRep.insertSection(
                        Section(
                            section?.id,
                            section?.idSubject ?: subjectId,
                            section?.name ?: sectionName.value
                        )
                    )
                    sectionName.value = ""
                    section = null
                }
            }
            is SectionEvent.OnSectionEventUpdate ->{
                val upd = Section(
                    section!!.id,
                    section!!.idSubject,
                    editableText.value
                )
                viewModelScope.launch {
                    sectionRep.updateSection(
                        upd
                    )
                }
            }
            is SectionEvent.OnSectionEventDelete ->{
                viewModelScope.launch {
                    sectionRep.deleteSection(section!!)
                }
                section = null
            }
            is SectionEvent.AddNote ->{
                if(event.noteName != "") {
                    viewModelScope.launch {
                        val note = Note(null, event.sectionId, event.noteName, 1)
                        noteRep.insertNote(note)
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
                    if(!deleteButton.value) {
                        onEvent(SectionEvent.OnSectionEventInsert)
                    } else {
                        onEvent(SectionEvent.OnSectionEventUpdate)
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
                onEvent(SectionEvent.OnSectionEventDelete)
                openMessage.value = false
                openDialog.value = false
                editableText.value = ""
            }
        }
    }
}