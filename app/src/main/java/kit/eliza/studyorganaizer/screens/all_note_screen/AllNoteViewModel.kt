package kit.eliza.studyorganaizer.screens.subject_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.screens.AllNoteEvent
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNoteViewModel @Inject constructor(
    private val noteRep: NoteRepository
) : ViewModel() {
   // var listNotes: Flow<List<Note>>? = null

    private var _listNotes = MutableStateFlow(emptyList<Note>())
    val listNotes: StateFlow<List<Note>> = _listNotes

    fun onEvent(event: AllNoteEvent){
        when(event){
            is AllNoteEvent.GetAllNote ->{
                viewModelScope.launch {
                noteRep.getAllNote().collect {it ->
                    _listNotes.value = it
                }
                }
            }
            is AllNoteEvent.OnAllNoteEventSearch ->{
                viewModelScope.launch {
                    val search = "%" + event.searchName + "%"
                    noteRep.getAllNoteByName(search).collect {it ->
                        _listNotes.value = it
                    }
                }
            }
        }
    }
}