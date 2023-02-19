package cd.zgeniuscoders.chattety.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cd.zgeniuscoders.chattety.databinding.ItemLayoutPostBinding
import cd.zgeniuscoders.chattety.models.Post
import com.bumptech.glide.Glide

class PostAdapter(val context: Context,private val posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(binding: ItemLayoutPostBinding) : ViewHolder(binding.root) {
        val binding = ItemLayoutPostBinding.bind(binding.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemLayoutPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        Glide.with(context).load(posts[position].image)
            .into(holder.binding.postImage)
    }

    override fun getItemCount(): Int = posts.size

}