package kit.eliza.studyorganaizer.data.event_note

import kotlinx.coroutines.flow.Flow

class EventNoteRepoImpl
    (private val dao: EventNoteDao) : EventNoteRepository {
    override suspend fun insertEventNote(eventNote: EventNote) {
        dao.insertEventNote(eventNote)
    }

    override suspend fun insertListEventNote(eventNotes: List<EventNote>) {
        dao.insertListEventNote(eventNotes)
    }

    override suspend fun updateEventNote(eventNote: EventNote) {
        dao.updateEventNote(eventNote)
    }

    override fun getAllEventNoteById(id: Int): Flow<List<EventNote>> {
        return dao.getAllEventNoteById(id)
    }

    override fun getAllEventNote(): Flow<List<EventNote>> {
        return dao.getAllEventNote()
    }

    override suspend fun deleteEventNote(eventNote: EventNote) {
        dao.deleteEventNote(eventNote)
    }

    override suspend fun deleteAllEventNoteById(id: Int) {
        dao.deleteAllEventNoteById(id)
    }
}