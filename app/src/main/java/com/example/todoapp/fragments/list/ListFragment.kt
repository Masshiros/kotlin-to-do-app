package com.example.todoapp.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.SharedVM
import com.example.todoapp.data.viewmodels.ToDoVM
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text


class ListFragment : Fragment(){
    private val adapter: ListAdapter by lazy {ListAdapter() }

    private val mToDoVM:ToDoVM by viewModels()
    private val mSharedVM:SharedVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        // fetch data with adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoVM.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedVM.checkEmptyDb(data)
            adapter.setData(data)

            Log.d("ListFragment", "Received new data: ${data.size}")
        })
        mSharedVM.emptyDb.observe(viewLifecycleOwner, Observer{
            showEmptyDbViews(it)
        })

        // navigate
        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)

        }
        view.findViewById<ConstraintLayout>(R.id.listLayout).setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
        // menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDbViews(emptyDb: Boolean) {
        if(emptyDb){
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.VISIBLE
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.VISIBLE

        }else{
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.INVISIBLE
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }
    // confirm delete all item
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mToDoVM.deleteAll()
            Toast.makeText(requireContext(),"Successfully Removed All Items",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete all")
        builder.setMessage("Are you sure you want to remove all items?")
        builder.create().show()
    }

}