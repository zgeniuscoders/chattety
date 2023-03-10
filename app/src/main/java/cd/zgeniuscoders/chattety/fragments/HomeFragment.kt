package cd.zgeniuscoders.chattety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cd.zgeniuscoders.chattety.R
import cd.zgeniuscoders.chattety.databinding.FragmentHomeBinding
import cd.zgeniuscoders.chattety.managers.PostManager

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var postManager: PostManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        postManager = PostManager()

        postManager.getPosts(requireContext(),binding.recyclerPosts)
        // Inflate the layout for this fragment
        return binding.root
    }
}