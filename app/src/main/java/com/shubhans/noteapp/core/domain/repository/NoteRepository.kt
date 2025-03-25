package com.shubhans.noteapp.core.domain.repository

import com.shubhans.noteapp.core.domain.model.Images
import com.shubhans.noteapp.core.domain.model.NoteItem

interface NoteRepository {
    suspend fun getAllNotes(): List<NoteItem>
    suspend fun upsertNote(note: NoteItem)
    suspend fun deleteNote(note: NoteItem)
    suspend fun searchImages(query: String): Images?
}