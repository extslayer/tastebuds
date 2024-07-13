package com.example.tastebuds

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.tastebuds.databinding.ActivityMainBinding
import com.example.tastebuds.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recipeback.setOnClickListener {
            finish()
        }
        binding.instrucbtn.setOnClickListener {
            binding.instrucbtn.background = null
            binding.instrucbtn.setImageResource(R.drawable.instrucbtn)
            binding.ingbtn.background = null
            binding.ingbtn.setImageResource(R.drawable.ingbtnup)
            binding.stepscroll.visibility = View.VISIBLE
            binding.ingscroll.visibility = View.GONE

        }

        binding.ingbtn.setOnClickListener {
            binding.instrucbtn.background = null
            binding.instrucbtn.setImageResource(R.drawable.instrcbtnup)
            binding.ingbtn.background = null
            binding.ingbtn.setImageResource(R.drawable.ingbton)
            binding.stepscroll.visibility = View.GONE
            binding.ingscroll.visibility = View.VISIBLE

        }

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.recipeimg)
        binding.recipetittle.text = intent.getStringExtra("title")
        binding.stepTxt.text = intent.getStringExtra("des")
        val ing = intent.getStringExtra("ing")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        for (i in 1 until ing!!.size){
            binding.ingTxt.text =
                """${binding.ingTxt.text}  ⚫ ${ing[i]}
                    
            """.trimIndent()
        }
    }
}