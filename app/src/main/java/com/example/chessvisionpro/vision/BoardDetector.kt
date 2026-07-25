package com.example.chessvisionpro.vision

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import java.nio.MappedByteBuffer

/**
 * BoardDetector - Detects chess board in camera frames using TensorFlow Lite
 */
class BoardDetector(context: Context) {

    private var interpreter: Interpreter? = null

    init {
        try {
            val modelBuffer = loadModelFile(context)
            interpreter = Interpreter(modelBuffer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Detect chess board corners in the given bitmap
     * Returns array of 8 coordinates [x1,y1,x2,y2,x3,y3,x4,y4] for board corners
     */
    fun detectBoard(bitmap: Bitmap): FloatArray? {
        return try {
            val tensorImage = TensorImage().apply {
                load(bitmap)
            }

            val output = Array(1) { FloatArray(8) }
            interpreter?.run(tensorImage.buffer, output)
            output[0]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Extract board region from bitmap using detected corners
     */
    fun extractBoardRegion(bitmap: Bitmap, corners: FloatArray): Bitmap? {
        return try {
            // Apply perspective transform to extract board
            // This is a simplified version - full implementation would use OpenCV or similar
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd("chess_board_model.tflite")
        val inputStream = fileDescriptor.createInputStream()
        val fileSize = fileDescriptor.declaredLength
        val buffer = ByteArray(fileSize.toInt())
        inputStream.read(buffer)
        inputStream.close()

        val byteBuffer = java.nio.ByteBuffer.allocateDirect(buffer.size)
        byteBuffer.put(buffer)
        byteBuffer.position(0)
        return byteBuffer as MappedByteBuffer
    }

    fun close() {
        interpreter?.close()
    }
}
