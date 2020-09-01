package com.sitiaisyah.idn.ojekonlinefirebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.sitiaisyah.idn.ojekonlinefirebase.MainActivity
import com.sitiaisyah.idn.ojekonlinefirebase.R
import com.sitiaisyah.idn.ojekonlinefirebase.utils.Constan
import kotlinx.android.synthetic.main.activity_authentication_hp.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AuthenticationHpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_hp)

        //todo 13
        val key = intent.getStringExtra(Constan.key)
        val database = FirebaseDatabase.getInstance()
        val myref = database.getReference(Constan.tb_uaser)

        //update realtim database
        authentikasiSubmit.onClick {
            if (authentikasiNomerHp.text.toString().isNotEmpty()){
                myref.child(key!!).child("hp")
                    .setValue(authentikasiNomerHp.text.toString())
                startActivity<MainActivity>()
            }

            else toast("tidak boleh kosong")
        }
    }
}