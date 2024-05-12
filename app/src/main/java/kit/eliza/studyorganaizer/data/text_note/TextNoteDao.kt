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
    @Insert
    suspend fun inserTextNote(textNote: TextNote)

    //Обновить текст
    @Update
    suspend fun updateTextNote(textNote: TextNote)

    //Получить текст
    @Query("SELECT * FROM text WHERE idNote = :idNote")
    fun getTextById(idNote: Int): Flow<TextNote>

    //Удалить текст
    @Delete
    suspend fun deleteText(textNote: TextNote)
}