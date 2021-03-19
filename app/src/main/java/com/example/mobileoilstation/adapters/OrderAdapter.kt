package com.example.mobileoilstation.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileoilstation.R
import com.example.mobileoilstation.model.Order

class OrderAdapter(var activity: Activity, var context: Context, var ordersList: MutableList<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderLayout: RelativeLayout = itemView.findViewById(R.id.order_layout)
        var carImage: ImageView = itemView.findViewById(R.id.car_image)
        var carMark: TextView = itemView.findViewById(R.id.car_mark)
        var carModel: TextView = itemView.findViewById(R.id.car_model)
        var carNumber: TextView = itemView.findViewById(R.id.car_num)
        var carColor: TextView = itemView.findViewById(R.id.car_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        activity.registerForContextMenu(holder.orderLayout)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }


}