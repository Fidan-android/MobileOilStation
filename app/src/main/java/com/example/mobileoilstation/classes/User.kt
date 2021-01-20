package com.example.mobileoilstation.classes

class User constructor(email: String, phone : String, nickname : String, pass : String, doublePass : String){
    //fields user
    private val email : String = email;
    private val phone : String = phone;
    private val nickname : String = nickname;
    private val pass : String = pass;
    private val doublePass : String = doublePass;

    //list errors for returning
    private var errors : ArrayList<String> = ArrayList();

    //checking all fields and return errors
    fun checkUser(){
    }
}