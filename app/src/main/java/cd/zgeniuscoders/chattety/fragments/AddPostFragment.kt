package cd.zgeniuscoders.chattety.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import cd.zgeniuscoders.chattety.databinding.FragmentAddPostBinding
import cd.zgeniuscoders.chattety.managers.PostManager
import cd.zgeniuscoders.chattety.models.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddPostFragment : Fragment() {
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var postManager: PostManager
    var postImg: Uri? = null
    var postImgUrl: String? = ""

    private var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            postImg = it.data!!.data
            binding.cardPostView.visibility = View.VISIBLE
            binding.postImage.setImageURI(postImg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(layoutInflater)

        postManager = PostManager()

        val grid = GridLayoutManager(requireContext(), 3)
        getPosts()

        binding.postRecycler.layoutManager = grid
        postManager.getPosts(requireContext(), binding.postRecycler)

        binding.addPost.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryActivity.launch(intent)
        }

        binding.postAdd.setOnClickListener {
            uploadImage()
        }

        binding.postImage.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            launchGalleryActivity.launch(intent)
        }

        binding.postCancel.setOnClickListener {
            postImg = null
            postImgUrl = null
            binding.cardPostView.visibility = View.GONE
        }

        return binding.root
    }

    private fun getPosts() {
        postManager.getPosts(requireContext(), binding.postRecycler)
    }

    @SuppressLint("SimpleDateFormat")
    private fun addPost() {
        val docId = postManager.docID()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val currentDate = Calendar.getInstance().time
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate)

        val post = Post(docId, userId, postImgUrl.toString(), date)
        postManager.addPost(post)
        Toast.makeText(requireContext(), "Poster", Toast.LENGTH_LONG).show()
    }

    private fun uploadImage() {

        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = FirebaseStorage
            .getInstance()
            .reference
            .child("posts/$fileName")

        refStorage.putFile(postImg!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image ->
                    postImgUrl = image.toString()
                    addPost()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }


    }

}