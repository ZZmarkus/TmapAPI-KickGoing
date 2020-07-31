package com.example.tmapclone_kickgoing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val Rlayout:RelativeLayout = findViewById(R.id.map_view) as RelativeLayout
        var tMapView = TMapView(this)
        tMapView.setSKTMapApiKey("APIKey")

        map_view.addView(tMapView)  //레이아웃에 Tmap 추가

        zoomIn.setOnClickListener { //zoomIn버튼 클릭시 지도 확대
            tMapView.MapZoomIn()
        }

        zoomOut.setOnClickListener {
            tMapView.MapZoomOut()
        }

        var intent = Intent(this, FindLoad::class.java)
        findLoad.setOnClickListener {
            startActivity(intent)
        }
    }
}
