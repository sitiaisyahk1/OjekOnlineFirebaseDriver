package com.sitiaisyah.idn.ojekonlinefirebase.ui.request.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sitiaisyah.idn.ojekonlinefirebase.R
import com.sitiaisyah.idn.ojekonlinefirebase.model.Booking
import com.sitiaisyah.idn.ojekonlinefirebase.ui.request.adapter.BookingAdapter
import com.sitiaisyah.idn.ojekonlinefirebase.ui.request.detail.DetailRequest
import com.sitiaisyah.idn.ojekonlinefirebase.utils.Constan
import kotlinx.android.synthetic.main.fragment_item_list.*
import org.jetbrains.anko.support.v4.startActivity
import java.lang.IllegalStateException

class CompleteBooking : Fragment() {

    private var columnCount = 1
    private var listener : RequestBooking.OnlistFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)


        return view
    }

    private fun exPlore(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_booking)
        val data = ArrayList<Booking>()
        val query = myRef.orderByChild("driver").equalTo("")

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (issue in snapshot.children) {
                    val dataFirebase = issue.getValue(Booking::class.java)
                    if (dataFirebase?.status == 4) {
                        val booking = Booking()
                        booking.tanggal = dataFirebase?.tanggal
                        booking.uid = dataFirebase?.uid
                        booking.lokasiAwal = dataFirebase?.lokasiAwal
                        booking.latAwal = dataFirebase?.latAwal
                        booking.lonAwal = dataFirebase?.lonAwal
                        booking.latTujuan = dataFirebase?.latTujuan
                        booking.lonTujuan = dataFirebase?.lonTujuan
                        booking.lokasiTujuan = dataFirebase?.lokasiTujuan
                        booking.jarak = dataFirebase?.jarak
                        booking.harga = dataFirebase?.harga
                        booking.status = dataFirebase?.status

                        data.add(booking)
                        showData(data)

                    }
                }
            }

        })
    }

    private fun showData(data: ArrayList<Booking>) {

        try {
            list.adapter = BookingAdapter(data, object : RequestBooking.OnlistFragmentInteractionListener {
                override fun onlistFragmentInteraction(item: Booking?) {
                    startActivity<DetailRequest>(
                        Constan.booking to item!!,
                        Constan.status to 1
                    )
                }
            })
            list.layoutManager = LinearLayoutManager(context)
        }catch (e : IllegalStateException){

        }
    }

    override fun onDetach(){
        super.onDetach()
        listener = null
    }

    interface OnlistFragmentInteractionListener{
        fun onlistFragmentInteraction(item: Booking?)
    }

}