package ir.calendar.kotlincource.KotlinCodes.KotlinRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinder.R
import com.example.moviefinder.models.ResultModel
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieAdapter(private val data : MutableList<ResultModel>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =  holder.bind(data[position])

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (resultModel: ResultModel){
            itemView.txt_name_movie.text = resultModel.title
        }
    }
}