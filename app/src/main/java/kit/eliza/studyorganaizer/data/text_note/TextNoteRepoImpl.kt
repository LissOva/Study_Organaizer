package kit.eliza.studyorganaizer.data.text_note

import kotlinx.coroutines.flow.Flow

class TextNoteRepoImpl
    (private val dao: TextNoteDao): TextNoteRepository {
    override suspend fun inserTextNote(textNote: TextNote) {
        dao.inserTextNote(textNote)
    }

    override suspend fun updateTextNote(textNote: TextNote) {
        dao.updateTextNote(textNote)
    }

    override fun getTextById(idNote: Int): Flow<TextNote> {
        return dao.getTextById(idNote)
    }

    override fun getAllText(): Flow<List<TextNote>> {
        return dao.getAllText()
    }

    override suspend fun deleteText(textNote: TextNote) {
        dao.deleteText(textNote)
    }
}