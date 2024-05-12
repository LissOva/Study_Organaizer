package kit.eliza.studyorganaizer.data.section

import kotlinx.coroutines.flow.Flow

interface SectionRepository {

    //Добавить раздел
    suspend fun insertSection(section: Section)

    //Добавить раздел
    suspend fun updateSection(section: Section)

    //Получить все разделы предмета
    fun getAllSectionById(idSubject: Int): Flow<List<Section>>

    //Удалить раздел
    suspend fun deleteSection(section: Section)
}

