package com.example.chatroom.core.domain.repository

import com.example.chatroom.core.domain.Resource

interface ImageRepository{
    suspend fun storeImage(uri: String): Resource<String>
}