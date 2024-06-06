package kit.eliza.studyorganaizer.data.text_note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TextNoteDao {
    //Добавить текст
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTextNote(textNote: TextNote)

    //Добавить тексты
    @Insert
    suspend fun inserAllTextNote(textNotes: List<TextNote>)

    //Обновить текст
    @Update
    suspend fun updateTextNote(textNote: TextNote)

    //Получить текст
    @Query("SELECT * FROM text WHERE idNote = :idNote")
    fun getTextById(idNote: Int): Flow<TextNote>

    //Получить все тексты
    @Query("SELECT * FROM text")
    fun getAllText(): Flow<List<TextNote>>

    //Удалить текст
    @Delete
    suspend fun deleteText(textNote: TextNote)
}