# TmapAPI-KickGoing

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
    
## * TMapView 띄우기와 현재 위치를 
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var isLocation = true
    private var actionBar = false
    private var tMapView:TMapView? = null

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var myLongitude: Double = 0.0 // 현재위치, 경도
    private var myLatitude: Double = 0.0   // 현재위치, 위도
    var data = HashMap<String, TMapMarkerItem>() // 킥고잉 마커명, 위치 저장 해시맵
    var pDistance:makedDistanceEvent = makedDistanceEvent()
    var n=0

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS =
        arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //val Rlayout:RelativeLayout = findViewById(R.id.map_view) as RelativeLayout
        tMapView = TMapView(this)
        tMapView!!.setSKTMapApiKey("l7xx6974090fe01b4f089324f17b6e5be7f3")
        map_view.addView(tMapView)  //레이아웃에 Tmap 추가
        tMapView!!.setIconVisibility(true)
        tMapView!!.setCompassMode(true)
        tMapView!!.setSightVisible(true)
