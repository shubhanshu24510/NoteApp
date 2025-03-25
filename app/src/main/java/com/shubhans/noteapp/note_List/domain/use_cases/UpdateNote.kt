package com.shubhans.noteapp.note_List.domain.use_cases

import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.core.domain.repository.NoteRepository

class UpdateNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteItem: NoteItem) {
        noteRepository.updateNote(noteItem)
    }
}
