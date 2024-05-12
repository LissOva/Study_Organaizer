package kit.eliza.studyorganaizer.di

import android.app.Application
import android.content.ContentValues
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.organizerstudy.data.formula.FormulaNoteNoteRepoImpl
import com.example.organizerstudy.data.formula.FormulaNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kit.eliza.studyorganaizer.data.MainDB
import kit.eliza.studyorganaizer.data.event_note.EventNoteRepoImpl
import kit.eliza.studyorganaizer.data.event_note.EventNoteRepository
import kit.eliza.studyorganaizer.data.note.NoteRepoImpl
import kit.eliza.studyorganaizer.data.note.NoteRepository
import kit.eliza.studyorganaizer.data.quote_note.QuoteNoteNoteRepoImpl
import kit.eliza.studyorganaizer.data.quote_note.QuoteNoteRepository
import kit.eliza.studyorganaizer.data.section.SectionRepoImpl
import kit.eliza.studyorganaizer.data.section.SectionRepository
import kit.eliza.studyorganaizer.data.subject.SubjectRepoImpl
import kit.eliza.studyorganaizer.data.subject.SubjectRepository
import kit.eliza.studyorganaizer.data.text_note.TextNoteRepoImpl
import kit.eliza.studyorganaizer.data.text_note.TextNoteRepository
import kit.eliza.studyorganaizer.data.type_note.TypeNote
import kit.eliza.studyorganaizer.data.type_note.TypeNoteRepoImpl
import kit.eliza.studyorganaizer.data.type_note.TypeNoteRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDB(app: Application): MainDB {
        return Room.databaseBuilder(
            app,
            MainDB::class.java,
            "repository_study_db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val initialType = listOf(
                    TypeNote(null, "Тема"),
                    TypeNote(null, "Биография"),
                    TypeNote(null, "Правило"),
                    TypeNote(null, "Определение")
                )

                initialType.forEach{it->
                    val cv = ContentValues()
                    cv.put("id", it.id)
                    cv.put("name", it.name)
                    db.insert("type", 0, cv)
                }
            }
        }).build()
    }

    @Provides
    @Singleton
    fun provideSubject(db: MainDB): SubjectRepository {
        return SubjectRepoImpl(db.subjectDao)
    }

    @Provides
    @Singleton
    fun provideSection(db: MainDB): SectionRepository {
        return SectionRepoImpl(db.sectionDao)
    }

    @Provides
    @Singleton
    fun provideNote(db: MainDB): NoteRepository{
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideTypeNote(db: MainDB): TypeNoteRepository {
        return TypeNoteRepoImpl(db.typeNoteDao)
    }

    @Provides
    @Singleton
    fun provideEvent(db: MainDB): EventNoteRepository {
        return EventNoteRepoImpl(db.eventNoteDao)
    }

    @Provides
    @Singleton
    fun provideFormula(db: MainDB): FormulaNoteRepository{
        return FormulaNoteNoteRepoImpl(db.formulaNoteDao)
    }

    @Provides
    @Singleton
    fun provideQuote(db: MainDB): QuoteNoteRepository{
        return QuoteNoteNoteRepoImpl(db.quoteNoteDao)
    }

    @Provides
    @Singleton
    fun provideText(db: MainDB): TextNoteRepository{
        return TextNoteRepoImpl(db.textNoteDao)
    }
}
