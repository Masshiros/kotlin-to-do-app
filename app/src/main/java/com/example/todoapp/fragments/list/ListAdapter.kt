package com.example.todoapp.fragments.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.EPriority
import com.example.todoapp.data.models.ToDoEntity

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var datas = mutableListOf<ToDoEntity>()
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view)  { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_txt).text = datas[position].title
        holder.itemView.findViewById<TextView>(R.id.description_txt).text = datas[position].description
        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(datas[position])
            holder.itemView.findNavController().navigate(action)
        }
        when(datas[position].priority){
            EPriority.HIGH -> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.red))
            EPriority.MEDIUM -> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.yellow))
            EPriority.LOW -> holder.itemView.findViewById<CardView>(R.id.priority_indicator).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.green))

        }
    }
    fun setData(toDoData: List<ToDoEntity>){
        this.datas.clear()
        this.datas.addAll(toDoData)

        notifyDataSetChanged()
        Log.d("ListAdapter","Datas: ${this.datas.size}")
    }

}