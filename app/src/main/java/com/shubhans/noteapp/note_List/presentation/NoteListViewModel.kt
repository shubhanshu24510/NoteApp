package com.shubhans.noteapp.note_List.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubhans.noteapp.core.domain.model.NoteItem
import com.shubhans.noteapp.note_List.domain.use_cases.DeleteNote
import com.shubhans.noteapp.note_List.domain.use_cases.GetAllNotes
import com.shubhans.noteapp.note_List.domain.use_cases.UpdateNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotes: GetAllNotes,
    private val deleteNote: DeleteNote,
    private val updateNote: UpdateNote
) : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItem>>(emptyList())
    val noteListState = _noteListState.asStateFlow()

    private val _orderByTitleState = MutableStateFlow(false)
    val orderByTitleState = _orderByTitleState.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch {
            val notes = getAllNotes.invoke(orderByTitleState.value)
            _noteListState.value = notes
        }
    }

    fun deleteNote(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNote.invoke(noteItem)
            loadNotes()
        }
    }

    fun changeOrder() {
        viewModelScope.launch {
            _orderByTitleState.value = !_orderByTitleState.value
            loadNotes()
        }
    }

    // Ensure data refresh after navigating back
    fun refreshNotes() {
        loadNotes()
    }
}
