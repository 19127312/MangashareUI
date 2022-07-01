package com.khtn.mangashare.model

import java.io.Serializable

class chapterItem : Serializable {

    var number: Int? = 0
    var datePost: String? = ""
    var price: Int? = 0



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


}