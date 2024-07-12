package com.example.tastebuds

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.tastebuds.databinding.ActivitySearchBinding

class searchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList : ArrayList<Recipe>
    private lateinit var recipe: List<Recipe?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.catname)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.searchview.requestFocus()
        binding.goback.setOnClickListener {
            finish()
        }
        var db = Room.databaseBuilder(this@searchActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        recipe = daoObject.getAll()!!

        setupRecyclerView()

        binding.searchview.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString()!==""){
                    filterData(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun filterData(filterText: String) {
        var filterData = ArrayList<Recipe>()
        for (i in recipe.indices){
            if(recipe[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                filterData.add(recipe[i]!!)

            }
            rvAdapter.filterList(filterList = filterData)
        }
    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        for (i in recipe!!.indices){
            if (recipe[i]!!.category.contains("Popular")){
                dataList.add(recipe[i]!!)

            }
            rvAdapter = SearchAdapter(dataList,this)
            binding.rvSearch.adapter =rvAdapter
        }

    }
}