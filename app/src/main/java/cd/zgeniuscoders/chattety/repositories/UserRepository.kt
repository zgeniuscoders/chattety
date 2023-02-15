package cd.zgeniuscoders.chattety.repositories

import cd.zgeniuscoders.chattety.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {

    companion object Singleton {
        const val COLLECTION_NAME = "users"
        val db = FirebaseFirestore.getInstance()
    }

    private fun getUserCollection(): CollectionReference {
        return getInstance().collection(COLLECTION_NAME)
    }

    fun getInstance(): FirebaseFirestore {
        return db
    }

    fun createUser(user: User) {
        getUserCollection().document(user.id).set(user)
    }

    fun getCurrentUser(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}