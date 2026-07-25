package com.example.chessvisionpro.vision

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.processor.ImageProcessor
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PieceRecognizer(context: Context) {
    private var interpreter: Interpreter? = null
    private var inputSize = 224

    private val pieces = arrayOf(
        "empty", "white_pawn", "white_knight", "white_bishop",
        "white_rook", "white_queen", "white_king",
        "black_pawn", "black_knight", "black_bishop",
        "black_rook", "black_queen", "black_king"
    )

    init {
        loadModel(context)
    }

    private fun loadModel(context: Context) {
        try {
            val modelBuffer = loadModelFile(context, "chess_piece_recognition.tflite")
            val options = Interpreter.Options()
            options.setNumThreads(4)
            interpreter = Interpreter(modelBuffer, options)

            // Get input tensor dimensions
            val inputTensor = interpreter?.getInputTensor(0)
            if (inputTensor != null) {
                val inputShape = inputTensor.shape()
                if (inputShape.size > 2) {
                    inputSize = inputShape[1]
                }
            }
            Log.d("PieceRecognizer", "Model loaded successfully. Input size: $inputSize")
        } catch (e: Exception) {
            Log.e("PieceRecognizer", "Error loading model: ${e.message}")
        }
    }

    private fun loadModelFile(context: Context, filename: String): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(filename)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun recognizePiece(bitmap: Bitmap): Pair<String, Float>? {
        return try {
            if (interpreter == null) {
                Log.e("PieceRecognizer", "Interpreter is null")
                return null
            }

            // Preprocess image
            val tensorImage = TensorImage()
            tensorImage.load(bitmap)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
                .add { imageData -> imageData } // Normalize if needed
                .build()

            val processedTensorImage = imageProcessor.process(tensorImage)

            // Run inference
            val output = Array(1) { FloatArray(pieces.size) }
            interpreter?.run(processedTensorImage.buffer, output)

            // Get highest confidence
            val confidences = output[0]
            val maxIndex = confidences.indices.maxByOrNull { confidences[it] } ?: 0
            val confidence = confidences[maxIndex]

            if (confidence > CONFIDENCE_THRESHOLD) {
                Pair(pieces[maxIndex], confidence)
            } else {
                Pair("empty", confidence)
            }
        } catch (e: Exception) {
            Log.e("PieceRecognizer", "Error recognizing piece: ${e.message}")
            null
        }
    }

    fun recognizeBoard(bitmap: Bitmap, boardCorners: FloatArray): Array<Array<String>> {
        val board = Array(8) { Array(8) { "empty" } }

        return try {
            // Extract 8x8 squares from the board
            val squareWidth = bitmap.width / 8
            val squareHeight = bitmap.height / 8

            for (row in 0..7) {
                for (col in 0..7) {
                    val left = col * squareWidth
                    val top = row * squareHeight
                    val width = squareWidth.coerceAtMost(bitmap.width - left)
                    val height = squareHeight.coerceAtMost(bitmap.height - top)

                    if (width > 0 && height > 0) {
                        val squareBitmap = Bitmap.createBitmap(bitmap, left, top, width, height)
                        val (piece, _) = recognizePiece(squareBitmap) ?: continue
                        board[row][col] = piece
                        squareBitmap.recycle()
                    }
                }
            }
            board
        } catch (e: Exception) {
            Log.e("PieceRecognizer", "Error recognizing board: ${e.message}")
            board
        }
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }

    companion object {
        private const val CONFIDENCE_THRESHOLD = 0.5f
    }
}