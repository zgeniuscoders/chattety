package cd.zgeniuscoders.chattety.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cd.zgeniuscoders.chattety.databinding.ItemLayoutStoryBinding
import cd.zgeniuscoders.chattety.models.Story

class StoryAdapter(private var stories: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(binding: ItemLayoutStoryBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemLayoutStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = stories.size
}