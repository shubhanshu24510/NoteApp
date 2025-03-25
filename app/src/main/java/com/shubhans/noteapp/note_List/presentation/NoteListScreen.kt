package com.shubhans.noteapp.note_List.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shubhans.noteapp.R
import com.shubhans.noteapp.note_List.domain.use_cases.DeleteNote
import com.shubhans.noteapp.note_List.domain.use_cases.GetAllNotes
import com.shubhans.noteapp.note_List.presentation.components.ListNoteItem

@Composable
fun NoteListScreen(
    onAddNoteClick: () -> Unit,
    onNoteClick: (Int) -> Unit,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
) {
    // Ensure notes are loaded on screen entry
    LaunchedEffect(Unit) {
        noteListViewModel.loadNotes()
    }
    val noteListState by noteListViewModel.noteListState.collectAsState()
    val orderByTitleState by noteListViewModel.orderByTitleState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.notes, noteListState.size),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            noteListViewModel.changeOrder()
                        }
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = if (orderByTitleState) stringResource(R.string.t)
                        else stringResource(R.string.d),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = null
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(
                items = noteListState,
                key = { it.id ?: 0 }
            ) { noteItem ->
                ListNoteItem(
                    noteItem = noteItem,
                    onNoteClick = {
                        noteItem.id?.let { noteId ->
                            onNoteClick(noteId)
                        }
                    },
                    onDeleteClick = {
                        noteListViewModel.deleteNote(noteItem)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

