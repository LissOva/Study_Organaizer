package kit.eliza.studyorganaizer.data.quote_note
import kotlinx.coroutines.flow.Flow

class QuoteNoteNoteRepoImpl
    (private val dao: QuoteNoteDao): QuoteNoteRepository {
    override suspend fun insertQuoteNote(quoteNote: QuoteNote) {
        dao.insertQuoteNote(quoteNote)
    }

    override suspend fun updateQuoteNote(quoteNote: QuoteNote) {
        dao.updateQuoteNote(quoteNote)
    }

    override fun getAllQuoteNoteById(id: Int): Flow<List<QuoteNote>> {
        return dao.getAllQuotNoteById(id)
    }

    override fun getAllQuotNote(): Flow<List<QuoteNote>> {
        return dao.getAllQuotNote()
    }

    override suspend fun deleteQuoteNote(quoteNote: QuoteNote) {
        dao.deleteQuoteNote(quoteNote)
    }

    override suspend fun deleteAllQuotNoteById(id: Int) {
        dao.deleteAllQuotNoteById(id)
    }

}