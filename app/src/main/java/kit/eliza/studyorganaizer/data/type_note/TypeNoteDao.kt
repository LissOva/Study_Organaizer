package kit.eliza.studyorganaizer.data.type_note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeNoteDao {
    //Получить список типов
    @Query("SELECT * FROM type")
    fun getAllType(): Flow<List<TypeNote>>
}