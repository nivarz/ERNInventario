package com.eriknivar.erninventario.inventoryapp.view.inventoryentry

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

val db = FirebaseFirestore.getInstance()

data class Card(val title: String, val description: String, val createdAt: Long)

fun addCard(card: String) {
    db.collection("cards")
        .add(card)
        .addOnSuccessListener { documentReference ->
            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "Error adding document", e)
        }
}
