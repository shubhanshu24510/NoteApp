package com.shubhans.noteapp.core.domain.model

data class NoteItem(
    var title: String,
    var description: String,
    var imageUrl: String,
    var dateAdded: Long,
    val id: Int = 0,
)