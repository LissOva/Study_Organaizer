package kit.eliza.studyorganaizer.data.event_note

import kotlinx.coroutines.flow.Flow

interface EventNoteRepository {
    //Добавить запись события
    suspend fun insertEventNote(eventNote: EventNote)

    //Обновить запись события
    suspend fun updateEventNote(eventNote: EventNote)

    //Получить все события заметки
    fun getAllEventNoteById(id: Int): Flow<List<EventNote>>

    //Удалить событие
    suspend fun deleteEventNote(eventNote: EventNote)
}