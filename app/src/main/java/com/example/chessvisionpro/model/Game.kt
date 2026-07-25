package com.example.chessvisionpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    val id: String,
    val userId: String,
    @SerializedName("created_at")
    val createdAt: Long,
    val status: String,
    val speed: String,
    val perf: String,
    @SerializedName("white")
    val whitePlayer: Player,
    @SerializedName("black")
    val blackPlayer: Player,
    val initialFen: String,
    val moves: String,
    val result: String?,
    val winner: String?,
    val rated: Boolean,
    val variant: String,
    val opening: Opening?,
    val lastMoveAt: Long?
) {
    data class Player(
        val userId: String,
        val username: String,
        val rating: Int,
        val ratingDiff: Int?
    )

    data class Opening(
        val eco: String,
        val name: String
    )
}