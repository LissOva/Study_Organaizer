package kit.eliza.studyorganaizer.data.to_do

import kotlinx.coroutines.flow.Flow

class ToDoRepoImpl (private val dao: ToDoDao) : ToDoRepository{
    override suspend fun insertToDo(toDo: ToDo) {
        dao.insertToDo(toDo)
    }

    override fun getToDO(): Flow<List<ToDo>> {
        return dao.getToDO()
    }

    override fun getCompleteToDO(): Flow<List<ToDo>> {
        return dao.getCompleteToDO()
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        dao.deleteToDo(toDo)
    }
}