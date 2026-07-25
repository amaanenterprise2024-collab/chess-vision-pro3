package com.example.chessvisionpro.api

import com.example.chessvisionpro.model.Game
import com.example.chessvisionpro.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface LichessService {
    /**
     * Get the currently authenticated user's profile
     */
    @GET("/api/account")
    suspend fun getAccount(@Header("Authorization") token: String): Response<User>

    /**
     * Get a user's public profile
     */
    @GET("/api/user/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String
    ): Response<User>

    /**
     * Get a user's game history
     */
    @GET("/api/games/user/{username}")
    suspend fun getUserGames(
        @Path("username") username: String,
        @Query("max") max: Int = 50,
        @Query("pgnInJson") pgnInJson: Boolean = true,
        @Header("Authorization") token: String? = null
    ): Response<String>

    /**
     * Stream events (real-time updates)
     */
    @Streaming
    @GET("/api/stream/event")
    suspend fun streamEvents(
        @Header("Authorization") token: String
    ): Response<String>

    /**
     * Get a specific game
     */
    @GET("/api/game/{gameId}")
    suspend fun getGame(
        @Path("gameId") gameId: String,
        @Query("pgn") pgn: Boolean = true,
        @Header("Authorization") token: String? = null
    ): Response<String>

    /**
     * Get engine analysis (Stockfish)
     */
    @GET("/api/engine/analysis/{gameId}")
    suspend fun getEngineAnalysis(
        @Path("gameId") gameId: String,
        @Query("multipv") multipv: Int = 1
    ): Response<String>

    /**
     * Get daily puzzles
     */
    @GET("/api/puzzle/daily")
    suspend fun getDailyPuzzle(): Response<String>

    /**
     * Get puzzle activity
     */
    @GET("/api/puzzle/activity")
    suspend fun getPuzzleActivity(
        @Query("max") max: Int = 50,
        @Header("Authorization") token: String
    ): Response<String>

    /**
     * Get opening moves statistics
     */
    @GET("/api/opening")
    suspend fun getOpeningStats(
        @Query("fen") fen: String,
        @Query("topGames") topGames: Int = 0,
        @Query("recentGames") recentGames: Int = 0,
        @Query("players") players: List<String>? = null
    ): Response<String>

    /**
     * Validate if the server is running
     */
    @GET("/api/status")
    suspend fun getStatus(): Response<String>
}