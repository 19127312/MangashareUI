package com.khtn.mangashare.model

class ratingItem {
    var name: String = ""
    var star: Int = 0
    var time: String = ""
    var content: String = ""
    constructor() {}
    constructor(name : String, star : Int, time : String, content: String) {
        this.name = name
        this.star = star
        this.content = content
        this.time = time
    }
}