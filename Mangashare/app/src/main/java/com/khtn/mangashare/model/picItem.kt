package com.khtn.mangashare.model

import android.net.Uri
import java.io.Serializable

class picItem : Serializable {
    var imgURI: Uri?=null


    constructor(){}
    constructor( imgURI: Uri){
        this.imgURI=imgURI
    }

}