package kit.eliza.studyorganaizer.data.text_note

import kit.eliza.studyorganaizer.data.text_note.TextNote
import kotlinx.coroutines.flow.Flow

interface TextNoteRepository {
    //Добавить текст
    suspend fun inserTextNote(textNote: TextNote)

    //Обновить текст
    suspend fun updateTextNote(textNote: TextNote)

    //Получить текст
    fun getTextById(idNote: Int): Flow<TextNote>

    //Удалить текст
    suspend fun deleteText(textNote: TextNote)
}