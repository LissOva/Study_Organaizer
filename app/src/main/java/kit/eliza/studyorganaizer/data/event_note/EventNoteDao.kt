package kit.eliza.studyorganaizer.data.event_note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventNoteDao {
    //Добавить запись события
    @Insert
    suspend fun insertEventNote(eventNote: EventNote)

    //Обновить запись события
    @Update
    suspend fun updateEventNote(eventNote: EventNote)

    //Получить все события заметки
    @Query("SELECT * FROM event WHERE idNote = :id")
    fun getAllEventNoteById(id: Int): Flow<List<EventNote>>

    //Удалить событие
    @Delete
    suspend fun deleteEventNote(eventNote: EventNote)
}