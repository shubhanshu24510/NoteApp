package com.shubhans.noteapp.note_List.domain.use_cases

import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.core.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: NoteItem) {
        noteRepository.deleteNote(note)
    }

}