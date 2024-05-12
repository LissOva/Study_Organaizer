package kit.eliza.studyorganaizer.data.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    //Добавить заметку
    @Insert
    suspend fun insertNote(note: Note)

    //Обновить заметку
    @Update
    suspend fun updateNote(note: Note)

    //Получить заметки по id раздела
    @Query("SELECT * FROM note WHERE idSection = :id ORDER BY name ASC")
    fun getAllNoteById(id: Int): Flow<List<Note>>

    //Получить все заметки
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAllNote(): Flow<List<Note>>

    //Получить заметку по id
    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): Flow<Note>

    //Удалить заметку
    @Delete
    suspend fun deleteNote(note: Note)
}