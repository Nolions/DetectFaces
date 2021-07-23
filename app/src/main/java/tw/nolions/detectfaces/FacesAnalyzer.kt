package tw.nolions.detectfaces

import android.graphics.Canvas
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class FacesAnalyzer() : ImageAnalysis.Analyzer {

    private lateinit var faceDetector: FaceDetector
    private lateinit var faceImage: InputImage
    private val canvas = Canvas()

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        faceImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        initDetector()
        detectFaces(imageProxy)
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
                                it.points
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


    private fun drawContours(points: List<FirebaseVisionPoint> ) {

    }
}