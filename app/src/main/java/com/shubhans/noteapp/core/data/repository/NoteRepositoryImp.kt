package com.shubhans.noteapp.core.data.repository

import com.shubhans.noteapp.core.data.local.NoteDao
import com.shubhans.noteapp.core.data.local.NoteDatabase
import com.shubhans.noteapp.core.data.mapper.toImages
import com.shubhans.noteapp.core.data.mapper.toNoteEntity
import com.shubhans.noteapp.core.data.mapper.toNoteEntityForDelete
import com.shubhans.noteapp.core.data.mapper.toNoteItem
import com.shubhans.noteapp.core.data.remote.api.ImageApi
import com.shubhans.noteapp.core.domain.model.Images
import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.core.domain.repository.NoteRepository

class NoteRepositoryImp(
    private val noteDatabase: NoteDatabase,
    private val imageApi: ImageApi
) : NoteRepository {
    private val noteDao: NoteDao = noteDatabase.noteDao()

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteDao.getAllNotesEntities().map { it.toNoteItem() }
    }

    override suspend fun upsertNote(note: NoteItem) {
        noteDao.upsertNoteEntity(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: NoteItem) {
        noteDao.deleteNoteEntity(note.toNoteEntityForDelete())
    }

    override suspend fun searchImages(query: String): Images? {
        return imageApi.searchImages(query)?.toImages()
    }

    override suspend fun getNoteById(noteId: Int): NoteItem? {
        return noteDao.getNoteById(noteId)?.toNoteItem()
    }

    override suspend fun updateNote(noteItem: NoteItem) {
        noteDao.updateNote(noteItem.toNoteEntity())
    }
}