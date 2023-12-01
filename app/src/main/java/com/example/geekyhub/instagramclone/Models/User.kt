package com.example.geekyhub.instagramclone.Models

class User {
    var image:String?=null//here we will store the image link that why string
    var name:String?=null
    var email:String?=null
    var password:String?=null

    constructor() //this is convention we need to make for firebase

    constructor(email: String?,name:String?,password:String?,image:String?) {
        this.email = email
        this.name=name
        this.password=password
        this.image=image
    }
    constructor(email: String?,name:String?,password:String?) {
        this.email = email
        this.name=name
        this.password=password
    }

    constructor(email: String?,password: String?) {
        this.email = email
        this.password=password
    }


}