package com.arynchoong.ccolour

import android.graphics.*
import android.media.Image
import android.util.Log
import android.widget.TextView
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.palette.graphics.Palette
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit


class ImageAnalyser(private val textOverlay: TextView) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimestamp = 0L

    /**
     * Helper extension function used to extract a byte array from an
     * image plane buffer
     */
    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy, rotationDegrees: Int) {
        val currentTimestamp = System.currentTimeMillis()
        // Calculate the average luma no more often than every second
        if (currentTimestamp - lastAnalyzedTimestamp >=
            TimeUnit.SECONDS.toMillis(1)) {
            // Get region of interest
            val x1: Int = textOverlay.left
            val y1: Int = textOverlay.top
            val w: Int = textOverlay.width
            val h: Int = textOverlay.height

            val x: Int = (image.width - w) / 2
            val y: Int = (image.height - h) / 2

            val rect = Rect(x, y, w, h)
            Log.d("ImageAnalyser", "left: $x1,$x top: $y1,$y width: $w height $h")
            image.cropRect = rect
            val roiImage = image.image

            // Update text
            if (roiImage != null) {
                if ((roiImage.format == ImageFormat.YUV_420_888) ||
                    (roiImage.format == ImageFormat.YUV_444_888) ||
                    (roiImage.format == ImageFormat.YUV_422_888)) {
                    Log.d("ImageAnalyser", "YUV")
                    val bmpImage = roiImage.YUVtoBitmap()
                    setTextColorForImage(bmpImage)
                } else {
                    val bmpImage = roiImage.JPEGtoBitmap()
                    setTextColorForImage(bmpImage)
                }
            } else {
                textOverlay.visibility = TextView.GONE
            }

            // Update timestamp of last analyzed frame
            lastAnalyzedTimestamp = currentTimestamp
        }
    }

    private fun Image.YUVtoBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val uBuffer = planes[1].buffer // U
        val vBuffer = planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun Image.JPEGtoBitmap(): Bitmap {
        val buffer: ByteBuffer = planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    val colour = ColourAnalyser()

    private fun setTextColorForImage(bitmapImage: Bitmap) {
        Palette.from(bitmapImage).generate { palette ->
            var swatch = palette?.dominantSwatch
            if (swatch == null && (palette?.getSwatches()?.size!! > 0)) {
                swatch = palette?.getSwatches()?.get(0)
            }

            if (swatch != null) {
                var textColor = swatch.getTitleTextColor()
                var bgColor = swatch?.rgb
                var nameColour = colour.getColorNameFromRgb(swatch.rgb.red,swatch.rgb.green,swatch.rgb.blue)

                Log.d("ImageAnalyser", "Text Color: $textColor, Bg Color: $bgColor, Colour: $nameColour")
                textOverlay.setTextColor(textColor)
                textOverlay.setBackgroundColor(bgColor)
                textOverlay.setText(nameColour)
                textOverlay.visibility = TextView.VISIBLE
            } else {
                textOverlay.visibility = TextView.GONE
            }
        }
    }

}