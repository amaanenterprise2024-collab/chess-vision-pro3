package com.example.chessvisionpro.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val username: String,
    val perfs: Map<String, PerfStats>,
    val createdAt: Long,
    val seenAt: Long? = null,
    val patron: Boolean = false,
    val verified: Boolean = false,
    val title: String? = null,
    @SerializedName("profile")
    val profile: UserProfile? = null,
    val gameCount: Int = 0,
    val rating: Int = 0
)

data class PerfStats(
    val games: Int,
    val rating: Int,
    val rd: Int,
    val prog: Int? = null,
    val prov: Boolean = false
)

data class UserProfile(
    val bio: String? = null,
    val country: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val location: String? = null
)
