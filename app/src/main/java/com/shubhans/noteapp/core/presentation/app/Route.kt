package com.shubhans.noteapp.core.presentation.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object NoteList : Route

    @Serializable
    data object AddNote : Route

}