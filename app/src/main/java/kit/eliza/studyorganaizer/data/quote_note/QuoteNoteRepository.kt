package kit.eliza.studyorganaizer.data.quote_note

import kotlinx.coroutines.flow.Flow

interface QuoteNoteRepository {
    //Добавить цитату
    suspend fun insertQuoteNote(quoteNote: QuoteNote)

    //Обновить цитату
    suspend fun updateQuoteNote(quoteNote: QuoteNote)

    //Получить все цитаты заметки
    fun getAllQuoteNoteById(id: Int): Flow<List<QuoteNote>>

    //Удалить цитату
    suspend fun deleteQuoteNote(quoteNote: QuoteNote)
}