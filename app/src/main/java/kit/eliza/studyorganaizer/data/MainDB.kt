package kit.eliza.studyorganaizer.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import kit.eliza.studyorganaizer.data.event_note.EventNote
import kit.eliza.studyorganaizer.data.event_note.EventNoteDao
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.formula_note.FormulaNoteDao
import kit.eliza.studyorganaizer.data.note.Note
import kit.eliza.studyorganaizer.data.note.NoteDao
import kit.eliza.studyorganaizer.data.quote_note.QuoteNote
import kit.eliza.studyorganaizer.data.quote_note.QuoteNoteDao
import kit.eliza.studyorganaizer.data.section.Section
import kit.eliza.studyorganaizer.data.section.SectionDao
import kit.eliza.studyorganaizer.data.subject.Subject
import kit.eliza.studyorganaizer.data.subject.SubjectDao
import kit.eliza.studyorganaizer.data.text_note.TextNote
import kit.eliza.studyorganaizer.data.text_note.TextNoteDao
import kit.eliza.studyorganaizer.data.type_note.TypeNote
import kit.eliza.studyorganaizer.data.type_note.TypeNoteDao

@Database(
    entities = [
        Subject::class,
        Section::class,
        Note::class,
        TypeNote::class,
        TextNote::class,
        EventNote::class,
        QuoteNote::class,
        FormulaNote::class],
    version = 1_5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1_4, to = 1_5)
    ]
)


abstract class MainDB : RoomDatabase(){
    abstract val subjectDao: SubjectDao
    abstract val sectionDao: SectionDao
    abstract val noteDao: NoteDao
    abstract val typeNoteDao: TypeNoteDao
    abstract val textNoteDao: TextNoteDao
    abstract val eventNoteDao: EventNoteDao
    abstract val quoteNoteDao: QuoteNoteDao
    abstract val formulaNoteDao: FormulaNoteDao
}



