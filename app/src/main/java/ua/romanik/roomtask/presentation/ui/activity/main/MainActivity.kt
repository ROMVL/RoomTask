package ua.romanik.roomtask.presentation.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import ua.romanik.roomtask.R

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel
        bottomNavView.setupWithNavController(findNavController(R.id.navFragment))
    }

    override fun onNavigateUp(): Boolean = findNavController(R.id.navFragment).navigateUp()
}
