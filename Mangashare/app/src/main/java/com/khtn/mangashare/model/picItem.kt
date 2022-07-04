package com.khtn.mangashare.model

import android.net.Uri
import java.io.Serializable

class picItem : Serializable {
    var imgURI: Uri?=null
    var check: Boolean=false
    var imgResource: Int=0
    constructor(){}
    constructor( imgURI: Uri){
        this.imgURI=imgURI
        this.check=false
        this.imgResource=0
    }
    constructor( imgResource: Int){
        this.imgResource=imgResource
        this.check=false
        this.imgURI=null
    }

}