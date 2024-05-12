package kit.eliza.studyorganaizer.data.type_note

import kotlinx.coroutines.flow.Flow

class TypeNoteRepoImpl
    (private val dao: TypeNoteDao): TypeNoteRepository {
    override fun getAllType(): Flow<List<TypeNote>> {
        return dao.getAllType()
    }
}