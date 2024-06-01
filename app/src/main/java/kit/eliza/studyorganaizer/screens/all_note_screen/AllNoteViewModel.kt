package kit.eliza.studyorganaizer.screens.subject_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllNoteViewModel @Inject constructor(
    private val noteRep: NoteRepository
) : ViewModel() {
    var listNotes: Flow<List<Note>>? = null
    fun get(){
    listNotes = noteRep.getAllNote()

    }
}