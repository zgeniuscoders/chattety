package cd.zgeniuscoders.chattety.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class StoryRepository {
    companion object Singleton {
        const val COLLECTION_NAME = "stories"
        val db = FirebaseFirestore.getInstance()
    }

    private fun getInstance(): FirebaseFirestore {
        return db
    }

    private fun getStoryCollection(): CollectionReference {
        return getInstance().collection(COLLECTION_NAME)
    }

    fun getDocuments(): Task<DocumentSnapshot> {
        return getStoryCollection().document().get()
    }

    fun getDocument(docId: String): Task<DocumentSnapshot> {
        return getStoryCollection().document(docId).get()
    }
}