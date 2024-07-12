package com.example.tastebuds

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tastebuds.databinding.ActivityCategoryBinding

class categoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList : ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.catname)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backbtn.setOnClickListener {
            finish()
        }

        binding.categoryname.text = intent.getStringExtra("TITLE")


        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        var db = Room.databaseBuilder(this@categoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipe = daoObject.getAll()
        for (i in recipe!!.indices){
            if (recipe[i]!!.category.contains(intent.getStringExtra("CAT")!!)){
                dataList.add(recipe[i]!!)

            }
            rvAdapter = CategoryAdapter(dataList,this)
            binding.rvCategory.adapter =rvAdapter
        }

    }
}