package kit.eliza.studyorganaizer.data.subject

import kotlinx.coroutines.flow.Flow

class SubjectRepoImpl
    (private val dao: SubjectDao): SubjectRepository {
    override suspend fun insertSubject(subject: Subject) {
        dao.insertSubject(subject)
    }

    override suspend fun updateSubject(subject: Subject) {
        dao.updateSubject(subject)
    }

    override fun getSubjectById(id: Int): Flow<Subject> {
        return dao.getSubjectById(id)
    }

    override fun getAllSubject(): Flow<List<Subject>> {
        return dao.getAllSubject()
    }

    override fun getAllSubjectRevers(): Flow<List<Subject>>{
        return dao.getAllSubjectRevers()
    }

    override fun getAllSubjectAlphabet(): Flow<List<Subject>>{
        return dao.getAllSubjectAlphabet()
    }

    override fun getAllSubjectAlphabetRevers(): Flow<List<Subject>>{
        return dao.getAllSubjectAlphabetRevers()
    }

    override fun getAllSubjectByName(nameSearch: String): Flow<List<Subject>> {
        return dao.getAllSubjectByName(nameSearch)
    }

    override suspend fun deleteSubject(subject: Subject) {
        dao.deleteSubject(subject)
    }
}