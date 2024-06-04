package kit.eliza.studyorganaizer.data.to_do

import kotlinx.coroutines.flow.Flow

class ToDoRepoImpl (private val dao: ToDoDao) : ToDoRepository{
    override suspend fun insertToDo(toDo: ToDo) {
        dao.insertToDo(toDo)
    }

    override suspend fun updateToDo(toDo: ToDo) {
        dao.updateToDo(toDo)
    }

    override fun getToDo(): Flow<List<ToDo>> {
        return dao.getToDo()
    }

    override fun getCompleteToDo(): Flow<List<ToDo>> {
        return dao.getCompleteToDo()
    }

    override fun getAllToDo(): Flow<List<ToDo>> {
        return dao.getAllToDo()
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }

    override suspend fun deleteAllToDo() {
        dao.deleteAllToDo()
    }
}