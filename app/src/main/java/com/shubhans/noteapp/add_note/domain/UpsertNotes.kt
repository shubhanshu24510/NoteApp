package com.shubhans.noteapp.add_note.domain

import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.core.domain.repository.NoteRepository

class UpsertNotes(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        noteId: Int? = null,
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        if (title.isEmpty() || description.isEmpty()) {
            return false
        }

        val note = NoteItem(
            id = noteId,
            title = title,
            description = description,
            imageUrl = imageUrl,
            dateAdded = System.currentTimeMillis()
        )
        noteRepository.upsertNote(note = note)
        return true
    }
}