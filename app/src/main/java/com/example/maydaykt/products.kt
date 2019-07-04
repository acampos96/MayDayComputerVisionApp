package com.example.maydaykt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_products.*

class products : AppCompatActivity() {
    val db=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        var bundle :Bundle ?=intent.extras
        var id = bundle!!.getString("id")
        var productos=db.collection("Products")
        var item=productos.whereEqualTo("ID",id).get()
        productID.setText(id)
    }

    fun back(view: View){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}



