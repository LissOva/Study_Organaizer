package kit.eliza.studyorganaizer.data.subject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    //Добавить предмет
    @Insert
    suspend fun insertSubject(subject: Subject)

    //Добавить список предметов
    @Insert
    suspend fun insertListSubject(subjects: List<Subject>)

    //Обновить предмет
    @Update
    suspend fun updateSubject(subject: Subject)

    //Получить предмет
    @Query("SELECT * FROM subject WHERE id = :id")
    fun getSubjectById(id: Int): Flow<Subject>

    //Получить предметы
    @Query("SELECT * FROM subject")
    fun getAllSubject(): Flow<List<Subject>>

    //Получить предметы в обратном порядке
    @Query("SELECT * FROM subject ORDER BY id DESC")
    fun getAllSubjectRevers(): Flow<List<Subject>>

    //Получить предметы в алфавитном порядке
    @Query("SELECT * FROM subject ORDER BY name ASC")
    fun getAllSubjectAlphabet(): Flow<List<Subject>>

    //Получить предметы в обратном алфавитном порядке
    @Query("SELECT * FROM subject ORDER BY name DESC")
    fun getAllSubjectAlphabetRevers(): Flow<List<Subject>>

    //Получить предметы по имени
    @Query("SELECT * FROM subject WHERE name LIKE :nameSearch")
    fun getAllSubjectByName(nameSearch: String): Flow<List<Subject>>

    //Удалить предмет
    @Delete
    suspend fun deleteSubject(subject: Subject)

    //Удалить все предметы
    @Query("DELETE FROM subject")
    suspend fun deleteAllSubject()
}