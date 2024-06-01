package kit.eliza.studyorganaizer.screens.note_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.organizerstudy.data.formula.FormulaNoteRepository
import com.example.organizerstudy.dialog.DialogMessageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.ModeNote
import kit.eliza.studyorganaizer.NoteBlock
import kit.eliza.studyorganaizer.NoteEvent
import kit.eliza.studyorganaizer.SectionEvent
import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.event_note.EventNoteRepository
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.quote_note.QuoteNoteRepository
import kit.eliza.studyorganaizer.data.text_note.TextNote
import kit.eliza.studyorganaizer.data.text_note.TextNoteRepository
import kit.eliza.studyorganaizer.data.type_note.TypeNote
import kit.eliza.studyorganaizer.data.type_note.TypeNoteRepository
import kit.eliza.studyorganaizer.dialog.DialogMessageController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRep: NoteRepository,
    private val typeRep: TypeNoteRepository,
    private val textRep: TextNoteRepository,
    private val quoteRep: QuoteNoteRepository,
    private val formulaRep: FormulaNoteRepository,
    private val eventRep: EventNoteRepository,
) : ViewModel(), DialogMessageController {

    lateinit var navController: NavHostController
    var noteId: Int = -1
    var mode = ModeNote.READ

    var note: Note? = null
    private var _textNote = MutableStateFlow<TextNote?>(null)
    val textNote: StateFlow<TextNote?> = _textNote.asStateFlow()


    private val _listQuote = MutableStateFlow(emptyList<QuoteNote>())
    val listQuote: StateFlow<List<QuoteNote>> = _listQuote
    private val _listFormula = MutableStateFlow(emptyList<FormulaNote>())
    val listFormula: StateFlow<List<FormulaNote>> = _listFormula
    private val _listEvent = MutableStateFlow(emptyList<EventNote>())
    val listEvent: StateFlow<List<EventNote>> = _listEvent

    var listType: Flow<List<TypeNote>>? = null

    var selectedNoteBlock = ""

    var selectedNote:Note? = null
    var selectedText:TextNote? = null
    var selectedEvent:EventNote? = null
    var selectedFormula:FormulaNote? = null
    var selectedQuote:QuoteNote? = null



    init {
        listType = typeRep.getAllType()
    }

    override var openMessage = mutableStateOf(false)
        private set

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.GetNote -> {
                viewModelScope.launch {
                    note = withContext(Dispatchers.IO) {
                        noteRep.getNoteById(event.noteId).first()
                    }
                    _textNote.value = withContext(Dispatchers.IO) {
                        textRep.getTextById(event.noteId).first()
                    }
                    _listQuote.value = withContext(Dispatchers.IO) {
                        quoteRep.getAllQuoteNoteById(event.noteId).first()
                    }
                    _listFormula.value = withContext(Dispatchers.IO) {
                        formulaRep.getAllFormulaNoteById(event.noteId).first()
                    }
                    _listEvent.value = withContext(Dispatchers.IO) {
                        eventRep.getAllEventNoteById(event.noteId).first()
                    }
                }
            }
            is NoteEvent.SaveNote -> {
                when(event.type){
                    2 ->{
                        viewModelScope.launch {
                            formulaRep.deleteAllFormulaNoteById(noteId)
                        }
                    }
                    3 ->{
                        viewModelScope.launch {
                            eventRep.deleteAllEventNoteById(noteId)
                        }
                        viewModelScope.launch {
                            quoteRep.deleteAllQuotNoteById(noteId)
                        }
                    }
                    4 ->{
                        viewModelScope.launch {
                            eventRep.deleteAllEventNoteById(noteId)
                        }
                        viewModelScope.launch {
                            formulaRep.deleteAllFormulaNoteById(noteId)
                        }
                        viewModelScope.launch {
                            quoteRep.deleteAllQuotNoteById(noteId)
                        }
                    }
                }
                note?.idType = event.type
                onEvent(NoteEvent.OnNoteEventUpdate(note!!))
                onEvent(NoteEvent.GetNote(noteId))

            }
            is NoteEvent.OnNoteEventDelete -> {
                viewModelScope.launch {
                    noteRep.deleteNote(note!!)
                }
            }
            is NoteEvent.OnNoteEventDeleteEvent -> {
                viewModelScope.launch {
                    eventRep.deleteEventNote(selectedEvent!!)
                    _listEvent.value = emptyList()
                    onEvent(NoteEvent.GetNote(noteId))
                    selectedEvent = null
                }
            }

            is NoteEvent.OnNoteEventDeleteFormula -> {
                viewModelScope.launch {
                    formulaRep.deleteFormulaNote(selectedFormula!!)
                    _listFormula.value = emptyList()
                    onEvent(NoteEvent.GetNote(noteId))
                    selectedFormula = null
                }
            }

            is NoteEvent.OnNoteEventDeleteQuote -> {
                viewModelScope.launch {
                    quoteRep.deleteQuoteNote(selectedQuote!!)
                    _listQuote.value = emptyList()
                    onEvent(NoteEvent.GetNote(noteId))
                    selectedEvent = null
                }
            }

            is NoteEvent.OnNoteEventDeleteText -> {
                viewModelScope.launch {
                textRep.deleteText(textNote.value!!)
                    onEvent(NoteEvent.GetNote(noteId))
                }
            }

            is NoteEvent.OnNoteEventInsert -> {
                viewModelScope.launch {
                    noteRep.insertNote(event.note)
                }
            }

            is NoteEvent.OnNoteEventInsertText -> {
                viewModelScope.launch {
                    textRep.inserTextNote(event.textNote)
                }
            }
            is NoteEvent.OnNoteEventInsertEvent -> {
                viewModelScope.launch {
                    eventRep.insertEventNote(event.eventNote)
                    onEvent(NoteEvent.GetNote(noteId))
                }
            }
            is NoteEvent.OnNoteEventInsertQuote -> {
                viewModelScope.launch {
                    quoteRep.insertQuoteNote(event.quoteNote)
                    onEvent(NoteEvent.GetNote(noteId))
                }
            }
            is NoteEvent.OnNoteEventInsertFormula -> {
                viewModelScope.launch {
                    formulaRep.insertFormulaNote(event.eventNote)
                    onEvent(NoteEvent.GetNote(noteId))
                }
            }
            is NoteEvent.OnNoteEventUpdate ->{
                viewModelScope.launch {
                    noteRep.updateNote(event.note)
                    onEvent(NoteEvent.GetNote(noteId))
                }
            }

            else -> {

            }
        }
    }

    fun EventList(list: List<EventNote>, flag: Boolean) {
        list.forEach {
            viewModelScope.launch {
                if (flag) {
                    eventRep.insertEventNote(it)
                } else {
                    eventRep.deleteEventNote(it)
                }
            }
        }
    }

    fun QuoteList(list: List<QuoteNote>, flag: Boolean) {
        list.forEach {
            viewModelScope.launch {
                if (flag) {
                    quoteRep.insertQuoteNote(it)
                } else {
                    quoteRep.deleteQuoteNote(it)
                }
            }
        }
    }

    fun FormulaList(list: List<FormulaNote>, flag: Boolean) {
        list.forEach {
            viewModelScope.launch {
                if (flag) {
                    formulaRep.insertFormulaNote(it)
                } else {
                    formulaRep.deleteFormulaNote(it)
                }
            }
        }
    }


    override fun onMessageEvent(event: DialogMessageEvent) {
        when (event) {
            DialogMessageEvent.OnCancel -> {
                openMessage.value = false
            }

            DialogMessageEvent.OnConfirm -> {
                when (selectedNoteBlock) {
                    NoteBlock.NOTE -> {
                        onEvent(NoteEvent.OnNoteEventDelete)
                        navController.navigateUp()
                    }
                    NoteBlock.TEXT -> {
                        onEvent(NoteEvent.OnNoteEventDeleteText)
                    }
                    NoteBlock.EVENT -> {
                        onEvent(NoteEvent.OnNoteEventDeleteEvent)
                    }
                    NoteBlock.QUOTE -> {
                        onEvent(NoteEvent.OnNoteEventDeleteQuote)
                    }
                    NoteBlock.FORMULA -> {
                        onEvent(NoteEvent.OnNoteEventDeleteFormula)
                    }
                }
                openMessage.value = false
            }
        }
    }

}