package ni.edu.uca.moviles2.comicviewer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity
import java.text.SimpleDateFormat
import java.util.*

//Adaptador para la lista de comics favoritos
class ComicListAdapter() : ListAdapter<ComicEntity, ComicListAdapter.ComicViewHolder>(COMICS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder.create(parent)
    }
    //
    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title,current.num,current.dateAdded)
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("comic" to current)

            Navigation.findNavController(holder.itemView).navigate(
                R.id.action_favorites_to_view_local,
                bundle)
        }
    }

    class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleItemView: TextView = itemView.findViewById(R.id.title_text)
        private val dateAddedItemView: TextView = itemView.findViewById(R.id.date_added_text)
        private val numItemView: TextView = itemView.findViewById(R.id.number_text)

        fun bind(title: String, num: Int, dateAdded: Long ) {
            titleItemView.text = itemView.context.resources.getString(R.string.title) +": " + title
            numItemView.text = itemView.context.resources.getString(R.string.num) +"\n " +num.toString()
            dateAddedItemView.text = itemView.context.resources.getString(R.string.dateAdded) +": " + convertLongToTime(dateAdded)
        }

        private fun convertLongToTime(dateAdded: Long): CharSequence? {
            val date = Date(dateAdded)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }

        companion object {
            fun create(parent: ViewGroup): ComicViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_view_item, parent, false)
                return ComicViewHolder(view)
            }
        }
    }

    companion object {
        private val COMICS_COMPARATOR = object : DiffUtil.ItemCallback<ComicEntity>() {
            override fun areItemsTheSame(oldItem: ComicEntity, newItem: ComicEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ComicEntity, newItem: ComicEntity): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}