package edu.stanford.yifeng98.myyelpclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "-q7FyCOSxgFzx3_f0uUdPu9SDCfNQ8N_uaR4G44woCDjDZMjZMsEdC97a5wFoCzBek6yikJ1zbpZX4YKpfnvC-VffggGPoLgIKHXkgZSKZpwVkmKe5flhXcYJamoX3Yx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        search("Avocado Toast")
//        val restaurants = mutableListOf<YelpRestaurant>()
//        val adapter = RestaurantsAdapter(this, restaurants)
//        rvRestaurants.adapter = adapter
//        rvRestaurants.layoutManager = LinearLayoutManager(this)
//        val retrofit =
//            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
//        val yelpService = retrofit.create(YelpService::class.java)
//        yelpService.searchRestaurants("Bearer $API_KEY","Avocado Toast", "New York").enqueue(object: Callback<YelpSearchResult>{
//            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
//                Log.i(TAG,"onResponse $response")
//                val body = response.body()
//                if (body==null){
//                    Log.w(TAG, "Did not receive valid data. Exiting...")
//                    return
//                }
//                restaurants.addAll(body.restaurants)
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
//                Log.i(TAG,"onFailure $t")
//            }
//        })

        etFood.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(etFood.text.isEmpty()){
                    return
                }
                search(etFood.text.toString())
            }

        })



    }

    private fun search(name: String){
        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY",name, "New York").enqueue(object: Callback<YelpSearchResult>{
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG,"onResponse $response")
                val body = response.body()
                if (body==null){
                    Log.w(TAG, "Did not receive valid data. Exiting...")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG,"onFailure $t")
            }
        })
    }
}