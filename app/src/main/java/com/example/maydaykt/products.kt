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

        for (item: producto in productos) {
            if (producto.id())
        }
//        var productos=db.collection("Products")
//        var item=productos.whereEqualTo("ID",id).get()
//        productID.setText(id)

//        val docRef = db.collection("Products").whereEqualTo("ID", id).get().addOnSuccessListener { document ->
//            var nombre = document.data.
//            productID.setText(nombre)
//        }
    }

    fun back(view: View){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    data class producto(
        var id: String ? = null, var descripcion: String ? = null, var rating : Float ? = null, var cantidad: Float ? = null, var img: String ? = null
    ){
        
    }
    var productos: Array<producto> = arrayOf(producto( "Coca_Cola", "Bebida carbonatada sabor a cola",4.5F, 5.0F), producto( "Pepsi", "Bebida carbonatada sabor a cola",5.0F, 4.5F), producto( "Diana", "Churros y dulces",3.75F, 5.0F), producto( "Dany", "Variedad de productos desde alimentos hasta empaques",4.5F, 5.0F))

}



