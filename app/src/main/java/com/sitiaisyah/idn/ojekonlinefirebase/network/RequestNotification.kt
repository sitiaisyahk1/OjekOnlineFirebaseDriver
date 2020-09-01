package com.sitiaisyah.idn.ojekonlinefirebase.network

import com.google.gson.annotations.SerializedName
import com.sitiaisyah.idn.ojekonlinefirebase.model.Booking

class RequestNotification {

    @SerializedName("to")
    var token: String? = null

    @SerializedName("data")
    var sendNotificationModel: Booking? = null
}