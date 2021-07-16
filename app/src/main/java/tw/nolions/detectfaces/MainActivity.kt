package tw.nolions.detectfaces

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import tw.nolions.detectfaces.databinding.ActivityMainBinding
import java.util.concurrent.Executor


private const val CAMERA_PERMISSION_REQUEST_CODE = 101
const val TAG: String = "detectfaces"


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding()

        if (checkPermission()) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Camera permission required", Toast.LENGTH_LONG).show()
        }
    }

    private fun viewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        // opening up dialog to ask for camera permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    private fun startCamera() {
        initCamera()
    }

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    private fun initCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val previewUseCase = Preview.Builder().build().also {

                it.setSurfaceProvider(binding.cameraView.surfaceProvider)
            }


            try {
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(
                    Executor { obj: Runnable -> obj.run() },
                    FacesAnalyzer()
                )

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    previewUseCase,
                    imageAnalysis
                )
            } catch (illegalStateException: IllegalStateException) {
                Log.e(
                    TAG,
                    "MainActivity::initCamera, illegalStateException" + illegalStateException.message.orEmpty()
                )
            } catch (illegalArgumentException: IllegalArgumentException) {
                Log.e(
                    TAG,
                    "MainActivity::initCamera, illegalArgumentException" + illegalArgumentException.message.orEmpty()
                )
            }

        }, ContextCompat.getMainExecutor(this))
    }

    fun switchCamera(view: View) {

    }
}