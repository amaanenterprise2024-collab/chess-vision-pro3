package com.example.chessvisionpro.vision

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.processor.ImageProcessor
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class BoardDetector(context: Context) {
    private var interpreter: Interpreter? = null
    private var inputSize = 224

    init {
        loadModel(context)
    }

    private fun loadModel(context: Context) {
        try {
            val modelBuffer = loadModelFile(context, "chess_board_detection.tflite")
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
            Log.d("BoardDetector", "Model loaded successfully. Input size: $inputSize")
        } catch (e: Exception) {
            Log.e("BoardDetector", "Error loading model: ${e.message}")
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

    fun detectBoardCorners(bitmap: Bitmap): FloatArray? {
        return try {
            if (interpreter == null) {
                Log.e("BoardDetector", "Interpreter is null")
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
            val output = Array(1) { FloatArray(8) } // 4 corners * 2 coordinates
            interpreter?.run(processedTensorImage.buffer, output)

            output[0]
        } catch (e: Exception) {
            Log.e("BoardDetector", "Error detecting board: ${e.message}")
            null
        }
    }

    fun close() {
        interpreter?.close()
        interpreter = null
    }
}