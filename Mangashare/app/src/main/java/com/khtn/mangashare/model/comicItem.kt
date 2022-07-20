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
    var viewNumber: Int? = 0
    var reviewNumber: Int? = 0
    var followNumber: Int? = 0
    var updatedTime: Long? = 0
    var lastSeenChapter = 0
    var lastDateSeen = ""
    var totalChapter = 0
    var cover = 0
    var status = "Censored"
    var completeStatus = false
    var category = arrayListOf<String>()
    var reporter = ""
    var contextReport = ""
    var replyReport = ""
    var chapter = arrayListOf<chapterItem>()
    var comment = arrayListOf<commentItem>()

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
        cover: Int,
        name: String,
        author: String,
        reporter: String,
        status: String,
        reply: String,
        context: String
    ) {
        this.name = name
        this.reporter = reporter
        this.status = status
        this.cover = cover
        this.contextReport = context
        this.replyReport = reply
        this.author = author
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

    constructor(name: String?, cover: Int, author: String?, lastDate: String) {
        this.name = name
        this.cover = cover
        this.author = author
        this.lastDateSeen = lastDate
    }

    constructor(
        name: String?,
        cover: Int,
        totalChapter: Int,
        view: Int,
        like: Int,
        description: String?,
        completeStatus: Boolean,
        category: ArrayList<String>
    ) {
        this.name = name
        this.cover = cover
        this.totalChapter = totalChapter
        this.viewNumber = view
        this.likeNumber = like
        this.description = description
        this.completeStatus = completeStatus
        this.category = category
    }

    constructor(
        name: String?,
        cover: Int,
        author: String?,
        totalChapter: Int,
        view: Int,
        like: Int,
        review: Int,
        follow: Int,
        description: String?,
        completeStatus: Boolean,
        category: ArrayList<String>,
        chapter: ArrayList<chapterItem>,
        comment: ArrayList<commentItem>
    ) {
        this.name = name
        this.author = author
        this.cover = cover
        this.totalChapter = totalChapter
        this.viewNumber = view
        this.likeNumber = like
        this.reviewNumber = review
        this.followNumber = follow
        this.description = description
        this.completeStatus = completeStatus
        this.category = category
        this.chapter = chapter
        this.comment = comment
    }

    constructor (temp: comicItem) {
        this.name = temp.name
        this.totalChapter = temp.totalChapter
        this.status = temp.status
        this.cover = temp.cover

    }
}