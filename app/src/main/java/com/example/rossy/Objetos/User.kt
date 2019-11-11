package com.example.rossy.Objetos

class User {

    lateinit var name: String
    lateinit var lastName: String
    lateinit var email: String

    constructor(){}

    constructor(name:String,lastName: String, email: String) {
        this.name = name
        this.lastName = lastName
        this.email = email
    }

}