package kit.eliza.studyorganaizer.data.section

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface SectionRepository {

    //Добавить раздел
    suspend fun insertSection(section: Section)

    //Добавить разделы
    suspend fun insertListSection(sections: List<Section>)

    //Обновить раздел
    suspend fun updateSection(section: Section)

    //Получить все разделы предмета
    fun getAllSectionById(idSubject: Int): Flow<List<Section>>

    //Получить все разделы
    fun getAllSection(): Flow<List<Section>>

    //Удалить раздел
    suspend fun deleteSection(section: Section)
}

