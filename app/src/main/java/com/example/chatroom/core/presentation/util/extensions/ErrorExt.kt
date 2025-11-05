package com.example.chatroom.core.presentation.util.extensions

import com.example.chatroom.core.domain.error.CRError
import com.example.chatroom.core.domain.error.DataError

fun CRError.toText(): String {
    return when (this) {
        is DataError.ChatError.Error -> message!!
        DataError.ChatError.MessageNotSent -> "Message Not Sent"
        is DataError.FirebaseError.Error -> message!!
        DataError.FirebaseError.NoUserFound -> "No User Found"
        DataError.FirebaseError.UnableToSubscribe ->"Unable To Subscribe"
        is DataError.SupabaseError.Error -> message!!
        DataError.SupabaseError.UnableToOpenStream -> "Unable To Open Stream"
    }
}
