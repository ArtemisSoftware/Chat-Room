package com.example.chatroom.feature.authentication.data.repository

import com.example.chatroom.core.domain.Resource
import com.example.chatroom.core.domain.error.DataError
import com.example.chatroom.feature.authentication.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
): AuthenticationRepository {

    override suspend fun isLoggedIn(): Resource<Boolean> {
        val currentUser = firebaseAuth.currentUser
        return Resource.Success(currentUser != null)
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Resource<Unit> {
        return suspendCoroutine { continuation ->
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result.user?.let {
                            it.updateProfile(
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()
                            ).addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    continuation.resume(Resource.Success(Unit))
                                } else {
                                    continuation.resume(
                                        Resource.Failure(
                                            DataError.FirebaseError.Error(
                                                updateTask.exception?.message
                                            )
                                        )
                                    )
                                }
                            }
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Resource.Failure(DataError.FirebaseError.NoUserFound)
                        )
                    } else {
                        continuation.resume(
                            Resource.Failure(DataError.FirebaseError.Error(task.exception?.message))
                        )
                    }
                }
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<Unit> {
        return suspendCoroutine { continuation ->
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result.user?.let {
                            continuation.resume(Resource.Success(Unit))
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Resource.Failure(
                                DataError.FirebaseError.Error(
                                    task.exception?.message
                                )
                            )
                        )

                    } else {
                        continuation.resume(
                            Resource.Failure(
                                DataError.FirebaseError.Error(
                                    task.exception?.message
                                )
                            )
                        )
                    }
                }
        }
    }
}