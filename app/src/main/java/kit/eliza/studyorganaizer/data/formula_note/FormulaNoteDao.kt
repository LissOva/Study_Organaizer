package kit.eliza.studyorganaizer.data.formula_note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FormulaNoteDao {
    //Добавить формулу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFormulaNote(formulaNote: FormulaNote)

    //Обновить формулу
    @Update
    suspend fun updateFormulaNote(formulaNote: FormulaNote)

    //Получить все формулы заметки
    @Query("SELECT * FROM formula WHERE idNote = :id")
    fun getAllFormulaNoteById(id: Int): Flow<List<FormulaNote>>

    //Удалить формулу
    @Delete
    suspend fun deleteFormulaNote(formulaNote: FormulaNote)

    //Удалить все формулы заметки
    @Query("DELETE FROM formula WHERE idNote = :id")
    suspend fun deleteAllFormulaNoteById(id: Int)
}