package cd.zgeniuscoders.chattety.managers

import cd.zgeniuscoders.chattety.models.User
import cd.zgeniuscoders.chattety.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestore

class UserManager {

    private val userRepository = UserRepository()

    fun getInstance(): FirebaseFirestore {
        return userRepository.getInstance()
    }

    fun createUser(user: User) {
        userRepository.createUser(user)
    }

    fun getCurrentUser(): String {
        return userRepository.getCurrentUser()
    }

    fun upDateUser(id: String, data: HashMap<String, Any>) {
        userRepository.upDateUser(id, data)
    }

    fun deleteUser(id: String) {
        userRepository.deleteUser(id)
    }

}