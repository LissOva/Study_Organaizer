package kit.eliza.studyorganaizer.screens.setting_screen

import android.os.Environment
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.organizerstudy.data.formula.FormulaNoteRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kit.eliza.studyorganaizer.FileName
import kit.eliza.studyorganaizer.R
import kit.eliza.studyorganaizer.SettingEvent
import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.event_note.EventNoteRepository
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.quote_note.QuoteNoteRepository
import kit.eliza.studyorganaizer.data.section.Section
import kit.eliza.studyorganaizer.data.section.SectionRepository
import kit.eliza.studyorganaizer.data.subject.Subject
import kit.eliza.studyorganaizer.data.subject.SubjectRepository
import kit.eliza.studyorganaizer.data.text_note.TextNote
import kit.eliza.studyorganaizer.data.text_note.TextNoteRepository
import kit.eliza.studyorganaizer.data.to_do.ToDo
import kit.eliza.studyorganaizer.data.to_do.ToDoRepository
import kit.eliza.studyorganaizer.data.type_note.TypeNote
import kit.eliza.studyorganaizer.data.type_note.TypeNoteRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val subjectRep: SubjectRepository,
    private val sectionRep: SectionRepository,
    private val noteRep: NoteRepository,
    private val typeRep: TypeNoteRepository,
    private val textRep: TextNoteRepository,
    private val quoteRep: QuoteNoteRepository,
    private val formulaRep: FormulaNoteRepository,
    private val eventRep: EventNoteRepository,
    private val toDoRep: ToDoRepository
): ViewModel() {

    private lateinit var subjects: List<Subject>
    private lateinit var sections: List<Section>
    private lateinit var notes: List<Note>
    private lateinit var events: List<EventNote>
    private lateinit var formulas: List<FormulaNote>
    private lateinit var quotes: List<QuoteNote>
    private lateinit var texts: List<TextNote>
    private lateinit var todos: List<ToDo>

    private val gson = Gson()
    private val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FileName.FOLDER)

    fun onEvent (event: SettingEvent){
        when(event){
            SettingEvent.DoJsonFile -> {
                val allData = mapOf(
                    "subjects" to subjects,
                    "sections" to sections,
                    "notes" to notes,
                    "textNotes" to texts,
                    "quoteNotes" to quotes,
                    "formulaNotes" to formulas,
                    "eventNotes" to events,
                    "toDos" to todos
                )

                val json = gson.toJson(allData)

                try {
                    val file = File(folder, FileName.FILE)
                    val writer = FileWriter(file)
                    writer.append(json)
                    writer.flush()
                    writer.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            SettingEvent.UseJsonFile -> {
                val file = File(folder, FileName.FILE)
                val json = file.readText()
                val subjectJ: Map<String, List<Subject>> = gson.fromJson(json, object : TypeToken<Map<String, List<Subject>>>() {}.type)
                val newSubjects = subjectJ["subjects"]
                val sectionJ: Map<String, List<Section>> = gson.fromJson(json, object : TypeToken<Map<String, List<Section>>>() {}.type)
                val newSections = sectionJ["sections"]
                val noteJ: Map<String, List<Note>> = gson.fromJson(json, object : TypeToken<Map<String, List<Note>>>() {}.type)
                val newNotes = noteJ["notes"]
                val textJ: Map<String, List<TextNote>> = gson.fromJson(json, object : TypeToken<Map<String, List<TextNote>>>() {}.type)
                val newTextNotes = textJ["textNotes"]
                val quoteJ: Map<String, List<QuoteNote>> = gson.fromJson(json, object : TypeToken<Map<String, List<QuoteNote>>>() {}.type)
                val newQuoteNotes = quoteJ["quoteNotes"]
                val formulaJ: Map<String, List<FormulaNote>> = gson.fromJson(json, object : TypeToken<Map<String, List<FormulaNote>>>() {}.type)
                val newFormulaNotes = formulaJ["formulaNotes"]
                val eventJ: Map<String, List<EventNote>> = gson.fromJson(json, object : TypeToken<Map<String, List<EventNote>>>() {}.type)
                val newEventNotes = eventJ["eventNotes"]
                val toDotJ: Map<String, List<ToDo>> = gson.fromJson(json, object : TypeToken<Map<String, List<ToDo>>>() {}.type)
                val newToDos = toDotJ["toDos"]

                    viewModelScope.launch {
                        subjectRep.deleteAllSubject()
                        subjectRep.insertListSubject(newSubjects!!)
                    }

            }
            SettingEvent.GetData -> {
                viewModelScope.launch {
                    runBlocking {
                        subjects = subjectRep.getAllSubject().first()
                        sections = sectionRep.getAllSection().first()
                        notes = noteRep.getAllNote().first()
                        texts = textRep.getAllText().first()
                        quotes = quoteRep.getAllQuotNote().first()
                        formulas = formulaRep.getAllFormula().first()
                        events = eventRep.getAllEventNote().first()
                        todos = toDoRep.getAllToDo().first()
                    }
                }
            }
        }
    }
}



