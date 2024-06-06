package kit.eliza.studyorganaizer.data.formula_note

import com.example.organizerstudy.data.formula.FormulaNoteRepository
import kotlinx.coroutines.flow.Flow

class FormulaNoteNoteRepoImpl
    (private val dao: FormulaNoteDao): FormulaNoteRepository {

    override suspend fun insertFormulaNote(formulaNote: FormulaNote) {
        dao.insertFormulaNote(formulaNote)
    }

    override suspend fun insertListFormulaNote(formulaNotes: List<FormulaNote>) {
        dao.insertListFormulaNote(formulaNotes)
    }

    override suspend fun updateFormulaNote(formulaNote: FormulaNote) {
        dao.updateFormulaNote(formulaNote)
    }

    override fun getAllFormulaNoteById(id: Int): Flow<List<FormulaNote>> {
        return dao.getAllFormulaNoteById(id)
    }

    override fun getAllFormula(): Flow<List<FormulaNote>> {
        return dao.getAllFormula()
    }

    override suspend fun deleteFormulaNote(formulaNote: FormulaNote) {
        dao.deleteFormulaNote(formulaNote)
    }

    override suspend fun deleteAllFormulaNoteById(id: Int) {
        dao.deleteAllFormulaNoteById(id)
    }
}