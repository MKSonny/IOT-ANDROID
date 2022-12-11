package com.example.iot_pbl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iot_pbl.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//data class Item(
//    val temp: String,
//    val humid: String,
//    val soil: String,
//    val light: String
//)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db: FirebaseFirestore = Firebase.firestore

        db.collection("node1").addSnapshotListener { snapshot, error ->
            for (doc in snapshot!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        val document = doc.document
                        val humid = document["humidity"] as String
                        val lighting = document["lighting"] as String
                        val soil = document["soil"] as String
                        val temperature = document["temperature"] as String

                        binding.humid.text = humid
                        binding.temp.text = temperature
                        binding.light.text = lighting
                        binding.soil.text = soil
                    }
                    else -> {}
                }
            }
        }
        db.collection("node2").addSnapshotListener { snapshot, error ->
            for (doc in snapshot!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        val document = doc.document
                        val humid = document["humidity"] as String
                        val lighting = document["lighting"] as String
                        val soil = document["soil"] as String
                        val temperature = document["temperature"] as String

                        binding.humid2.text = humid
                        binding.temp2.text = temperature
                        binding.light2.text = lighting
                        binding.soil2.text = soil
                    }
                    else -> {}
                }
            }
        }

        db.collection("command").addSnapshotListener { snapshot, error ->
            for (doc in snapshot!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.MODIFIED -> {
                        val document = doc.document
                        val temp = document["fan"] as String
                        val waterpump = document["waterpump"] as String

                        binding.fanEdit.setText(temp)
                        binding.soilEdit.setText(waterpump)
                    }
                    DocumentChange.Type.ADDED -> {
                        val document = doc.document
                        val temp = document["fan"] as String
                        val waterpump = document["waterpump"] as String

                        binding.fanEdit.setText(temp)
                        binding.soilEdit.setText(waterpump)
                    }
                    else -> {}
                }
            }
        }



        binding.button3.setOnClickListener {
            val itemMap = hashMapOf(
                "fan" to binding.fanEdit.text.toString(),
                "waterpump" to binding.soilEdit.text.toString()
            )

            db.collection("command").document("command").set(itemMap).addOnSuccessListener {

            }
        }

    }
}