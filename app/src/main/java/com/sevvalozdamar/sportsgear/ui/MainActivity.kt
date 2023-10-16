package com.sevvalozdamar.sportsgear.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sevvalozdamar.sportsgear.R
import com.sevvalozdamar.sportsgear.common.viewBinding
import com.sevvalozdamar.sportsgear.databinding.ActivityMainBinding
import com.sevvalozdamar.sportsgear.ui.cart.CartFragment
import com.sevvalozdamar.sportsgear.ui.favorites.FavoritesFragment
import com.sevvalozdamar.sportsgear.ui.home.HomeFragment
import com.sevvalozdamar.sportsgear.ui.search.SearchFragment


class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, HomeFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuSearch -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, SearchFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuCart -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, CartFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.menuFavorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FavoritesFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        fun showBottomNavigation() {
            binding.bottomNavigation.visibility = View.VISIBLE
        }

        fun hideBottomNavigation() {
            binding.bottomNavigation.visibility = View.GONE
        }

    }
}
