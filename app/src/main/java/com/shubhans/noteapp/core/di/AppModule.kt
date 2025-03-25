package com.shubhans.noteapp.core.di

import android.app.Application
import androidx.room.Room
import com.shubhans.noteapp.add_note.domain.GetNoteById
import com.shubhans.noteapp.add_note.domain.SearchImages
import com.shubhans.noteapp.add_note.domain.UpsertNotes
import com.shubhans.noteapp.core.data.local.NoteDatabase
import com.shubhans.noteapp.core.data.remote.api.ImageApi
import com.shubhans.noteapp.core.data.repository.NoteRepositoryImp
import com.shubhans.noteapp.core.domain.repository.NoteRepository
import com.shubhans.noteapp.note_List.domain.use_cases.DeleteNote
import com.shubhans.noteapp.note_List.domain.use_cases.GetAllNotes
import com.shubhans.noteapp.note_List.domain.use_cases.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase, imageApi: ImageApi): NoteRepository {
        return NoteRepositoryImp(noteDatabase, imageApi)
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImageApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImageApi.BASE_URL)
            .build()
            .create(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        noteRepository: NoteRepository
    ): GetAllNotes {
        return GetAllNotes(noteRepository)
    }


    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository
    ): DeleteNote {
        return DeleteNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(
        noteRepository: NoteRepository
    ): UpsertNotes {
        return UpsertNotes(noteRepository)
    }

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(
        noteRepository: NoteRepository
    ): GetNoteById {
        return GetNoteById(noteRepository)
    }

    @Provides
    fun provideUpdateNote(
        noteRepository: NoteRepository
    ): UpdateNote {
        return UpdateNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(
        noteRepository: NoteRepository
    ): SearchImages {
        return SearchImages(noteRepository)
    }
}