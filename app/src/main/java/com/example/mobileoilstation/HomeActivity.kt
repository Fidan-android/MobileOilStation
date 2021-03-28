package com.example.mobileoilstation


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobileoilstation.adapters.CarAdapter
import com.example.mobileoilstation.adapters.OrderAdapter
import com.example.mobileoilstation.api.ApiRequest
import com.example.mobileoilstation.databinding.ActivityHomeBinding
import com.example.mobileoilstation.model.Car
import com.example.mobileoilstation.model.Message
import com.example.mobileoilstation.model.Order
import com.example.mobileoilstation.model.User
import com.example.mobileoilstation.tracker.TrackerGPS
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val BaseUrl: String = "http://mobile-oil-station.ru"
    private lateinit var mapView: MapView
    private var backPressed: Long = 0
    private lateinit var gps: TrackerGPS
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var cars: MutableList<Car>? = null
    private var orders: MutableList<Order>? = null
    private lateinit var user: User
    private var FINAL_LONGITUDE: Double = 54.44
    private var FINAL_LATITUDE: Double = 55.58

    //SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.initialize(this)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        mapView = binding.mapView
        checkGpsPermission()

        binding.bottomBar.setOnItemSelectedListener { menuItem ->
            when (menuItem) {
                R.id.maps -> {
                    this.bottomBarSelect(map = View.VISIBLE)
                    mapView.onStart()
                }
                R.id.cars -> {
                    binding.addCar.isEnabled = true;
                    if (cars != null && cars!!.size > 0) {
                        binding.rvCars.visibility = View.VISIBLE
                        binding.textCarNoExists.visibility = View.INVISIBLE
                        binding.rvCars.adapter = CarAdapter(this, this, cars!!)
                        binding.rvCars.layoutManager = GridLayoutManager(this, 2)
                    } else {
                        binding.rvCars.visibility = View.VISIBLE
                        binding.textCarNoExists.visibility = View.INVISIBLE
                    }
                    this.bottomBarSelect(car = View.VISIBLE)
                    mapView.onStop()
                }
                R.id.orders -> {
                    if (orders != null && orders!!.size > 0) {
                        binding.rvOrders.visibility = View.VISIBLE
                        binding.textOrderNoExists.visibility = View.INVISIBLE
                        binding.rvOrders.adapter = OrderAdapter(this, this, orders!!)
                        binding.rvOrders.layoutManager = GridLayoutManager(this, 2)
                    } else {
                        binding.rvOrders.visibility = View.INVISIBLE
                        binding.textOrderNoExists.visibility = View.VISIBLE
                    }
                    this.bottomBarSelect(order = View.VISIBLE)
                    mapView.onStop()
                }
                R.id.profiles -> {
                    println(user.phone)
                    this.bottomBarSelect(profile = View.VISIBLE)
                    mapView.onStop()
                }
            }
        }
        binding.bottomBar.setItemSelected(R.id.maps);
        binding.bottomBar.setItemEnabled(R.id.maps, true);

        binding.addCar.setOnClickListener {
            startActivity(Intent(this, AddCarActivity::class.java))
            binding.addCar.isEnabled = false;
            //finish()
        }

        binding.addNewOrder.setOnClickListener {
            Snackbar.make(it, "Adding", Snackbar.LENGTH_SHORT).show()
        }


        binding.myLocation.setOnClickListener {
            checkGpsPermission()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view: View? = super.onCreateView(name, context, attrs)

        val preferences: SharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), 0)
        val token: String = preferences.getString("token", "")!!
        println(token)

        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor {
                    val request: Request = it.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    return@addInterceptor it.proceed(request)
                }
                .build()

        val retrofit: ApiRequest = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiRequest::class.java)

        val serviceCars = retrofit.getCars()
        serviceCars.enqueue(object : Callback<MutableList<Car>> {
            override fun onResponse(call: Call<MutableList<Car>>, response: Response<MutableList<Car>>) {
                cars = if (response.body() != null) {
                    response.body() as MutableList<Car>
                } else {
                    arrayListOf()
                }
            }

            override fun onFailure(call: Call<MutableList<Car>>, t: Throwable) {
                t.printStackTrace()
            }
        })

        val serviceOrders = retrofit.getOrders()
        serviceOrders.enqueue(object: Callback<MutableList<Order>>{
            override fun onResponse(call: Call<MutableList<Order>>, response: Response<MutableList<Order>>) {
                orders = if (response.body() != null) {
                    response.body() as MutableList<Order>
                } else {
                    null
                }
            }

            override fun onFailure(call: Call<MutableList<Order>>, t: Throwable) {
                t.printStackTrace()
            }

        })

        val serviceProfile = retrofit.getProfileInfo()
        serviceProfile.enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                user = response.body() as User
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

        })

        return view
    }

    private fun checkGpsPermission(){
        val access_fine_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (access_fine_location == PackageManager.PERMISSION_GRANTED) {
            fusedLocation = LocationServices.getFusedLocationProviderClient(this)
            fusedLocation.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            FINAL_LATITUDE = location.latitude
                        }
                        if (location != null) {
                            FINAL_LONGITUDE = location.longitude
                        }
                    }
            println("latitude: $FINAL_LATITUDE and longitude: $FINAL_LONGITUDE")
            mapView.map.mapObjects.addPlacemark(Point(FINAL_LATITUDE, FINAL_LONGITUDE))
            mapView.map.move(
                    CameraPosition(Point(FINAL_LATITUDE, FINAL_LONGITUDE), 15.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 2.5f),
                    null)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }

    private fun bottomBarSelect(map: Int = View.INVISIBLE, car: Int = View.INVISIBLE,
                                order: Int = View.INVISIBLE, profile: Int = View.INVISIBLE) {
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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onBackPressed() {
        if (backPressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}