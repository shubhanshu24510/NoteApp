package com.shubhans.noteapp.core.data.mapper

import com.shubhans.noteapp.core.data.local.NoteEntity
import com.shubhans.noteapp.core.data.remote.dto.ImageListDto
import com.shubhans.noteapp.core.domain.model.Images
import com.shubhans.noteapp.core.domain.model.NoteItem

fun NoteItem.toNoteEntity(): NoteEntity {
    return NoteEntity(
        title = title,
        description = description,
        dateAdded = dateAdded,
        imageUrl = imageUrl
    )
}

fun NoteItem.toNoteEntityForDelete(
): NoteEntity {
    return NoteEntity(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id
    )
}

fun NoteEntity.toNoteItem(): NoteItem {
    return NoteItem(
        id = id ?: 0,
        title = title,
        description = description,
        dateAdded = dateAdded,
        imageUrl = imageUrl
    )
}


fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.previewURL ?: ""
        } ?: emptyList()
    )
}