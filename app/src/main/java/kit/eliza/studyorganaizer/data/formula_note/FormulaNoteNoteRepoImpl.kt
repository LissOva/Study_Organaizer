package com.example.organizerstudy.data.formula

import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kit.eliza.studyorganaizer.data.formula_note.FormulaNoteDao
import kotlinx.coroutines.flow.Flow

class FormulaNoteNoteRepoImpl
    (private val dao: FormulaNoteDao): FormulaNoteRepository {

    override suspend fun insertFormulaNote(formulaNote: FormulaNote) {
        dao.insertFormulaNote(formulaNote)
    }

    override suspend fun updateFormulaNote(formulaNote: FormulaNote) {
        dao.updateFormulaNote(formulaNote)
    }

    override fun getAllFormulaNoteById(id: Int): Flow<List<FormulaNote>> {
        return dao.getAllFormulaNoteById(id)
    }

    override suspend fun deleteFormulaNote(formulaNote: FormulaNote) {
        dao.deleteFormulaNote(formulaNote)
    }

    override suspend fun deleteAllFormulaNoteById(id: Int) {
        dao.deleteAllFormulaNoteById(id)
    }
}