package tw.nolions.detectfaces

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
                Log.d(TAG, "FacesAnalyzer::detectFaces Success, result: ${result}")
            }
            .addOnFailureListener {
                Log.e(TAG, "FacesAnalyzer::detectFaces Fail, error: ${it.message}")
            }.addOnCompleteListener {
                imageProxy.image?.close()
                imageProxy.close()
            }
    }
}