package kit.eliza.studyorganaizer.data.note

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kit.eliza.studyorganaizer.data.note.Note
import kotlinx.coroutines.flow.Flow
interface NoteRepository {
    //Добавить заметку
    suspend fun insertNote(note: Note)

    //Обновить заметку
    suspend fun updateNote(note: Note)

    //Получить заметки по id раздела
    fun getAllNoteById(id: Int): Flow<List<Note>>

    //Получить все заметки
    fun getAllNote(): Flow<List<Note>>

    //Получить заметку по id
    fun getNoteById(id: Int): Flow<Note>

    //Удалить заметку
    suspend fun deleteNote(note: Note)
}