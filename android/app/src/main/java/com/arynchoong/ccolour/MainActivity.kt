package com.arynchoong.ccolour

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.Bitmap.*
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LifecycleOwner
import androidx.palette.graphics.Palette
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.Executors


private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewFinder = findViewById(R.id.view_finder)
        textOverlay = findViewById(R.id.text_overlay)
        fab = findViewById(R.id.fab)

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
        // Setup ViewFinder onTouchListener
        viewFinder.setOnTouchListener { v, event ->
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                // Set textOverlay top and left
                lastTouchDownXY[0] = event.getX()
                lastTouchDownXY[1] = event.getY()
                textOverlay.x = lastTouchDownXY[0]
                textOverlay.y = lastTouchDownXY[1]
                if (fab.visibility != TextView.VISIBLE) {
                    textOverlay.visibility = TextView.VISIBLE
                    fab.visibility = TextView.VISIBLE
                }
            }
            false
        }
        fab.setOnClickListener {
            this.setVisible(false)
            textOverlay.visibility = TextView.GONE
            stateFlag = STATE.COLOUR
            viewFinder.post { startCamera() }
        }
    }

    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var viewFinder: TextureView
    private lateinit var textOverlay: TextView
    private lateinit var fab: FloatingActionButton
    var lastTouchDownXY = FloatArray(2)
    var imgCoords = FloatArray(2)
    lateinit var imageAnalyse: Bitmap
    var stateFlag = STATE.COLOUR

    private fun startCamera() {
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {
            if (stateFlag!=STATE.INSPECT){
                // To update the SurfaceTexture, we have to remove it and re-add it
                val parent = viewFinder.parent as ViewGroup
                parent.removeView(viewFinder)
                parent.addView(viewFinder, 0)

                viewFinder.surfaceTexture = it.surfaceTexture
                updateTransform()
            }
        }

        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)

        viewFinder.setOnClickListener{
            if(stateFlag == STATE.COLOUR) {
                imageCapture.takePicture(executor,
                    object : ImageCapture.OnImageCapturedListener() {
                        override fun onError(
                            imageCaptureError: ImageCapture.ImageCaptureError,
                            message: String,
                            exc: Throwable?
                        ) {
                            val msg = "Photo capture failed: $message"
                            Log.e("CColour", msg, exc)
                            viewFinder.post {
                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onCaptureSuccess(
                            image: ImageProxy,
                            rotationDegrees: Int
                        ) {
                            setImageAnalyse(image.image)

                            imgCoords[0] =
                                imageAnalyse.width / viewFinder.width * lastTouchDownXY[0]
                            imgCoords[1] =
                                imageAnalyse.height / viewFinder.height * lastTouchDownXY[1]
                            var msg =
                                "touchCoords: " + lastTouchDownXY[0].toString() + " " + lastTouchDownXY[1].toString()
                            Log.d("CColour", msg)
                            msg =
                                "imgCoords: " + imgCoords[0].toString() + " " + imgCoords[1].toString()
                            Log.d("CColour", msg)

                            image.close()
                        }
                    }
                )
                CameraX.unbindAll()
                stateFlag = STATE.INSPECT
                analyse(imageAnalyse)
            } else {
                imgCoords[0] =
                    imageAnalyse.width / viewFinder.width * lastTouchDownXY[0]
                imgCoords[1] =
                    imageAnalyse.height / viewFinder.height * lastTouchDownXY[1]
                var msg =
                    "touchCoords: " + lastTouchDownXY[0].toString() + " " + lastTouchDownXY[1].toString()
                Log.d("CColour", msg)
                msg =
                    "imgCoords: " + imgCoords[0].toString() + " " + imgCoords[1].toString()
                Log.d("CColour", msg)

                analyse(imageAnalyse)
            }

        }

        // Bind use cases to lifecycle
        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun setImageAnalyse(image: Image?) {
        if (image != null) {
            // convert image
            if ((image.format == ImageFormat.YUV_420_888) ||
                (image.format == ImageFormat.YUV_444_888) ||
                (image.format == ImageFormat.YUV_422_888)
            ) {
                Log.d("ImageAnalyser", "YUV")
                imageAnalyse = image.YUVtoBitmap()
            } else {
                imageAnalyse = image.JPEGtoBitmap()
            }
        }
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        viewFinder.setTransform(matrix)
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    // Get colour
    private fun analyse(image: Bitmap) {
        // Get region of interest
        val w = 20
        val x: Int = if (imgCoords[0] < w) imgCoords[0].toInt() else  (imgCoords[0] - (w/2)).toInt()
        val y: Int = if (imgCoords[1] < w) imgCoords[1].toInt() else   imgCoords[1].toInt() - (w/2)

        Log.d("ImageAnalyser", "left: $x top: $y width: $w")
        val bmpImage = createBitmap(image, x, y, w, w)
        // Update text
        setTextColorForImage(bmpImage)
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

