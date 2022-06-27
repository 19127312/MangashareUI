package com.khtn.mangashare.model

import java.io.Serializable

class comicItem : Serializable {
    var thumbnail: String? = ""
    var name: String? = ""
    var id: String? = ""
    var description: String? = ""
    var author: String? = ""
    var lastestChapter: Int? = 1
    var likeNumber: Int? = 0
    var updatedTime: Long? = 0
    var lastSeenChapter = 0
    var lastDateSeen = ""
    var totalChapter = 0
    var cover = 0
    var status = "Censored"

    constructor() {}
    constructor(cover: Int, name: String, total: Int, lastSeen: Int, lastDate: String) {
        this.name = name
        this.totalChapter = total
        this.lastDateSeen = lastDate
        this.cover = cover
        this.lastSeenChapter = lastSeen

    }

    constructor(cover: Int, name: String, total: Int, status: String) {
        this.name = name
        this.totalChapter = total
        this.status = status
        this.cover = cover

    }

    constructor(
        name: String?,
        thumbnail: String?,
        id: String?,
        description: String?,
        author: String?,
        lastestChapter: Int?,
        likeNumber: Int?,
        updatedTime: Long?
    ) {
        this.name = name
        this.thumbnail = thumbnail
        this.id = id
        this.description = description
        this.author = author
        this.lastestChapter = lastestChapter
        this.likeNumber = likeNumber
        this.updatedTime = updatedTime
    }

    constructor(name: String?, cover: Int, totalChapter: Int) {
        this.name = name
        this.cover = cover
        this.totalChapter = totalChapter
    }
}