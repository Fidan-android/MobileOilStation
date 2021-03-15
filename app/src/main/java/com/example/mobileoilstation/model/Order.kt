package com.example.mobileoilstation.model

import java.sql.Date

data class Order(val car_number: Int,
                 val type_oil: String,
                 val amount_money: Float,
                 val location: String,
                 val amount_oil: Float,
                 val date: Date?)
