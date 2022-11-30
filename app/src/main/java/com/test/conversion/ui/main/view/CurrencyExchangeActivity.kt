package com.test.conversion.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.test.conversion.R

class CurrencyExchangeActivity : AppCompatActivity() {

    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        initNavigation()
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater

        navGraph = graphInflater.inflate(R.navigation.exchange_rate_navigation)
        navController = navHostFragment.navController
        navGraph.setStartDestination(R.id.exchange_rates_fragment)
        navController.graph = navGraph
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}