package com.example.tastebuds

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tastebuds.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList : ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.catname)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        binding.searchview.setOnClickListener{
            startActivity(Intent(this,searchActivity::class.java))
        }

        binding.healthy.setOnClickListener {
            var myIntent = Intent(this@MainActivity,categoryActivity::class.java)
            myIntent.putExtra("TITLE","Healthy")
            myIntent.putExtra("CAT","Salad")
            startActivity(myIntent)
        }
        binding.maincourse.setOnClickListener {
            var myIntent = Intent(this@MainActivity,categoryActivity::class.java)
            myIntent.putExtra("TITLE","Main Course")
            myIntent.putExtra("CAT","Dish")
            startActivity(myIntent)
        }
        binding.drinks.setOnClickListener {
            var myIntent = Intent(this@MainActivity,categoryActivity::class.java)
            myIntent.putExtra("TITLE","Drinks")
            myIntent.putExtra("CAT","Drinks")
            startActivity(myIntent)
        }
        binding.dessert.setOnClickListener {
            var myIntent = Intent(this@MainActivity,categoryActivity::class.java)
            myIntent.putExtra("TITLE","Desserts")
            myIntent.putExtra("CAT","Desserts")
            startActivity(myIntent)
        }



    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvPopular.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db = Room.databaseBuilder(this@MainActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipe = daoObject.getAll()
        for (i in recipe!!.indices){
            if (recipe[i]!!.category.contains("Popular")){
                dataList.add(recipe[i]!!)

            }
            rvAdapter = PopularAdapter(dataList,this)
            binding.rvPopular.adapter =rvAdapter
        }

    }
}