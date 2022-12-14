package cz.cvut.fit.adametim.spacex_crew.features.crew.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cz.cvut.fit.adametim.spacex_crew.databinding.CrewMemberItemBinding
import cz.cvut.fit.adametim.spacex_crew.features.crew.domain.CrewMember

class CrewAdapter() : ListAdapter<CrewMember, CrewAdapter.CrewMemberHolder>(CrewDiffCallback()) {

    /*init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }*/

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewMemberHolder {
        val binding = CrewMemberItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CrewMemberHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewMemberHolder, position: Int) {
        val crewMember = getItem(position)
        holder.bind(crewMember)
    }

    class CrewMemberHolder(private val binding: CrewMemberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crewMember: CrewMember) {
            binding.txtName.text = crewMember.name
            binding.txtStatus.text = crewMember.status
            if (binding.txtStatus.text.isNotEmpty()) {
                binding.txtStatus.text = binding.txtStatus.text[0].uppercaseChar()
                    .toString() + binding.txtStatus.text.substring(1, binding.txtStatus.text.length)
            }
            binding.txtAgency.text = crewMember.agency
            binding.txtWikipedia.text = crewMember.wikipedia
            binding.txtNumberOfLaunches.text = crewMember.numberOfLaunches.toString()
            Glide.with(binding.root)
                .load(crewMember.imageUrl)
                .into(binding.imgAvatar)
        }
    }

    private class CrewDiffCallback : DiffUtil.ItemCallback<CrewMember>() {

        override fun areItemsTheSame(oldItem: CrewMember, newItem: CrewMember): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CrewMember, newItem: CrewMember): Boolean {
            return oldItem == newItem
        }
    }
}