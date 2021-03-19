package com.example.mobileoilstation.model

import java.sql.Date

data class User (val email: String,
                 val phone : String? = null,
                 val name : String? = null,
                 val password : String,
                 val created: Date? = null,
                 val firstname: String? = null,
                 val lastname: String? = null,
                 val photo: String? = null)