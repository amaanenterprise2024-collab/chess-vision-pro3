package com.example.chessvisionpro.model

import com.google.gson.annotations.SerializedName

data class Game(
    val id: String,
    val createdAt: Long,
    val lastMoveAt: Long,
    val status: String,
    val players: Players,
    val pgn: String,
    val opening: Opening? = null,
    val moves: String,
    val clock: Clock? = null,
    val rated: Boolean,
    val variant: String,
    val speed: String,
    val perf: String
)

data class Players(
    val white: Player,
    val black: Player
)

data class Player(
    val user: User? = null,
    val rating: Int,
    val ratingDiff: Int? = null
)

data class Opening(
    val eco: String,
    val name: String,
    val ply: Int
)

data class Clock(
    val initial: Int,
    val increment: Int,
    val totalTime: Int? = null
)
