package kit.eliza.studyorganaizer.data.quote_note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteNoteDao {
    //Добавить цитату
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuoteNote(quoteNote: QuoteNote)

    //Обновить цитату
    @Update
    suspend fun updateQuoteNote(quoteNote: QuoteNote)

    //Получить все цитаты заметки
    @Query("SELECT * FROM quote WHERE idNote = :id")
    fun getAllQuotNoteById(id: Int): Flow<List<QuoteNote>>

    //Удалить цитату
    @Delete
    suspend fun deleteQuoteNote(quoteNote: QuoteNote)

    //Удалить все цитаты заметки
    @Query("DELETE FROM quote WHERE idNote = :id")
    suspend fun deleteAllQuotNoteById(id: Int)
}