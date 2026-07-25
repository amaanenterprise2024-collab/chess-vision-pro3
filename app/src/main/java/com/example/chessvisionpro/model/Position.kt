package com.example.chessvisionpro.model

data class Position(
    val fen: String,
    val uci: String?,
    val san: String?,
    val evaluation: Evaluation?,
    val bestMove: String?,
    val legalMoves: List<String>,
    val inCheck: Boolean,
    val isCheckmate: Boolean,
    val isStalemate: Boolean,
    val moveNumber: Int,
    val halfMoveCount: Int
) {
    data class Evaluation(
        val mate: Int?,
        val cp: Int?,
        val depth: Int
    )
}