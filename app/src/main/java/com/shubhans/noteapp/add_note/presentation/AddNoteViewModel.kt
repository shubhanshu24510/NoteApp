package com.shubhans.noteapp.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.shubhans.noteapp.add_note.domain.SearchImages
import com.shubhans.noteapp.add_note.domain.UpsertNotes
import com.shubhans.noteapp.add_note.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val upsertNotes: UpsertNotes,
    private val searchImages: SearchImages
) : ViewModel() {
    private var searchJob: Job? = null

    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    fun onAction(action: AddNoteAction) {
        when (action) {
            is AddNoteAction.UpdateTitle -> {
                _addNoteState.update { it.copy(title = action.newTitle) }
            }

            is AddNoteAction.UpdateDescription -> {
                _addNoteState.update { it.copy(description = action.newDescription) }
            }

            is AddNoteAction.PickImage -> {
                _addNoteState.update { it.copy(imageUrl = action.imageUrl) }
            }

            AddNoteAction.UpdateImagesDialogVisibility -> {
                _addNoteState.update { it.copy(isImagesDialogShowing = !it.isImagesDialogShowing) }
            }

            AddNoteAction.SaveNote -> {
                viewModelScope.launch {
                    val isNoteSaved = upsertNote(
                        title = addNoteState.value.title,
                        description = addNoteState.value.description,
                        imageUrl = addNoteState.value.imageUrl
                    )
                    _noteSavedChannel.send(isNoteSaved)
                }
            }

            is AddNoteAction.UpdateSearchImageQuery -> {
                _addNoteState.update { it.copy(searchImagesQuery = action.newQuery) }
                onSearchImages(query = action.newQuery)
            }
        }
    }

    private fun onSearchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchImages.invoke(query = query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _addNoteState.update { it.copy(imageList = emptyList()) }
                        }

                        is Resource.Loading -> {
                            _addNoteState.update { it.copy(isLoadingImages = result.isLoading) }
                        }

                        is Resource.Success -> {
                            _addNoteState.update {
                                it.copy(
                                    imageList = result.data?.images ?: emptyList(),
                                )
                            }
                        }
                    }
                }
        }
    }

    private suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        return upsertNotes.invoke(
            title = title,
            description = description,
            imageUrl = imageUrl
        )
    }
}