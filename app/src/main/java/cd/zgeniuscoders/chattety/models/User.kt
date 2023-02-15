package cd.zgeniuscoders.chattety.models

data class User(
    val id: String,
    val username: String,
    val email: String,
    val profile: String = "",
    val birthday: String,
    val gender: String,
    val passions: ArrayList<String>? = null,
    val agePreference: String = "",
    val peoplePreference: String = ""
)