package com.shubhans.noteapp.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteentity")
    suspend fun getAllNotesEntities(): List<NoteEntity>

    @Upsert
    suspend fun upsertNoteEntity(note: NoteEntity)

    @Delete
    suspend fun deleteNoteEntity(note: NoteEntity)
}