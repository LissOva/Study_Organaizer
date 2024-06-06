package kit.eliza.studyorganaizer.data.to_do

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kit.eliza.studyorganaizer.data.subject.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    //Добавить или обновить задачу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    //Добавить задачи
    @Insert
    suspend fun insertListToDo(toDos: List<ToDo>)

    //Обновить задачу
    @Update
    suspend fun updateToDo(toDo: ToDo)

    //Получить избранные не выполненные задачи
    @Query("SELECT * FROM to_do WHERE status = 0 AND favourite = 1 ORDER BY id DESC")
    fun getFavouriteToDo(): Flow<List<ToDo>>

    //Получить не выполненные задачи
    @Query("SELECT * FROM to_do WHERE status = 0 AND favourite = 0 ORDER BY id DESC")
    fun getToDo(): Flow<List<ToDo>>

    //Получить выполненные задачи
    @Query("SELECT * FROM to_do WHERE status = 1 ORDER BY id DESC")
    fun getCompleteToDo(): Flow<List<ToDo>>

    //Получить все задачи
    @Query("SELECT * FROM to_do")
    fun getAllToDo(): Flow<List<ToDo>>

    //Удалить задачу
    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    //Удалить все задачи
    @Query("DELETE FROM to_do")
    suspend fun deleteAllToDo()
}