package com.example.organizerstudy.data.formula

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kit.eliza.studyorganaizer.data.formula_note.FormulaNote
import kotlinx.coroutines.flow.Flow

interface FormulaNoteRepository {
    //Добавить формулу
    suspend fun insertFormulaNote(formulaNote: FormulaNote)

    //Добавить формулы
    suspend fun insertListFormulaNote(formulaNotes: List<FormulaNote>)

    //Обновить формулу
    suspend fun updateFormulaNote(formulaNote: FormulaNote)

    //Получить все формулы заметки
    fun getAllFormulaNoteById(id: Int): Flow<List<FormulaNote>>

    //Получить все формулы
    fun getAllFormula(): Flow<List<FormulaNote>>

    //Удалить формулу
    suspend fun deleteFormulaNote(formulaNote: FormulaNote)

    //Удалить все формулы заметки
    suspend fun deleteAllFormulaNoteById(id: Int)
}