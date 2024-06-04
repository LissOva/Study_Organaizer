package kit.eliza.studyorganaizer.data.subject

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    //Добавить предмет
    suspend fun insertSubject(subject: Subject)

    //Добавить список предметов
    suspend fun insertListSubject(subjects: List<Subject>)

    //Обновить предмет
    suspend fun updateSubject(subject: Subject)

    //Получить предмет
    fun getSubjectById(id: Int): Flow<Subject>

    //Получить предметы
    fun getAllSubject(): Flow<List<Subject>>

    //Получить предметы в обратном порядке
    fun getAllSubjectRevers(): Flow<List<Subject>>

    //Получить предметы в алфавитном порядке
    fun getAllSubjectAlphabet(): Flow<List<Subject>>

    //Получить предметы в обратном алфавитном порядке
    fun getAllSubjectAlphabetRevers(): Flow<List<Subject>>

    //Получить предметы по имени
    fun getAllSubjectByName(nameSearch: String): Flow<List<Subject>>

    //Удалить предмет
    suspend fun deleteSubject(subject: Subject)

    //Удалить все предметы
    suspend fun deleteAllSubject()
}