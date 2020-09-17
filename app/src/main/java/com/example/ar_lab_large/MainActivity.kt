package com.example.ar_lab_large

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*

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
        fab.setOnClickListener(fabClickListener)
        button1.setOnClickListener(button1ClickListener)
        button2.setOnClickListener(button2ClickListener)
        button3.setOnClickListener(button3ClickListener)
        button4.setOnClickListener(button4ClickListener)


    }

    private fun gone(){
        fab.show()
        button1.visibility = View.GONE
        button2.visibility = View.GONE
        button3.visibility = View.GONE
        button4.visibility = View.GONE
    }

    private fun show(){
        fab.hide()
        button1.visibility = View.VISIBLE
        button2.visibility = View.VISIBLE
        button3.visibility = View.VISIBLE
        button4.visibility = View.VISIBLE
    }

    private val fabClickListener = View.OnClickListener {
        show()

    }

    private val button1ClickListener = View.OnClickListener {
        gone()
    }

    private val button2ClickListener = View.OnClickListener {
        gone()
    }

    private val button3ClickListener = View.OnClickListener {
        gone()
    }

    private val button4ClickListener = View.OnClickListener {
        gone()
    }
}