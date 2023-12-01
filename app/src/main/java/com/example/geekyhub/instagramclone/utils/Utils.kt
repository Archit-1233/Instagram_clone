package com.example.geekyhub.instagramclone.utils

import android.app.ProgressDialog
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*

//ek image aayegi with folder name ki is folder mei upload hona hai
//imageurl mei url aajayega aur fir function return ho jayega
//uska upload karegea aur uska url lega
fun uploadImage(uri: Uri,folderName:String,callback:(String? )->Unit) {

    var imageUrl:String?=null

  FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).
       putFile(uri)
      .addOnSuccessListener {
          it.storage.downloadUrl.addOnSuccessListener {
              imageUrl=it.toString()
              callback(imageUrl)
          }
      }

}

fun uploadVideo(uri: Uri, folderName:String,progressDialog: ProgressDialog, callback:(String? )->Unit) {

    var imageUrl:String?=null
   progressDialog.setTitle("Uploading. . .")
    progressDialog.show()


    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).
    putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl=it.toString()
                progressDialog.dismiss()
                callback(imageUrl)
            }
        }
        .addOnProgressListener {
            var uploadedValue:Long=(it.bytesTransferred/it.totalByteCount)*100
            progressDialog.setMessage("Uploaded $uploadedValue %")
        }

}