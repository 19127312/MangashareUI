package com.khtn.mangashare.model

import java.io.Serializable

class transactionItem : Serializable {
    var priceConvert: String = ""
    var price: String = ""

    constructor() {}
    constructor(priceConvert : String, price : String) {
        this.priceConvert = priceConvert
        this.price = price

    }
}