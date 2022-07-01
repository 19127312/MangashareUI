package com.khtn.mangashare.model

import java.io.Serializable

class chapterItem : Serializable {

    var number: Int? = 0
    var datePost: String? = ""
    var price: Int? = 0
    var bookmark: Boolean = false
    var viewNumber: Int? = 0

    constructor() {}

    constructor(number: Int, datePost: String, price: Int) {
        this.number = number
        this.datePost = datePost
        this.price = price

    }


    constructor(number: Int, datePost: String) {
        this.number = number
        this.datePost = datePost
    }

    constructor(number: Int, datePost: String, price: Int, bookmark: Boolean, view : Int) {
        this.number = number
        this.datePost = datePost
        this.price = price
        this.bookmark = bookmark
        this.viewNumber = view
    }

}