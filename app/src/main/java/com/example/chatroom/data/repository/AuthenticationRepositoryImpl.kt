package com.example.chatroom.data.repository

class AuthenticationRepositoryImpl {
    //TODO: completar
/*
    setup
    https://www.youtube.com/watch?v=qnSY34NWOOY&t=1206s

    fun signIn(email: String, password: String) {
        _state.value = SignInState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        _state.value = SignInState.Success
                        return@addOnCompleteListener
                    }
                    _state.value = SignInState.Error

                } else {
                    _state.value = SignInState.Error
                }
            }
    }


    fun signUp(name: String, email: String, password: String) {
        _state.value = SignUpState.Loading
        // Firebase signIn
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let {
                        it.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        )?.addOnCompleteListener {
                            _state.value = SignUpState.Success
                        }
                        return@addOnCompleteListener
                    }
                    _state.value = SignUpState.Error

                } else {
                    _state.value = SignUpState.Error
                }
            }
    }
    */
}