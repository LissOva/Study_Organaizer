package kit.eliza.studyorganaizer.data.section

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {

    //Добавить раздел
    @Insert
    suspend fun insertSection(section: Section)

    //Обновить раздел
    @Update
    suspend fun updateSection(section: Section)

    //Получить все разделы предмета
    @Query("SELECT * FROM section WHERE idSubject = :idSubject ORDER BY name ASC")
    fun getAllSectionById(idSubject: Int): Flow<List<Section>>

    //Удалить раздел
    @Delete
    suspend fun deleteSection(section: Section)
}