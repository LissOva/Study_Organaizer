package kit.eliza.studyorganaizer.data.to_do

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kit.eliza.studyorganaizer.data.subject.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    //Добавить или обновить задачу
    @Insert
    suspend fun insertToDo(toDo: ToDo)

    //Получить не выполненные задачи
    @Query("SELECT * FROM to_do WHERE status = 0")
    fun getToDO(): Flow<List<ToDo>>

    //Получить выполненные задачи
    @Query("SELECT * FROM to_do WHERE status = 1")
    fun getCompleteToDO(): Flow<List<ToDo>>

    //Удалить задачу
    @Delete
    suspend fun deleteToDo(toDo: ToDo)
}