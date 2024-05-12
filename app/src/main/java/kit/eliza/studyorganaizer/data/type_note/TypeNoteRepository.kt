package kit.eliza.studyorganaizer.data.type_note

import kotlinx.coroutines.flow.Flow

interface TypeNoteRepository {
    //Получить список типов
    fun getAllType(): Flow<List<TypeNote>>
}