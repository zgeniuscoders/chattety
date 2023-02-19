package cd.zgeniuscoders.chattety.repositories

import android.util.Log
import cd.zgeniuscoders.chattety.models.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostRepository {
    companion object Singleton {
        const val COLLECTION_NAME = "posts"
        val db = Firebase.firestore.collection(COLLECTION_NAME)
    }

    fun getInstance(): CollectionReference {
        return db
    }

    fun getPosts(): CollectionReference {
        return getInstance()
    }

    fun getPost(postId: String): DocumentReference {
        return getInstance().document(postId)
    }

    fun addPost(post: Post): Task<Void> {
        return getInstance().document(post.id).set(post)
    }

    fun removePost(docId: String) {
        getInstance().document().delete()
    }

}