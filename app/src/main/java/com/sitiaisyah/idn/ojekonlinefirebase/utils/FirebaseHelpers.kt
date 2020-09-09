package com.sitiaisyah.idn.ojekonlinefirebase.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.sitiaisyah.idn.ojekonlinefirebase.model.Users

//proses pengambilan token dari firebase
object FirebaseHelper {

    private var auth: FirebaseAuth? = null

    fun insertUser(
        name: String, email: String,
        hp: String, uid: String
    ): Boolean {

        val uid = auth?.currentUser?.uid
        val token = FirebaseInstanceId.getInstance().token

        var user = Users()
        user.email = email
        user.name = name
        user.hp = hp
        user.uid = uid
        user.latitude = "0.0"
        user.longitude = "0.0"
        user.token = token
        val database = FirebaseDatabase.getInstance()
        var key = database.reference.push().key
        var myref = database.getReference(Constan.tb_uaser)

        key?.let { myref.child(it).setValue(user)}
        return true
    }
}