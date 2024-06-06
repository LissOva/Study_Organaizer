package kit.eliza.studyorganaizer.data.event_note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventNoteDao {
    //Добавить событие
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventNote(eventNote: EventNote)

    //Добавить события
    @Insert
    suspend fun insertListEventNote(eventNotes: List<EventNote>)

    //Обновить событие
    @Update
    suspend fun updateEventNote(eventNote: EventNote)

    //Получить все события заметки
    @Query("SELECT * FROM event WHERE idNote = :id")
    fun getAllEventNoteById(id: Int): Flow<List<EventNote>>

    //Получить все события
    @Query("SELECT * FROM event")
    fun getAllEventNote(): Flow<List<EventNote>>

    //Удалить событие
    @Delete
    suspend fun deleteEventNote(eventNote: EventNote)

    //Удалить все события заметки
    @Query("DELETE FROM event WHERE idNote = :id")
    suspend fun deleteAllEventNoteById(id: Int)
}