package com.shubhans.noteapp.add_note.domain

import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.core.domain.repository.NoteRepository

class GetNoteById(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int): NoteItem? {
        return noteRepository.getNoteById(noteId)
    }
}
