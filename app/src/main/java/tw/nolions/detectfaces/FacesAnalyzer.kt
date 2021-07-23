package tw.nolions.detectfaces

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import android.view.TextureView
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class FacesAnalyzer() : ImageAnalysis.Analyzer {

    private lateinit var faceDetector: FaceDetector
    private lateinit var faceImage: InputImage
    private var canvas:Canvas? = null
    private var widthScaleFactor = 1.0f
    private var heightScaleFactor = 1.0f
    private val dotPaint: Paint? = null
    private var linePaint:Paint? = null
    private val bitmap: Bitmap? = null
    private val tv: TextureView? = null

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        faceImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        initDetector()
        initDrawingUtils()
        detectFaces(imageProxy)
    }

    private fun initDrawingUtils() {
        canvas = Canvas()
        linePaint = Paint()
//        widthScaleFactor = canvas.width / (fbImage.getBitmap().getWidth() * 1.0f)
//        heightScaleFactor = canvas.height / (fbImage.getBitmap().getHeight() * 1.0f)
    }

    private fun initDetector() {
        val detectorOptions = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()

        faceDetector = FaceDetection.getClient(detectorOptions)
    }

    private fun detectFaces(imageProxy: ImageProxy) {
        faceDetector.process(faceImage)
            .addOnSuccessListener { result ->
                Log.d(TAG, "FacesAnalyzer::detectFaces, result: ${result}")
                if (result != null) {
                    result.forEach { item ->
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::boundingBox: ${item.boundingBox}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::trackingId: ${item.trackingId}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::rightEyeOpenProbability: ${item.rightEyeOpenProbability}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::leftEyeOpenProbability: ${item.leftEyeOpenProbability}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::headEulerAngleX: ${item.headEulerAngleX}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::headEulerAngleY: ${item.headEulerAngleY}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::headEulerAngleZ: ${item.headEulerAngleZ}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::smilingProbability: ${item.smilingProbability}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::allLandmarks: ${item.allLandmarks}"
                        )
                        Log.d(
                            TAG,
                            "FacesAnalyzer::detectFaces, face::allContours: ${item.allContours}"
                        )


                        item.allContours.forEach { face ->
                            face.points
                            face?.let {
                                drawContours(it.points)
                            }
                        }
                    }
                }

            }
            .addOnFailureListener {
                Log.e(TAG, "FacesAnalyzer::detectFaces Fail, error: ${it.message}")
            }.addOnCompleteListener {
                imageProxy.image?.close()
                imageProxy.close()
            }
    }


    private fun drawContours(points: List<PointF>) {

        for ((counter, point) in points.withIndex()) {
            if (counter != points.size - 1) {
                canvas.drawLine(
                    translateX(point.x),
                    translateY(point.y),
                    translateX(points[counter + 1].x),
                    translateY(points[counter + 1].y),
                    linePaint
                )
            } else {
                canvas.drawLine(
                    translateX(point.getX()),
                    translateY(point.getY()),
                    translateX(points[0].getX()),
                    translateY(points[0].getY()),
                    linePaint
                )

            }
            counter++
            canvas.drawCircle(translateX(point.getX()), translateY(point.getY()), 6f, dotPaint)
            canvas.drawCircle(translateX(point.getX(), translateY(point.getY()), 6, dotPaint)
        }
    }

    private fun translateX(x: Float): Float {
        val scaledX: Float = x * widthScaleFactor
        return canvas.width - scaledX
    }

    private fun translateY(y: Float): Float {
        return y * heightScaleFactor
    }
}