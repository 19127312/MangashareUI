package com.khtn.mangashare.model

import android.net.Uri
import java.io.Serializable

class categoryItem : Serializable {
    var name: String = ""
    var color: Int = 0
    var imgResource: Int = 0

    constructor() {}
    constructor(name : String, color : Int, image : Int) {
        this.name = name
        this.color = color
        this.imgResource = image
    }
}
