package kit.eliza.studyorganaizer.data.note

import kotlinx.coroutines.flow.Flow

class NoteRepoImpl
    (private val dao: NoteDao): NoteRepository {
    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    override fun getAllNoteById(id: Int): Flow<List<Note>> {
        return dao.getAllNoteById(id)
    }

    override fun getNoteById(id: Int): Flow<Note> {
        return dao.getNoteById(id)
    }

    override fun getAllNote(): Flow<List<Note>> {
        return dao.getAllNote()
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}