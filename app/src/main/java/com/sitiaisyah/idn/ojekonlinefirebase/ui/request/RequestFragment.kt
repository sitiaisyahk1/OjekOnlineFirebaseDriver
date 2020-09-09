package com.sitiaisyah.idn.ojekonlinefirebase.ui.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sitiaisyah.idn.ojekonlinefirebase.R
import com.sitiaisyah.idn.ojekonlinefirebase.ui.request.fragment.CompleteBooking
import com.sitiaisyah.idn.ojekonlinefirebase.ui.request.fragment.ProsesBooking
import com.sitiaisyah.idn.ojekonlinefirebase.ui.request.fragment.RequestBooking
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.request -> {
                setFragment(RequestBooking())
                return@OnNavigationItemSelectedListener true
            }
            R.id.handle -> {
                setFragment(ProsesBooking())
                return@OnNavigationItemSelectedListener true
            }
            R.id.complete -> {
                setFragment(CompleteBooking())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.pager, fragment)?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragment(RequestBooking())
        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

   }