package com.shubhans.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shubhans.noteapp.add_note.presentation.AddNoteScreenRoot
import com.shubhans.noteapp.core.presentation.app.Route
import com.shubhans.noteapp.core.presentation.ui.theme.NoteAppTheme
import com.shubhans.noteapp.note_List.presentation.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier, navController = navController, startDestination = Route.NoteList
    ) {

        composable<Route.NoteList> {
            NoteListScreen(
                onAddNoteClick = {
                    navController.navigate(Route.AddNote)
                })
        }

        composable<Route.AddNote> {
            AddNoteScreenRoot(
                onSaveNote = {
                    navController.popBackStack()
                })
        }
    }
}