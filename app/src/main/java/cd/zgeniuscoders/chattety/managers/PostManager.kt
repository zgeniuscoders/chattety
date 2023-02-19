package cd.zgeniuscoders.chattety.managers

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import cd.zgeniuscoders.chattety.adapters.PostAdapter
import cd.zgeniuscoders.chattety.models.Post
import cd.zgeniuscoders.chattety.repositories.PostRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject

class PostManager {

    private var postRepository = PostRepository()

    fun docID(): String {
        return postRepository.getInstance().document().id
    }

    fun getPosts(context: Context, recycler: RecyclerView): ListenerRegistration {
        val post = ArrayList<Post>()
        return postRepository.getPosts().addSnapshotListener { querySnap, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            post.clear()
            if (querySnap != null) {
                for (doc in querySnap.documents) {
                    val data = doc.toObject(Post::class.java)
                    post.add(data!!)
                }
            }
            recycler.adapter = PostAdapter(context, post)
        }
    }

    fun addPost(post: Post): Task<Void> {
        return postRepository.addPost(post)
    }

}