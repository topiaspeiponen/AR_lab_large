package com.example.ar_lab_large

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.ux.ArFragment

/**
 * @author Topias Peiponen, Roope Vaarama
 * @since 17.09.2020
 */

class MainActivity : AppCompatActivity() {
    private lateinit var fragment : ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment
    }
}