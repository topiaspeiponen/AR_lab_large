package com.example.ar_lab_large

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Topias Peiponen, Roope Vaarama
 * @since 17.09.2020
 */

class MainActivity : AppCompatActivity() {
    private lateinit var fragment : ArFragment
    private var testRenderable: ModelRenderable? = null
    private lateinit var modelUri: Uri


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

private fun addObject(){
    val renderableFuture = ModelRenderable.builder()
        .setSource(this, RenderableSource.builder().setSource(
            this,
            modelUri,
            RenderableSource.SourceType.GLTF2)
            .setScale(0.2f) //Scale the original model to 20%
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build())
        .setRegistryId("CesiusMan")
        .build()
    renderableFuture.thenAccept { it -> testRenderable = it}
    renderableFuture.exceptionally { throwable -> testRenderable }

    val frame = fragment.arSceneView.arFrame
    val pt = getScreenCenter()
    val hits: List<HitResult>
    if(frame != null && testRenderable != null){
        hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
        for(hit in hits){
            val trackable = hit.trackable
            if(trackable is Plane){
                val anchor = hit!!.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(fragment.arSceneView.scene)
                val mNode = TransformableNode(fragment.transformationSystem)
                mNode.setParent(anchorNode)
                mNode.renderable = testRenderable
                mNode.select()
                break
            }
        }
    }

}

private fun getScreenCenter(): android.graphics.Point {
    val vw = findViewById<View>(android.R.id.content)
    Log.d("vw", "$vw")
    return android.graphics.Point(vw.width/2, vw.height/2)
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
        modelUri = Uri.parse("https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/CesiumMan/glTF/CesiumMan.gltf")
        addObject()
    }

    private val button2ClickListener = View.OnClickListener {
        gone()
        modelUri = Uri.parse("")
    }

    private val button3ClickListener = View.OnClickListener {
        gone()
        modelUri = Uri.parse("")
    }

    private val button4ClickListener = View.OnClickListener {
        gone()
        modelUri = Uri.parse("")
    }
}