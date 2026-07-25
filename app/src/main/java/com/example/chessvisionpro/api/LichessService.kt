package com.example.chessvisionpro.api

import com.example.chessvisionpro.model.Game
import com.example.chessvisionpro.model.User
import retrofit2.Response
import retrofit2.http.*

interface LichessService {

    /**
     * Get authenticated user's profile information
     */
    @GET("/api/account")
    suspend fun getAccount(@Header("Authorization") token: String): Response<User>

    /**
     * Get user's recent games
     */
    @GET("/api/games/user/{username}")
    suspend fun getUserGames(
        @Path("username") username: String,
        @Header("Authorization") token: String,
        @Query("max") max: Int = 50,
        @Query("sort") sort: String = "dateDesc"
    ): Response<String> // Returns NDJSON format

    /**
     * Get game analysis
     */
    @GET("/api/games/{gameId}")
    suspend fun getGame(
        @Path("gameId") gameId: String,
        @Header("Authorization") token: String
    ): Response<Game>

    /**
     * Stream events (games, challenges, etc.)
     */
    @GET("/api/stream/event")
    suspend fun streamEvents(
        @Header("Authorization") token: String
    ): Response<String> // Server-Sent Events

    /**
     * Get daily puzzle
     */
    @GET("/api/puzzle/daily")
    suspend fun getDailyPuzzle(
        @Header("Authorization") token: String
    ): Response<String>

    /**
     * Get opening explorer stats for a position
     */
    @GET("/api/explorer/master")
    suspend fun getOpeningStats(
        @Query("fen") fen: String,
        @Header("Authorization") token: String
    ): Response<String>

    /**
     * Request engine analysis for a position
     */
    @POST("/api/engine/analyze")
    suspend fun analyzePosition(
        @Query("fen") fen: String,
        @Query("depth") depth: Int = 20,
        @Header("Authorization") token: String
    ): Response<String>

    /**
     * Create a game challenge
     */
    @POST("/api/challenge/{username}")
    suspend fun challengeUser(
        @Path("username") username: String,
        @QueryMap options: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<String>
}
