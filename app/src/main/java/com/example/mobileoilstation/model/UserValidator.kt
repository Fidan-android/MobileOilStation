package com.example.mobileoilstation.model

class UserValidator(private val user : User) {

    private var errorList : MutableMap<String, String> = mutableMapOf();

    fun validate() : MutableMap<String, String> {
        if (user.email == ""){
            errorList.put("", "");
        }
        return errorList;
    }
}