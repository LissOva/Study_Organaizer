package kit.eliza.studyorganaizer.data.to_do

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    //Добавить или обновить задачу
    suspend fun insertToDo(toDo: ToDo)

    //Обновить задачу
    suspend fun updateToDo(toDo: ToDo)

    //Получить не выполненные задачи
    fun getToDo(): Flow<List<ToDo>>

    //Получить выполненные задачи
    fun getCompleteToDo(): Flow<List<ToDo>>

    //Удалить задачу
    suspend fun deleteToDo(toDo: ToDo)
}