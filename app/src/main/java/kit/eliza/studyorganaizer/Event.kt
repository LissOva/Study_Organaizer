package kit.eliza.studyorganaizer

import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.section.Section
import kit.eliza.studyorganaizer.data.subject.Subject
import kit.eliza.studyorganaizer.data.text_note.TextNote
import kit.eliza.studyorganaizer.data.to_do.ToDo

sealed class SubjectEvent {
    data object OnSubjectEventInsert : SubjectEvent()
    data object OnSubjectEventUpdate : SubjectEvent()
    data object OnSubjectEventDelete : SubjectEvent()

    data object OnSubjectEventSort : SubjectEvent()
    data object AddSubjectEvent : SubjectEvent()
    data class OnLongPressSubjectEvent(var subject: Subject) : SubjectEvent()
    data class OnSubjectEventSearch(var searchName: String) : SubjectEvent()

    data object SubjectSortEvent : SubjectEvent()
    data object SubjectSortReversEvent : SubjectEvent()
    data object SubjectSortAlphabetEvent : SubjectEvent()
    data object SubjectSortAlphabetReversEvent : SubjectEvent()
}

sealed class SectionEvent {
    data object OnSectionEventInsert : SectionEvent()
    data object OnSectionEventUpdate : SectionEvent()
    data object OnSectionEventDelete : SectionEvent()

    data class GetSections(val subjectId: Int) : SectionEvent()
    data class GetSubject(val subjectId: Int) : SectionEvent()
    data class GetNotes(val sectionId: Int) : SectionEvent()
    data class AddNote(val sectionId: Int, val noteName: String) : SectionEvent()
    data class OnSectionEventNameChange(val sectionName: String) : SectionEvent()
    data class OnLongPressSectionEvent(var section: Section) : SectionEvent()
}

sealed class NoteEvent {
    data class OnNoteEventInsert(var note: Note) : NoteEvent()
    data class OnNoteEventUpdate(var note: Note) : NoteEvent()
    data object OnNoteEventDelete : NoteEvent()
    data object OnNoteEventDeleteEvent : NoteEvent()
    data object OnNoteEventDeleteFormula : NoteEvent()
    data object OnNoteEventDeleteQuote : NoteEvent()
    data object OnNoteEventDeleteText : NoteEvent()

    data class GetNote(val noteId: Int) : NoteEvent()
    data class SaveNote(val type: Int) : NoteEvent()

    data class OnNoteEventInsertText(val textNote: TextNote) : NoteEvent()
    data class OnNoteEventInsertEvent(val eventNote: EventNote) : NoteEvent()
    data class OnNoteEventInsertQuote(val quoteNote: QuoteNote) : NoteEvent()
    data class OnNoteEventInsertFormula(val eventNote: FormulaNote) : NoteEvent()

}

sealed class AllNoteEvent {
    data class OnAllNoteEventSearch(var searchName: String) : AllNoteEvent()
    data object GetAllNote : AllNoteEvent()
}

sealed class ToDoEvent {
    data object GetToDo : ToDoEvent()
    data class OnToDoEventInsert(val toDo: ToDo) : ToDoEvent()
    data class OnToDoEventUpdate(val toDo: ToDo) : ToDoEvent()
    data object OnToDoEventDelete : ToDoEvent()

    data class OnToDoEventNameChange(val toDoName: String) : ToDoEvent()
    data class OnPressToDoEvent(var toDo: ToDo) : ToDoEvent()

}