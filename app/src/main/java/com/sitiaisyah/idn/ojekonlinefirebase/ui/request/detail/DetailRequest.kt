package com.sitiaisyah.idn.ojekonlinefirebase.ui.request.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sitiaisyah.idn.ojekonlinefirebase.MainActivity
import com.sitiaisyah.idn.ojekonlinefirebase.R
import com.sitiaisyah.idn.ojekonlinefirebase.model.Booking
import com.sitiaisyah.idn.ojekonlinefirebase.utils.Constan
import com.sitiaisyah.idn.ojekonlinefirebase.utils.Constan.key
import kotlinx.android.synthetic.main.activity_detail_request.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class DetailRequest : AppCompatActivity(), OnMapReadyCallback {

    var status : Int? = null
    var booking : Booking? = null

    private lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_request)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapsdetail) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var auth = FirebaseAuth.getInstance()

        //menangkap objeck yg dikirim dr masing2 list
        booking = Booking()
        booking = intent.getSerializableExtra(Constan.booking) as Booking
        status = intent.getIntExtra(Constan.status, 0)

        //pindah object ke view
        detail_awal.text = booking?.lokasiAwal
        detail_tujuan.text = booking?.lokasiTujuan
        detail_tanggal.text = booking?.tanggal
        detail_price.text = booking?.harga

        detailBooking()

        detail_button.onClick {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(Constan.tb_booking)

            if (status == 1){
                myRef.child(key).child("status").setValue(2)
                myRef.child(key).child("driver").setValue(auth.currentUser?.uid)

                startActivity<MainActivity>()
            }
            else if (status == 2){
                myRef.child(key).child("status").setValue(4)
                startActivity<MainActivity>()
            }
        }
    }

    private fun detailBooking() {
        if (status == 2){
            detail_button.text = getString(R.string.complete)
        }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_booking)
        var key = ""

        val query = myRef.orderByChild("tanggal").equalTo(booking?.tanggal)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //untuk mengambil key masing2 item
                for (issue in snapshot.children){
                    key = issue.key.toString()
                }
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latlng = booking?.latAwal?.let {
            booking?.lonAwal?.let {
                it1 -> LatLng(it, it1)
            }
        }

        val latlng1 = booking?.latTujuan?.let {
            booking?.lonTujuan?.let { it1 ->
                LatLng(it, it1)
            }
        }
        val res = this.resources
        val marker1 = BitmapFactory.decodeResource(res, R.mipmap.ic_pin)
        val smallmarker = Bitmap.createScaledBitmap(marker1, 80, 120, false)

        mMap.addMarker(latlng?.let {
            MarkerOptions().position(it).title("awal").icon(BitmapDescriptorFactory.fromBitmap(smallmarker))
        })
        mMap.addMarker(latlng?.let {
            MarkerOptions().position(it).title("tujuan").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        })

        //menampilkan seluruh marker
        val builder = LatLngBounds.builder()
        builder.include(latlng)
        builder.include(latlng1)

        mMap.setOnCameraIdleListener { mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 32)) }
    }
}