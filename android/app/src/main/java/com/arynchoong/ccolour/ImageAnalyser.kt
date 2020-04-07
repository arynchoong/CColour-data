package com.arynchoong.ccolour

import android.graphics.*
import android.media.Image
import android.util.Log
import android.util.Size
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


class ImageAnalyser(
    var textOverlay: TextView,
    var previewSize: Size
) : ImageAnalysis.Analyzer {
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

             analyse(image, rotationDegrees)

            // Update timestamp of last analyzed frame
            lastAnalyzedTimestamp = currentTimestamp
        }
    }

    fun analyse(image: ImageProxy, rotationDegrees: Int) {

        // Get region of interest
        var textOverlayXY : IntArray = intArrayOf(1,1)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            textOverlay.getLocationInSurface(textOverlayXY)
        } else{
            textOverlay.getLocationOnScreen(textOverlayXY)
        }
        val x1 = textOverlayXY[0]
        val y1 = textOverlayXY[1]

        Log.d("ImageAnalyser", "rotationDegrees: $rotationDegrees")
        var x = 0
        var y = 0
        if ((rotationDegrees == 90) ) {
            x = ((image.width * y1) / previewSize.height)
            y = image.height - (image.height * x1) / previewSize.width
        } else if (rotationDegrees == 180) {
            x = image.width - ((image.width * x1) / previewSize.width)
            y = image.height - ((image.width * y1) / previewSize.width)
        } else if (rotationDegrees == 270) {
            x = image.width - ((image.width * y1) / previewSize.height)
            y = (image.height * x1) / previewSize.width
        } else {
            x = (image.width * x1) / previewSize.width
            y = (image.width * y1) / previewSize.width
        }
        val w = 24
        val h = 24
        //width: 640,1080height: 480,1798,57
        Log.d("ImageAnalyser", "x: $x, $x1 y: $y, $y1 w: " + previewSize.width.toString() + "," + image.width.toString() + " h: " + previewSize.height.toString() + "," + image.height.toString())
        val rect = Rect(x, y, x+w, y+h) // left, top, right, bottom


        if(image.format == ImageFormat.YUV_420_888) {
            val bmpImage = image.image?.YUVtoBitmap(rect)
            bmpImage?.let { setTextColorForImage(it) }
        } else {
            val bmpImage = image.image?.JPEGtoBitmap()
            bmpImage?.let { setTextColorForImage(it) }
        }
    }

    private fun Image.YUVtoBitmap(rect: Rect): Bitmap {

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

        try {
            yuvImage.compressToJpeg( rect, 100, out)
        } catch (e: IllegalArgumentException) {
            Log.d("ImageAnalyser", "rect l:" + rect.left.toString() +" t:"+ rect.top.toString() + " r:"+rect.right.toString()+" b:"+ rect.bottom.toString()
                    + " , w:" + yuvImage.width.toString() + " h:" + yuvImage.height.toString())
            // analyse whole image
            centerText()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        }
        if(out.size() <= 0) {
            // analyse whole image
            centerText()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        }

        Log.d("YUVtoBitmap", "out: " + out.size().toString() )
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun Image.JPEGtoBitmap(): Bitmap {
        Log.d("ImageAnalyser", "JPEGtoBitmap")
        val buffer: ByteBuffer = planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun centerText() {
        textOverlay.x = ((previewSize.width - textOverlay.width) / 2).toFloat()
        textOverlay.y = (previewSize.height - textOverlay.height).toFloat()
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
                //var nameColour = colour.getColorNameFromRgb(swatch.rgb.red,swatch.rgb.green,swatch.rgb.blue)
                //val nameColour = colour.getColorNameFromHsl(swatch?.hsl[0], swatch?.hsl[1], swatch?.hsl[2])
                val nameColour = colour.getColourName(bgColor.red, bgColor.green, bgColor.blue, swatch?.hsl)
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

