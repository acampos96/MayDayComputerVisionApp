package com.example.maydaykt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions
import com.google.firebase.ml.vision.objects.FirebaseVisionObject
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions
import com.otaliastudios.cameraview.Frame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraView.setLifecycleOwner(this)
        cameraView.addFrameProcessor {
            extractDataFromFrame(it) { result ->
                tvDetectedObject.text = result
            }
        }
    }

    val conditions = FirebaseModelDownloadConditions.Builder()
        .requireWifi()
        .build()
    val remoteModel = FirebaseRemoteModel.Builder("MayDayCVProductsDataSet")
        .enableModelUpdates(true)
        .setInitialDownloadConditions(conditions)
        .setUpdatesDownloadConditions(conditions)
        .build()
    val localModel = FirebaseLocalModel.Builder("MayDayCVProductsDataSet")
        .setAssetFilePath("MayDayCVProductsDataSet/manifest.json")
        .build()

    val y = FirebaseModelManager.getInstance().registerLocalModel(localModel)
    val x = FirebaseModelManager.getInstance().registerRemoteModel(remoteModel)

    private fun extractDataFromFrame(frame: Frame, callback: (String) -> Unit) {

        val options = FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
            .setLocalModelName("MayDayCVProductsDataSet")
            .setRemoteModelName("MayDayCVProductsDataSet")
            .setConfidenceThreshold(0.6F)
            .build()

        val objectDetector = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)

        objectDetector.processImage(getVisionImageFromFrame(frame))
            .addOnSuccessListener {
                var result = ""
                it.forEach { item ->
                    result = "${item.text}\n"  //TODO : Get the knowledge graph result for this entity
                    Log.e("TAG",item.text)
                    id=item.text
                    Log.e("TAG",""+item.confidence)
                }
                callback(result)
            }
            .addOnFailureListener {
                callback("No se ha podido detectar productos")
            }
            .addOnCompleteListener {

            }

    }

    private fun getVisionImageFromFrame(frame : Frame) : FirebaseVisionImage{
        //ByteArray for the captured frame
        val data = frame.data

        //Metadata that gives more information on the image that is to be converted to FirebaseVisionImage
        val imageMetaData = FirebaseVisionImageMetadata.Builder()
            .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
            .setRotation(FirebaseVisionImageMetadata.ROTATION_90)
            .setHeight(frame.size.height)
            .setWidth(frame.size.width)
            .build()

        val image = FirebaseVisionImage.fromByteArray(data, imageMetaData)

        return image
    }
    fun regresar(view: View){
        val intent= Intent(this, Menu::class.java)
        startActivity(intent)
    }
    fun producto(view: View){
        var intent=Intent(this,products::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }
}