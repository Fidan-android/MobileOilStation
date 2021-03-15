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
import com.example.mobileoilstation.model.Car

class CarAdapter(private val activity: Activity, private val context : Context, val carList : MutableList<Car>?) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var carLayout: RelativeLayout = itemView.findViewById(R.id.car_layout)
        var carImage: ImageView = itemView.findViewById(R.id.car_image)
        var carMark: TextView = itemView.findViewById(R.id.car_mark)
        var carModel: TextView = itemView.findViewById(R.id.car_model)
        var carNumber: TextView = itemView.findViewById(R.id.car_num)
        var carColor: TextView = itemView.findViewById(R.id.car_color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        activity.registerForContextMenu(holder.carLayout)
    }

    override fun getItemCount(): Int {
        return 10
    }


}