package com.example.rossy.Objetos

import kotlin.properties.Delegates

class Table {
    lateinit var name: String
    lateinit var area: String
    var capacity: Int by Delegates.notNull()

    constructor(){}

    constructor(name:String,area: String, capacity: Int) {
        this.name = name
        this.area = area
        this.capacity = capacity
    }
}