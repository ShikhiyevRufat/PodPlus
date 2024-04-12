package com.example.network

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore {
            return Firebase.firestore
        }

//        @Provides
//        @Singleton
//        fun provideFirebaseStorage(): FirebaseStorage {
//            return Firebase.storage
//        }
    }
