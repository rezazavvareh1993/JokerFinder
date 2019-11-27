package ir.calendar.kotlincource.KotlinCodes.KotlinRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.example.moviefinder.Utils.MyDiffUtilCallback
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieAdapter(private val data : MutableList<String>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =  holder.bind(data[position])

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (movieName : String){
            itemView.txt_name_movie.text = movieName
        }
    }

    fun insertItem(newList : List<String>){
        val diffUtilCallback = MyDiffUtilCallback(data, newList)
        val diffUtilsResult = DiffUtil.calculateDiff(diffUtilCallback)

        data.addAll(newList)//Add new items to exist dataList
        diffUtilsResult.dispatchUpdatesTo(this)

    }

    fun updateItem(newList : List<String>){
        val diffUtilCallback = MyDiffUtilCallback(data, newList)
        val diffUtilsResult = DiffUtil.calculateDiff(diffUtilCallback)

        data.clear()//clear old dataList
        data.addAll(newList)
        diffUtilsResult.dispatchUpdatesTo(this)

    }

}