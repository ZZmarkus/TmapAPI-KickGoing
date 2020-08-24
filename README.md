# TmapAPI-KickGoing

## Tmap API를 사용한 길찾기 기능이 포함된 퍼스널 모빌리티 대여 어플
#### - 프로젝트기간 : 2020.07.20 ~ 2020.08.14


## 요구사항 분석

#### - 성능적 요구사항
```
1. 배터리 배터리 소모를 줄이기 위해 앱을 최소화 할 시 백그라운드에서는 동작하지 않게 한다.
2. 길찾기 시 검색 결과도출의 응답시간이 빨라야한다.
```

#### - 인터페이스 요구사항
```
1. 온라인 도움말, 사용 단말을 고려한 화면사이즈 제공, 오류메시지에 대한 내용을 제공하여
사용자 편의성 보장

2. 통합 UI를 구성하여 시스템 또는 SW의 통일성 보장
```

#### - 품질 요구사항
```
1. 시스템은 신속한 장애 대응을 위하여 백업 절차를 마련해야 함
2. 에러복구, 장애 대책 확보 등 신뢰성 있는 서비스 환경 제공
3. 프로그램 설치 및 제거, 이용이 용이하여야 함
```

#### - 기타 요구사항
```
1. 초기 자료 구축 시, 필요 작업 요소를 기술하고 작업 요소별 보정 요소에 대한 요건을 명시
2. 데이터 전환 시, 데이터 전환 시간, 전환 데이터의 우선순위 등을 명시해야 한다.
```

#### - 기능적 요구사항
```
1. 사용자는 전동 킥보드 기준의 길찾기 기능을 이용
2. 원하는 사용자는 공유 전동 킥보드 대여 서비스를 이용
3. 서비스 환경 제공
4. 프로그램 설치 및 제거, 이용이 용이
```

#### - 요구사항에 부합하는 기능
```
1. 사용자는 공유 전동 킥보드를 선택하여 이용.
2. 자신의 현재 위치를 기준으로 포커스를 위한 버튼이 존재, 버튼을
누를 시 자신의 현재 위치 중심의 맵을 보여줌
3. +버튼과 – 버튼이 존재하며, 이는 각각 줌인 줌 아웃 기능으로 맵을
확대 축소
4. 포커싱된 맵의 일정 반경내에 있는 전동 킥보드를 보여줌.
5. 사용자는 회원가입을 할 때 운전면허를 등록할 수 있음.
6. 사용자는 현재위치로부터 목적지까지의 폴리건 라인을 볼 수 있음.
7. 사용자는 기존의 길찾기 기능에 더해 전동 킥보드 기준의 길찾기 기능
을 이용.
8. 사용자는 현재위치로부터 자신의 정해진 반경 내 전동 킥보드까지의
거리, 예상시간을 알 수 있음.
9. 대여하기 버튼을 누르면 QR코드를 찍을 수 있는 화면이 나옴.
10. 전동 킥보드가 위치하는 곳에는 마커가 찍혀져 있다.
11. 전동 킥보드를 빌리게 되면 마커가 사라진다.
12. 나침반 버튼이 있고 버튼 클릭 시 방향에 맞게 지도가 회전하고 한번 더 클릭시
지도는 고정되어 터치와 드래그로 조작가능하다.
13. 각 전동킥보드 마킹은 각자 고유 태그값을 가지고 있다.
```

## * 구현할 기능 
    * 1. 앱을 실행시켰을 때 사용자는 현재 위치를 확인할 수 있어야 한다.
    * 2. 다른 지역확인 중 현재위치 버튼 클릭 시 현재 위치로 이동할 수 있어야 한다.
    * 3. 출발지, 목적지 입력 시 폴리건 라인을 통해 거리를 선으로 나타낸다.
    * 4. 전동 킥보드의 길찾기는 자전거와 동일한 수준으로 길찾기를 해준다.
    * 5. 사용자는 포커싱된 맵의 일정 반경내에 있는 전동 킥보드를 볼 수 있다.
    * 6. 전동 킥보드는 맵에 랜덤 좌표로 배정된다. (실제 전동 킥보드를 배치할 수 없기때문)
    * 7. 사용자는 일정 반경내에 있는 킥고잉과의 거리, 예상 시간을 알 수 있다.
    * 8. 전동 킥보드의 위치, 태그, 이름은 Firebase RealTime DB에 저장한다.
    * 9. 사용자는 회원가입을 할 때 선택적으로 운전면허 등록을 할 수 있다.
    * 10. 사용자는 전동 킥보드를 대여하기 위해서 회원가입 및 로그인을 해야하며 운전면허 등록이 필요하다.
    * 11. 사용자는 이용하기 버튼을 누르면 전동 킥보드에 부착되어 있는 QR코드를 찍어 대여할 수 있다.
    

# MainActivity
## * TMapView 띄우기와 현재 위치를 받기 위한 준비
```kotlin
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var lm : LocationManager
    private var isLocation = true
    private var actionBar = false
    private lateinit var tMapView:TMapView
    private var QR_on = false

    //var currentUser: FirebaseAuth = Activity_login.auth

    private var myLongitude: Double = 0.0 // 현재위치, 경도
    private var myLatitude: Double = 0.0   // 현재위치, 위도
    var data = ArrayList<TMapMarkerItem>() // 킥고잉 마커명, 위치 저장 해시맵
    var n=0

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_format_list_bulleted_24)

        val intent: Intent = Intent(this, Activity_login::class.java)
        startActivity(intent)



        //val Rlayout:RelativeLayout = findViewById(R.id.map_view) as RelativeLayout
        tMapView = TMapView(this)
        tMapView!!.setSKTMapApiKey("API KEY")
//        map_view.addView(tMapView)  //레이아웃에 Tmap 추가
        tMapView!!.setIconVisibility(true)
        tMapView!!.setCompassMode(true)
        tMapView!!.setSightVisible(true)
```
## * 위치 변경되면 발생하는 이벤트리스너
```kotlin
private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                myLongitude = location.longitude
                myLatitude = location.latitude

                tMapView!!.setLocationPoint(myLongitude, myLatitude)
                tMapView!!.setCenterPoint(myLongitude, myLatitude)
                tMapView!!.setTrackingMode(true)

                Thread(Runnable { // runOnUiThread를 추가하고 그 안에 UI작업을 한다.
                    runOnUiThread {
                        for(i in data){ // data에는 DB에서 받아온 킥고잉 마커들이 담겨있음
                            var lat = i.tMapPoint.latitude
                            var lon = i.tMapPoint.longitude
                            var model = i.calloutTitle

                            var p:TMapPoint = TMapPoint(lat, lon) // 마커 포인트

                            // 2020-08-11(화) 작성
                            // 현재 위치 변경 시 킥고잉과의 거리를 서브 타이틀에 표시
                            var myLocation = TMapPoint(location!!.latitude, location.longitude) // 현재위치를 가진 변수
                            var tpolyLine = TMapPolyLine()
                            tpolyLine.addLinePoint(p) // 마커 포인트 추가
                            tpolyLine.addLinePoint(myLocation) // 현재위치 포인트 추가
                            var dist = tpolyLine.distance.toInt()
                            i.calloutSubTitle = "${dist}m"
                            i.autoCalloutVisible = true
                            tMapView!!.invalidate()
                        }
                    }
                }).start()

            }
        } // end of mLocationListener
```

## * 현재 위치를 받기 위한 gps 함수 사용
```kotlin
    // 현재위치를 위한 gps를 받아오는 함수
    fun setGps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        lm.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,  // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
            500, 1f,  // 통지사이의 최소 변경거리 (m)
            mLocationListener
        )
    }// end of setGps()
```
## * gps버튼, 확대, 축소버튼에 대한 클릭 리스너
```kotlin
myLocation.setOnClickListener {
            if(isLocation){
                tMapView!!.setTrackingMode(false)
                tMapView!!.setSightVisible(false)
                tMapView!!.setCompassMode(false)
                tMapView!!.invalidate()
                isLocation = false
                myLocation.setImageResource(R.drawable.ic_baseline_my_location_24_black)
            }
            else{
                tMapView!!.setTrackingMode(true)
                tMapView!!.setSightVisible(true)
                tMapView!!.setCompassMode(true)
                isLocation = true
                myLocation.setImageResource(R.drawable.ic_baseline_my_location_24)
            }
        }

        //zoomIn버튼 클릭시 지도 확대
        zoomIn.setOnClickListener {
            tMapView?.MapZoomIn()
        }
        //zoomOut버튼 클릭시 지도 축소
        zoomOut.setOnClickListener {
            tMapView?.MapZoomOut()
        }
```

## * 앱이 실행될 때 User클래스의 정보를 가져와 로그인 상태를 체크한다.
```kotlin
 override fun onStart() {
        super.onStart()
        drawer_layout.closeDrawer(GravityCompat.START)

        var fb: FirebaseUser? = auth?.currentUser
        if (fb != null) {
            if (User.getFBUserLog() == true) {
                User.setName(fb.displayName.toString())
                User.setEmail("FaceBook")
                User.setUserLog(true)
            } else {
                User.setName(fb.displayName.toString())
                User.setEmail(fb.email.toString())
                User.setUserLog(true)
            }
        }
        Log.d("start", "email : " + User.getEmail())
        Log.d("start", "name : " + User.getName())
        Log.d("start", "login : " + User.getUserLog())
    }
```

## * 툴바의 메뉴를 클릭할 때 User클래스의 정보를 바탕으로 현재 로그인한 유저정보로 세팅해준다.
```kotlin
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                name.text = User.getName()
                email.text = User.getEmail()
                Log.d("showInformed", "mainEmail: " + User.getName())
            }
        }
        return super.onOptionsItemSelected(item)
    }
```

## * 툴바의 메뉴를 눌렀을 때 나오는 메뉴들에 대한 행동
```kotlin
override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account-> {
                if(User.getUserLog()==true){
                    auth.signOut()
                }
                else {
                    val intent: Intent = Intent(this, Activity_login::class.java)
                    startActivity(intent)
                }
            }
            R.id.how-> Toast.makeText(this,"item2 clicked",Toast.LENGTH_SHORT).show()
            R.id.setting-> Toast.makeText(this,"item3 clicked",Toast.LENGTH_SHORT).show()
            R.id.logout->{
                drawer_layout.closeDrawer(GravityCompat.START)
                Firebase.auth.signOut()
                User.setUserLog(false)
                User.setName("QuickGoing")
                User.setFBlogin(false)
                User.setEmail("do not login")
            }
        }
        return false
    }
```

## * Static 변수 선언
```kotlin
    companion object{
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
    }
```
