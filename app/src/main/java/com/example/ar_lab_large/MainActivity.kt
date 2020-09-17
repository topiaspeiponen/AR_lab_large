package com.example.ar_lab_large

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Topias Peiponen, Roope Vaarama
 * @since 17.09.2020
 */

class MainActivity : AppCompatActivity() {
    private lateinit var fragment : ArFragment
    private var renderable1: ModelRenderable? = null
    private var renderable2: ModelRenderable? = null
    private var renderable3: ViewRenderable? = null
    private var renderable4: ViewRenderable? = null
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

        createRenderables()
    }

    private fun addObjectModel(renderable : ModelRenderable){
        val frame = fragment.arSceneView.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        if(frame != null && renderable != null){
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for(hit in hits){
                val trackable = hit.trackable
                if(trackable is Plane){
                    val anchor = hit!!.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val mNode = TransformableNode(fragment.transformationSystem)
                    mNode.setParent(anchorNode)
                    mNode.renderable = renderable
                    mNode.select()
                    break
                }
            }
        }
    }

    private fun addObjectView(renderable : ViewRenderable){
        val frame = fragment.arSceneView.arFrame
        val pt = getScreenCenter()
        val hits: List<HitResult>
        if(frame != null && renderable != null){
            hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for(hit in hits){
                val trackable = hit.trackable
                if(trackable is Plane){
                    val anchor = hit!!.createAnchor()
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val mNode = TransformableNode(fragment.transformationSystem)
                    mNode.setParent(anchorNode)
                    mNode.renderable = renderable
                    mNode.select()
                    break
                }
            }
        }
    }

    private fun createRenderables() {
        val renderableFuture1 = ModelRenderable.builder()
            .setSource(this, RenderableSource.builder().setSource(
                this,
                Uri.parse("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf"),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.2f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build())
            .setRegistryId("Roope")
            .build()
        renderableFuture1.thenAccept { renderable1 = it}
        renderableFuture1.exceptionally { throwable -> renderable1 }

        val renderableFuture2 = ModelRenderable.builder()
            .setSource(this, RenderableSource.builder().setSource(
                this,
                Uri.parse("orig/tolppa.gltf"),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.2f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build())
            .setRegistryId("Tolppa")
            .build()
        renderableFuture2.thenAccept { renderable2 = it}
        renderableFuture2.exceptionally { throwable -> renderable2 }

        val renderableFuture3 = ViewRenderable.builder()
            .setView(this, R.layout.text)
            .build()
        renderableFuture3.thenAccept { renderable3 = it}
        renderableFuture3.exceptionally { throwable -> renderable3 }

        val renderableFuture4 = ViewRenderable.builder()
            .setView(this, R.layout.button)
            .build()
        renderableFuture4.thenAccept { renderable4 = it}
        renderableFuture4.exceptionally { throwable -> renderable4 }
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
        addObjectModel(renderable1!!)
    }

    private val button2ClickListener = View.OnClickListener {
        gone()
        addObjectModel(renderable2!!)
    }

    private val button3ClickListener = View.OnClickListener {
        gone()
        addObjectView(renderable3!!)
    }

    private val button4ClickListener = View.OnClickListener {
        gone()
        addObjectView(renderable4!!)
    }
}