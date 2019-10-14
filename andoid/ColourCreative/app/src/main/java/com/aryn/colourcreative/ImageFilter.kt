package com.aryn.colourcreative

import android.media.Image
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer

enum class FilterType {
    UNSELECTED,
    PROTANOPIA,
    DEUTERANOPIA,
    TRITANOPIA,
    ACHROMATOPSIA,
    TRICROME,
    DICHROME_RG,
    DICHROME_B,
    MONOCHROME
}

/**
 * Applies filter to preview [Image]
 */
internal class ImageFilter(
        /**
         * The JPEG image
         */
        private val image: Image,

        /**
         * The file we save the image into.
         */
        private val file: File
) : Runnable {

    override fun run() {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

    }

    companion object {
        /**
         * Tag for the [Log].
         */
        private val TAG = "ImageFilter"
    }
}
