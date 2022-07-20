package com.khtn.mangashare.model

import java.io.Serializable

class commentItem : Serializable {
    var name: String = ""
    var chapter: Int = 0
    var time: String = ""
    var content: String = ""
    constructor() {}
    constructor(name : String, chapter : Int, time : String, content: String) {
        this.name = name
        this.chapter = chapter
        this.content = content
        this.time = time
    }
}