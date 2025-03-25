package com.shubhans.noteapp.add_note.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.shubhans.noteapp.R
import com.shubhans.noteapp.add_note.presentation.components.ImagesDialogContent
import com.shubhans.noteapp.core.presentation.ui.theme.NoteAppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNoteScreenRoot(
    onSaveNote: () -> Unit,
    addNoteViewModel: AddNoteViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        addNoteViewModel.noteSavedFlow.collectLatest { saved ->
            if (saved) {
                onSaveNote()
            } else {
                Toast.makeText(
                    context,
                    "Failed to save note",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    val addNoteState = addNoteViewModel.addNoteState.collectAsState()
    AddNoteScreenContent(
        state = addNoteState.value,
        action = addNoteViewModel::onAction
    )
}

@Composable
private fun AddNoteScreenContent(
    state: AddNoteState,
    action: (AddNoteAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    action(AddNoteAction.UpdateImagesDialogVisibility)
                },
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(state.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.title,
            onValueChange = {
                action(AddNoteAction.UpdateTitle(it))
            },
            label = {
                Text(text = stringResource(R.string.title))
            },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = state.description,
            onValueChange = {
                action(AddNoteAction.UpdateDescription(it))
            },
            label = {
                Text(text = stringResource(R.string.description))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                action(AddNoteAction.SaveNote)
            }
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (state.isImagesDialogShowing) {
        Dialog(
            onDismissRequest = {
                action(AddNoteAction.UpdateImagesDialogVisibility)
            }
        ) {
            ImagesDialogContent(
                addNoteState = state,
                onSearchQueryChange = {
                    action(AddNoteAction.UpdateSearchImageQuery(it))
                },
                onImageClick = {
                    action(AddNoteAction.PickImage(it))
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddNoteScreen() {
    NoteAppTheme {
        AddNoteScreenContent(
            state = AddNoteState(),
            action = { }
        )
    }
}
