package kit.eliza.studyorganaizer.data.to_do

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    //Добавить или обновить задачу
    suspend fun insertToDo(toDo: ToDo)

    //Получить не выполненные задачи
    fun getToDO(): Flow<List<ToDo>>

    //Получить выполненные задачи
    fun getCompleteToDO(): Flow<List<ToDo>>

    //Удалить задачу
    suspend fun deleteToDo(toDo: ToDo)
}