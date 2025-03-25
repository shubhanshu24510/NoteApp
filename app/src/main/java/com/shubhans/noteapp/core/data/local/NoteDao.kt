package com.shubhans.noteapp.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.shubhans.noteapp.core.domain.model.NoteItem

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteentity")
    suspend fun getAllNotesEntities(): List<NoteEntity>

    @Upsert
    suspend fun upsertNoteEntity(note: NoteEntity)

    @Query("SELECT * FROM noteentity WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteEntity?

    @Delete
    suspend fun deleteNoteEntity(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: NoteEntity)
}