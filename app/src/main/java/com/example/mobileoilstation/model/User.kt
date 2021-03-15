package com.example.mobileoilstation.model

import java.sql.Date

data class User (val email: String,
                 val phone : String,
                 val name : String,
                 val pass : String,
                 val created: Date?,
                 val firstname: String?,
                 val lastname: String?,
                 val photo: String?)