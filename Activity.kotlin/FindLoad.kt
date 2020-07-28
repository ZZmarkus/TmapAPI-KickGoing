package com.example.tmapclone_kickgoing

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_find_load.*

class FindLoad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_load)
        
        //카카오맵 api에서 Tmap으로 바뀌어서 Tmap에 맞는 설정이 필요 아래 코드는 카카오맵 api용 코드임
        
        /*
        val geocoder: Geocoder = Geocoder(this) //주소나 지명을 좌표로 변환시키기 위한 Geocoder
        
        search.setOnClickListener {
            var startlist: List<Address>? = null
            var endlist: List<Address>? = null

            startlist =geocoder.getFromLocationName(startAddress.text.toString(),10)    //editText의 주소를 불러와 좌표로 변환시킨 뒤 리스트에 넣는다.
            endlist=geocoder.getFromLocationName(endAddress.text.toString(),10)

            val startaddress: Address =startlist.get(0)
            val endaddress: Address =endlist.get(0)


            val uri = Uri.parse(
                "kakaomap://route?sp=" + startaddress.latitude.toString()
                        + "," + startaddress.longitude.toString() + "&ep="
                        + endaddress.latitude.toString() + ","
                        + endaddress.longitude.toString() + "&by=FOOT"
            )
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            */
        }
    }
}
