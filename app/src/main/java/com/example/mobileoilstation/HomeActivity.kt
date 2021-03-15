package com.example.mobileoilstation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobileoilstation.adapters.CarAdapter
import com.example.mobileoilstation.databinding.ActivityHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem) {
                R.id.maps -> this.bottomBarSelect(map = View.VISIBLE);
                R.id.cars -> this.bottomBarSelect(car = View.VISIBLE);
                R.id.orders -> this.bottomBarSelect(order = View.VISIBLE);
                R.id.profiles -> this.bottomBarSelect(profile = View.VISIBLE);
            }
        }
        binding.bottomBar.setItemSelected(R.id.maps);
        binding.bottomBar.setItemEnabled(R.id.maps, true);

        binding.rvCars.adapter = CarAdapter(this, this,null)
        binding.rvCars.layoutManager = GridLayoutManager(this, 2)

        binding.addCar.setOnClickListener(View.OnClickListener {
            Snackbar.make(it, "Adding", Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun bottomBarSelect(map : Int = View.INVISIBLE, car : Int = View.INVISIBLE,
                                order: Int = View.INVISIBLE, profile : Int = View.INVISIBLE) {
        binding.map.visibility = map;
        binding.car.visibility = car;
        binding.order.visibility = order;
        binding.profile.visibility = profile;
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.car_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit -> {
                Snackbar.make(item.actionView, "Edited", Snackbar.LENGTH_SHORT).show();
                return true
            }
            R.id.delete -> {
                Snackbar.make(item.actionView, "Deleted", Snackbar.LENGTH_SHORT).show();
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}