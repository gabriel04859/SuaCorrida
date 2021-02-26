package com.gabrielribeiro.suacorrida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.gabrielribeiro.suacorrida.database.RunDAO
import com.gabrielribeiro.suacorrida.databinding.ActivityMainBinding
import com.gabrielribeiro.suacorrida.utils.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navController = findNavController(R.id.navHostFragment)
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        binding.bottomNavigationViewMain.setOnNavigationItemReselectedListener { /* NO-OP */ }
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.settingsFragment, R.id.runFragment, R.id.staticsFragment ->
                    binding.bottomNavigationViewMain.visibility = View.VISIBLE
                else -> binding.bottomNavigationViewMain.visibility = View.GONE
            }
        }
        navigateToTrackingFragment(intent)

    }

    private fun navigateToTrackingFragment(intent : Intent?) {
        if(intent?.action == ACTION_SHOW_TRACKING_FRAGMENT){
            navController.navigate(R.id.action_global_to_trackingFragment)
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragment(intent)
    }
    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController,appBarConfiguration)
    
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}