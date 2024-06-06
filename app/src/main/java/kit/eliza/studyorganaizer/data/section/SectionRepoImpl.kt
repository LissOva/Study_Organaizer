package kit.eliza.studyorganaizer.data.section

import kotlinx.coroutines.flow.Flow

class SectionRepoImpl
    (private val dao: SectionDao): SectionRepository {
    override suspend fun insertSection(section: Section) {
        dao.insertSection(section)
    }

    override suspend fun insertListSection(sections: List<Section>) {
        dao.insertListSection(sections)
    }

    override suspend fun updateSection(section: Section) {
        dao.updateSection(section)
    }

    override fun getAllSectionById(idSubject: Int): Flow<List<Section>> {
        return dao.getAllSectionById(idSubject)
    }

    override fun getAllSection(): Flow<List<Section>> {
        return dao.getAllSection()
    }

    override suspend fun deleteSection(section: Section) {
        dao.deleteSection(section)
    }
}