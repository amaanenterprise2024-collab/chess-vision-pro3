package com.example.chessvisionpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val title: String?,
    @SerializedName("created_at")
    val createdAt: Long,
    val profile: Profile?,
    val perfs: Perfs,
    @SerializedName("play_time")
    val playTime: PlayTime?,
    val nbFollowing: Int,
    val nbFollowers: Int,
    val online: Boolean?,
    val tosViolation: Boolean?,
    val patron: Boolean?,
    val badges: List<Badge>?
) {
    data class Profile(
        val bio: String?,
        val country: String?,
        val location: String?,
        val firstName: String?,
        val lastName: String?
    )

    data class Perfs(
        val bullet: PerfStats?,
        val blitz: PerfStats?,
        val rapid: PerfStats?,
        val classical: PerfStats?,
        val puzzle: PerfStats?
    )

    data class PerfStats(
        val games: Int,
        val rating: Int,
        val rd: Int,
        val prov: String?,
        val prog: Int?
    )

    data class PlayTime(
        val total: Int,
        val tv: Int
    )

    data class Badge(
        val name: String,
        val iconUrl: String
    )
}