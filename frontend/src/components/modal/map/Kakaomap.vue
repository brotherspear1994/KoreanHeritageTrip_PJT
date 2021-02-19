<template>
    <div class="container">
        <!-- search bar -->
        <div class="brown1 mainHeader">
            <span class="jua" style="font-size: 18px;">
            우리나라의 방대한 문화재를 담은 한디아나존스 지도입니다.
            <br/>
            지도내 정보 조회 안내
            <ul>
                <li>마커에 커서를 올릴 시 간이 정보 조회</li>
                <li>마커 클릭 시 상세 정보 조회</li>
            </ul>
            위치 간 경로 및 시간 안내
            <ul>
                <li>마우스 클릭 시 경로 생성</li>
                <li>마우스 우클릭 경로 생성 종료</li>
                <li>경로 클릭 시 삭제</li>
            </ul>
            </span>
        </div>
       <div class="search">
            <input type="text" class="searchTerm" placeholder="문화재 또는 지역을 검색해 주세요" v-model="keyword" @keypress.enter="keywordMarker(keyword)">
            <button type="" class="searchButton" @click="keywordMarker(keyword)">
                <i class="fa fa-search"></i>
            </button>
        </div>
        <!-- 카카오 맵 -->
        <vue-daum-map
        id="map"
        :appKey="appKey"
        :center.sync="center"
        :level.sync="level"
        :mapTypeId="mapTypeId"
        :libraries="libraries"
        @load="onLoad()"
        class= "kakaomap"
        />
        <!-- 문화재 상세정보 모달 -->
        <div id="myModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
                <div class="detailimagediv">
                    <img id="detailimg" src="" alt="" class="detailimage">
                </div>
                <span class="close">&times;</span>
                <div class="detailcontents">
                    <div class="detailtitle east-sea-Dokdo"><p id="detailtitle" style="margin-bottom: 10%">문화재 명</p></div>
                    <div class="detailkind"><p id="detailkind" class="jua">문화재 종류</p></div>
                    <div class="detaildescription">
                        <p id="detailcontent">문화재 설명</p>
                    </div>
                    <div class="detailaddress"><p id="detailaddress" class="jua">문화재 주소</p></div>
                </div>
            </div>
        </div> 
        <!-- 종료선 -->
    </div>
</template>


<script>
import VueDaumMap from 'vue-daum-map';
import { heritages } from "@/api/heritage.js";
// 문화재 정보 API 상세 정보 요청을 위한 세팅
import axios from 'axios';
import { API_BASE_URL } from '@/config/index.js';

export default {
    name: 'Kakaomap',
    components: {
        VueDaumMap,
    },
    data: () => ({
        // 카카오맵을 위한 data instances
        appKey: '2e4b87e7a16709ce4966d35d9caaad46',
        center: { lat: 36.2683, lng: 127.6358 }, // 지도의 중심 좌표
        level: 13, 
        mapTypeId: VueDaumMap.MapTypeId.NORMAL,
        libraries: ['services', 'clusterer', 'drawing'],
        map: null,
        // keyword 검색을 위한 data instances
        keyword: '',
        // 공지사항 창 boolean
        isClick: false,
    }),
    beforeCreate () {
        this.isClick=false
    },
    methods: {
        // 페이지에 최초 맵을 생성할 때 DB에 담긴 모든 문화유적의 마커를 깔고 클러스트로 표현하며 시작한다.
        onLoad () {
            var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
                    center : new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심좌표
                    level : 13 // 지도의 확대 레벨
                });
            this.map = map;

            // 경로거리 계산을 위한 코드 시작
            var drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
            var moveLine; // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
            var clickLine // 마우스로 클릭한 좌표로 그려질 선 객체입니다
            var distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
            var dots = {}; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.

            // 지도에 클릭 이벤트를 등록합니다
            // 지도를 클릭하면 선 그리기가 시작됩니다 그려진 선이 있으면 지우고 다시 그립니다
            kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

                // 마우스로 클릭한 위치입니다 
                var clickPosition = mouseEvent.latLng;

                // 지도 클릭이벤트가 발생했는데 선을 그리고있는 상태가 아니면
                if (!drawingFlag) {

                    // 상태를 true로, 선이 그리고있는 상태로 변경합니다
                    drawingFlag = true;
                    
                    // 지도 위에 선이 표시되고 있다면 지도에서 제거합니다
                    deleteClickLine();
                    
                    // 지도 위에 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
                    deleteDistnce();

                    // 지도 위에 선을 그리기 위해 클릭한 지점과 해당 지점의 거리정보가 표시되고 있다면 지도에서 제거합니다
                    deleteCircleDot();
                
                    // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
                    clickLine = new kakao.maps.Polyline({
                        map: map, // 선을 표시할 지도입니다 
                        path: [clickPosition], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
                        strokeWeight: 3, // 선의 두께입니다 
                        strokeColor: '#FF8A80', // 선의 색깔입니다
                        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
                        strokeStyle: 'solid' // 선의 스타일입니다
                    });
                    
                    // 선이 그려지고 있을 때 마우스 움직임에 따라 선이 그려질 위치를 표시할 선을 생성합니다
                    moveLine = new kakao.maps.Polyline({
                        strokeWeight: 3, // 선의 두께입니다 
                        strokeColor: '#FF8A80', // 선의 색깔입니다
                        strokeOpacity: 0.5, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
                        strokeStyle: 'solid' // 선의 스타일입니다    
                    });
                
                    // 클릭한 지점에 대한 정보를 지도에 표시합니다
                    displayCircleDot(clickPosition, 0);

                        
                } else { // 선이 그려지고 있는 상태이면

                    // 그려지고 있는 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();

                    // 좌표 배열에 클릭한 위치를 추가합니다
                    path.push(clickPosition);
                    
                    // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
                    clickLine.setPath(path);

                    var distance = Math.round(clickLine.getLength());
                    displayCircleDot(clickPosition, distance);
                }
            });
                
            // 지도에 마우스무브 이벤트를 등록합니다
            // 선을 그리고있는 상태에서 마우스무브 이벤트가 발생하면 그려질 선의 위치를 동적으로 보여주도록 합니다
            kakao.maps.event.addListener(map, 'mousemove', function (mouseEvent) {

                // 지도 마우스무브 이벤트가 발생했는데 선을 그리고있는 상태이면
                if (drawingFlag){
                    
                    // 마우스 커서의 현재 위치를 얻어옵니다 
                    var mousePosition = mouseEvent.latLng; 

                    // 마우스 클릭으로 그려진 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();
                    
                    // 마우스 클릭으로 그려진 마지막 좌표와 마우스 커서 위치의 좌표로 선을 표시합니다
                    var movepath = [path[path.length-1], mousePosition];
                    moveLine.setPath(movepath);    
                    moveLine.setMap(map);
                    
                    var distance = Math.round(clickLine.getLength() + moveLine.getLength()), // 선의 총 거리를 계산합니다
                        content = '<div class="dotOverlay distanceInfo jua">총거리 <span class="number">' + distance + '</span>m</div>'; // 커스텀오버레이에 추가될 내용입니다
                    
                    // 거리정보를 지도에 표시합니다
                    showDistance(content, mousePosition);   
                }             
            });                 

            // 지도에 마우스 오른쪽 클릭 이벤트를 등록합니다
            // 선을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면 선 그리기를 종료합니다
            kakao.maps.event.addListener(map, 'rightclick', function () {

                // 지도 오른쪽 클릭 이벤트가 발생했는데 선을 그리고있는 상태이면
                if (drawingFlag) {
                    
                    // 마우스무브로 그려진 선은 지도에서 제거합니다
                    moveLine.setMap(null);
                    moveLine = null;  
                    
                    // 마우스 클릭으로 그린 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();
                
                    // 선을 구성하는 좌표의 개수가 2개 이상이면
                    if (path.length > 1) {

                        // 마지막 클릭 지점에 대한 거리 정보 커스텀 오버레이를 지웁니다
                        if (dots[dots.length-1].distance) {
                            dots[dots.length-1].distance.setMap(null);
                            dots[dots.length-1].distance = null;    
                        }

                        var distance = Math.round(clickLine.getLength()), // 선의 총 거리를 계산합니다
                            content = getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다
                            
                        // 그려진 선의 거리정보를 지도에 표시합니다
                        showDistance(content, path[path.length-1]);  
                        
                    } else {

                        // 선을 구성하는 좌표의 개수가 1개 이하이면 
                        // 지도에 표시되고 있는 선과 정보들을 지도에서 제거합니다.
                        deleteClickLine();
                        deleteCircleDot(); 
                        deleteDistnce();

                    }
                    
                    // 상태를 false로, 그리지 않고 있는 상태로 변경합니다
                    drawingFlag = false;          
                }  
            });    

            // 클릭으로 그려진 선을 지도에서 제거하는 함수입니다
            function deleteClickLine() {
                if (clickLine) {
                    clickLine.setMap(null);    
                    clickLine = null;        
                }
            }

            // 마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하거
            // 마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
            function showDistance(content, position) {
                
                if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면
                    
                    // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
                    distanceOverlay.setPosition(position);
                    distanceOverlay.setContent(content);
                    
                } else { // 커스텀 오버레이가 생성되지 않은 상태이면
                    
                    // 커스텀 오버레이를 생성하고 지도에 표시합니다
                    distanceOverlay = new kakao.maps.CustomOverlay({
                        map: map, // 커스텀오버레이를 표시할 지도입니다
                        content: content,  // 커스텀오버레이에 표시할 내용입니다
                        position: position, // 커스텀오버레이를 표시할 위치입니다.
                        xAnchor: 0,
                        yAnchor: 0,
                        zIndex: 3  
                    });      
                }
            }

            // 그려지고 있는 선의 총거리 정보와 
            // 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
            function deleteDistnce () {
                if (distanceOverlay) {
                    distanceOverlay.setMap(null);
                    distanceOverlay = null;
                }
            }

            // 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여 
            // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
            function displayCircleDot(position, distance) {

                // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
                var circleOverlay = new kakao.maps.CustomOverlay({
                    content: '<span class="dot"></span>',
                    position: position,
                    zIndex: 1
                });

                // 지도에 표시합니다
                circleOverlay.setMap(map);

                if (distance > 0) {
                    // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
                    var distanceOverlay = new kakao.maps.CustomOverlay({
                        content: '<div class="dotOverlay jua">거리 <span class="number">' + distance + '</span>m</div>',
                        position: position,
                        yAnchor: 1,
                        zIndex: 2
                    });

                    // 지도에 표시합니다
                    distanceOverlay.setMap(map);
                }

                // 배열에 추가합니다
                dots.push({circle:circleOverlay, distance: distanceOverlay});
            }

            // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
            function deleteCircleDot() {
                var i;

                for ( i = 0; i < dots.length; i++ ){
                    if (dots[i].circle) { 
                        dots[i].circle.setMap(null);
                    }

                    if (dots[i].distance) {
                        dots[i].distance.setMap(null);
                    }
                }

                dots = [];
            }

            // 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여 
            // 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
            // HTML Content를 만들어 리턴하는 함수입니다
            function getTimeHTML(distance) {

                // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
                var walkkTime = distance / 67 | 0;
                var walkHour = '', walkMin = '';

                // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
                if (walkkTime > 60) {
                    walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + '</span>시간 '
                }
                walkMin = '<span class="number">' + walkkTime % 60 + '</span>분'

                // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
                var bycicleTime = distance / 227 | 0;
                var bycicleHour = '', bycicleMin = '';

                // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
                if (bycicleTime > 60) {
                    bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + '</span>시간 '
                }
                bycicleMin = '<span class="number">' + bycicleTime % 60 + '</span>분'

                // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
                var content = '<ul class="dotOverlay distanceInfo jua">';
                content += '    <li>';
                content += '        <span class="label">총거리</span><span class="number">' + distance + '</span>m';
                content += '    </li>';
                content += '    <li>';
                content += '        <span class="label">도보</span>' + walkHour + walkMin;
                content += '    </li>';
                content += '    <li>';
                content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
                content += '    </li>';
                content += '</ul>'

                return content;
            }// getTimehtml 종료부분 // 경로계산 종료

            // 문화재 데이터 axios 요청해서 받기
            heritages((response) => {
                // cluster 생성 methods 실행
                this.setMarkerCluster(response.data, map)
            })
        },
        // 마커 클러스터러를 생성.
        // async 사용해 동기처리.
        setMarkerCluster: async (data, map) => {
            let clusterer = new window.kakao.maps.MarkerClusterer({
                map: map,
                averageCenter: true,
                minLevel: 10,
                calculator: [10,50], // 클러스터의 크기 구분 값, 각 사이값마다 설정된 text나 style이 적용된다
                styles: [{ // calculator 각 사이 값 마다 적용될 스타일을 지정한다
                        width : '30px', height : '30px',
                        background: '#D7CCC8',
                        borderRadius: '15px',
                        color: '#000',
                        textAlign: 'center',
                        fontWeight: 'bold',
                        lineHeight: '31px',
                    },
                    {
                        width : '40px', height : '40px',
                        background: '#37474F',
                        borderRadius: '20px',
                        color: '#FFF',
                        textAlign: 'center',
                        fontWeight: 'bold',
                        lineHeight: '41px'
                    },
                    {
                        width : '50px', height : '50px',
                        background: '#D7CCC8',
                        borderRadius: '25px',
                        color: '#000',
                        textAlign: 'center',
                        fontWeight: 'bold',
                        lineHeight: '51px'
                    }, // styles 종료선.
                ] // clusterer 종료선.
            });
            // 데이터에서 좌표 값을 가지고 마커를 표시합니다
            let markers = data.map(
                (singleheritage) => {
                // 마커 이미지를 커스텀하기 위한 코드
                var imageSrc = require('@/assets/heritage.png'), // 마커이미지의 주소입니다    
                    imageSize = new kakao.maps.Size(28, 32), // 마커이미지의 크기입니다
                    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
                // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

                // 커스텀 오버레이가 표시될 위치입니다 
                var markerPosition = new kakao.maps.LatLng(singleheritage.latitude, singleheritage.longitude);  

                // 커스텀 오버레이를 생성합니다
                var customOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    position: markerPosition,
                    yAnchor: 10
                });
                // map에 커스텀오버레이 표시
                customOverlay.setMap(map);
                    // markers에 담길 position과 image 값들을 반환합니다.
                return new window.kakao.maps.Marker({
                    // marker에 담고싶은 정보들 data 이용해서 이 부분부터 추가.(중요)
                    // position: new window.kakao.maps.LatLng(singleheritage.latitude, singleheritage.longitude),
                    position: markerPosition,
                    image: markerImage,
                    clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
                });
            });
            // 마커에 마우스오버 등록을 위한 코드 시작.
            for (var i = 0; i < markers.length; i ++) {
                    var marker = markers[i]
                    var singledata = data[i]
                    // 마커에 표시할 인포윈도우 생성
                    // 인포위도우 커스터마이징(정보 및 이미지 담고 구조랑 디자인 짜기)
                    // image 요청 url 파라미터 생성
                    var ccbaKdcd = singledata.ccba_kdcd
                    var ccbaAsno = singledata.ccba_asno
                    var ccbaCtcd = singledata.ccba_ctcd   
                    var imageSrc= singledata.img_url
    
                    // 변수명 사용을 위해 분기점을 나눠준다.
                    var content0 = '<div class="infoHeader jua"><text>클릭하면 상세정보를 볼 수 있어요</text></div>'
                    var content1 = '<img class="infoImg" src="'
                    var content2 = imageSrc
                    var content3 = '">'
                    var content4 = '<div class="infoTitle"><text class="east-sea-Dokdo">'
                    var content5 = singledata.ccba_mnm
                    var content6 = '</text></div>'
    
                    var infowindow = new kakao.maps.InfoWindow({
                        content: content0+content1+content2+content3+content4+content5+content6 // 인포윈도우에 표시할 내용
                    });
                    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
                    // 이벤트 리스너로는 클로저를 만들어 등록합니다 
                    // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
                    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
                    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
                    // 마커에 클릭이벤트를 등록합니다
                    kakao.maps.event.addListener(marker, 'click', makeClickListner(ccbaKdcd,ccbaAsno,ccbaCtcd, imageSrc));
                }
            // 마우스오버 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
            function makeOverListener(map, marker, infowindow) {
                return function() {
                    infowindow.open(map, marker);
                };
            }
            // 마우스아웃 인포윈도우를 닫는 클로저를 만드는 함수입니다 
            function makeOutListener(infowindow) {
                return function() {
                    infowindow.close();
                };
            }
            // 클릭이벤트용 인포윈도우를 표시하는 클로저를 만드는 함수
            function makeClickListner(ccbaKdcd,ccbaAsno,ccbaCtcd,img) {
                return async () => {

                    // 마커 클릭시 모달 열람 기능 추가
                    var modal = document.getElementById("myModal");
                    modal.style.display = "block";
                    // When the user clicks on <span> (x), close the modal
                    var span = document.getElementsByClassName("close")[0];
                    span.onclick = function() {
                        modal.style.display = "none";
                    }
                    // When the user clicks anywhere outside of the modal, close it
                    window.onclick = function(event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                        }
                    }

                    // axios 요청
                    var name = null; 
                    var kind = null; 
                    var content = null; 
                    var loc = null
                    const getdetail = async () => {
                        try {
                            await axios.get(`${API_BASE_URL}/heritage?asno=${ccbaAsno}&ctcd=${ccbaCtcd}&kdcd=${ccbaKdcd}`)
                            .then((res) => {
                                name = res.data.ccba_mnm
                                kind = res.data.mcode_name
                                content = res.data.content
                                loc = res.data.ccba_lcad
                            })
                        } catch(err) {console.log(err)}
                    }
                    await getdetail()
                    // 모달 내용 변경
                    // 이미지 변경
                    var detailimg = document.getElementById("detailimg");
                    detailimg.src = img;
                    // 문화재명
                    var detailtitle = document.getElementById("detailtitle");
                    detailtitle.innerHTML = name;
                    // 문화재 종류
                    var detailkind = document.getElementById("detailkind");
                    detailkind.innerHTML = "분류: "+kind;
                    // 문화재 상세설명
                    var detailcontent = document.getElementById("detailcontent");
                    detailcontent.innerHTML = content;
                    //문화재 주소
                    var detailaddress = document.getElementById("detailaddress")
                    detailaddress.innerHTML = "위치: "+loc
                };
            }
            // clusterer에 마커들을 추가.(중요)
            clusterer.addMarkers(markers)
        },

        //////////////////////////////////////////////////////////////////////////////
        // keyword 검색을 위한 Method
        keywordMarker: async (keyword) => {
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                mapOption = {
                    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                };  
            // 지도를 생성합니다    
            var map = new kakao.maps.Map(mapContainer, mapOption); 

            // 경로거리 계산을 위한 코드 시작
            var drawingFlag = false; // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
            var moveLine; // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
            var clickLine // 마우스로 클릭한 좌표로 그려질 선 객체입니다
            var distanceOverlay; // 선의 거리정보를 표시할 커스텀오버레이 입니다
            var dots = {}; // 선이 그려지고 있을때 클릭할 때마다 클릭 지점과 거리를 표시하는 커스텀 오버레이 배열입니다.

            // 지도에 클릭 이벤트를 등록합니다
            // 지도를 클릭하면 선 그리기가 시작됩니다 그려진 선이 있으면 지우고 다시 그립니다
            kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

                // 마우스로 클릭한 위치입니다 
                var clickPosition = mouseEvent.latLng;

                // 지도 클릭이벤트가 발생했는데 선을 그리고있는 상태가 아니면
                if (!drawingFlag) {

                    // 상태를 true로, 선이 그리고있는 상태로 변경합니다
                    drawingFlag = true;
                    
                    // 지도 위에 선이 표시되고 있다면 지도에서 제거합니다
                    deleteClickLine();
                    
                    // 지도 위에 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
                    deleteDistnce();

                    // 지도 위에 선을 그리기 위해 클릭한 지점과 해당 지점의 거리정보가 표시되고 있다면 지도에서 제거합니다
                    deleteCircleDot();
                
                    // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
                    clickLine = new kakao.maps.Polyline({
                        map: map, // 선을 표시할 지도입니다 
                        path: [clickPosition], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
                        strokeWeight: 3, // 선의 두께입니다 
                        strokeColor: '#FF8A80', // 선의 색깔입니다
                        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
                        strokeStyle: 'solid' // 선의 스타일입니다
                    });
                    
                    // 선이 그려지고 있을 때 마우스 움직임에 따라 선이 그려질 위치를 표시할 선을 생성합니다
                    moveLine = new kakao.maps.Polyline({
                        strokeWeight: 3, // 선의 두께입니다 
                        strokeColor: '#FF8A80', // 선의 색깔입니다
                        strokeOpacity: 0.5, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
                        strokeStyle: 'solid' // 선의 스타일입니다    
                    });
                
                    // 클릭한 지점에 대한 정보를 지도에 표시합니다
                    displayCircleDot(clickPosition, 0);

                        
                } else { // 선이 그려지고 있는 상태이면

                    // 그려지고 있는 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();

                    // 좌표 배열에 클릭한 위치를 추가합니다
                    path.push(clickPosition);
                    
                    // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
                    clickLine.setPath(path);

                    var distance = Math.round(clickLine.getLength());
                    displayCircleDot(clickPosition, distance);
                }
            });
                
            // 지도에 마우스무브 이벤트를 등록합니다
            // 선을 그리고있는 상태에서 마우스무브 이벤트가 발생하면 그려질 선의 위치를 동적으로 보여주도록 합니다
            kakao.maps.event.addListener(map, 'mousemove', function (mouseEvent) {

                // 지도 마우스무브 이벤트가 발생했는데 선을 그리고있는 상태이면
                if (drawingFlag){
                    
                    // 마우스 커서의 현재 위치를 얻어옵니다 
                    var mousePosition = mouseEvent.latLng; 

                    // 마우스 클릭으로 그려진 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();
                    
                    // 마우스 클릭으로 그려진 마지막 좌표와 마우스 커서 위치의 좌표로 선을 표시합니다
                    var movepath = [path[path.length-1], mousePosition];
                    moveLine.setPath(movepath);    
                    moveLine.setMap(map);
                    
                    var distance = Math.round(clickLine.getLength() + moveLine.getLength()), // 선의 총 거리를 계산합니다
                        content = '<div class="dotOverlay distanceInfo jua">총거리 <span class="number">' + distance + '</span>m</div>'; // 커스텀오버레이에 추가될 내용입니다
                    
                    // 거리정보를 지도에 표시합니다
                    showDistance(content, mousePosition);   
                }             
            });                 

            // 지도에 마우스 오른쪽 클릭 이벤트를 등록합니다
            // 선을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면 선 그리기를 종료합니다
            kakao.maps.event.addListener(map, 'rightclick', function () {

                // 지도 오른쪽 클릭 이벤트가 발생했는데 선을 그리고있는 상태이면
                if (drawingFlag) {
                    
                    // 마우스무브로 그려진 선은 지도에서 제거합니다
                    moveLine.setMap(null);
                    moveLine = null;  
                    
                    // 마우스 클릭으로 그린 선의 좌표 배열을 얻어옵니다
                    var path = clickLine.getPath();
                
                    // 선을 구성하는 좌표의 개수가 2개 이상이면
                    if (path.length > 1) {

                        // 마지막 클릭 지점에 대한 거리 정보 커스텀 오버레이를 지웁니다
                        if (dots[dots.length-1].distance) {
                            dots[dots.length-1].distance.setMap(null);
                            dots[dots.length-1].distance = null;    
                        }

                        var distance = Math.round(clickLine.getLength()), // 선의 총 거리를 계산합니다
                            content = getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다
                            
                        // 그려진 선의 거리정보를 지도에 표시합니다
                        showDistance(content, path[path.length-1]);  
                        
                    } else {

                        // 선을 구성하는 좌표의 개수가 1개 이하이면 
                        // 지도에 표시되고 있는 선과 정보들을 지도에서 제거합니다.
                        deleteClickLine();
                        deleteCircleDot(); 
                        deleteDistnce();

                    }
                    
                    // 상태를 false로, 그리지 않고 있는 상태로 변경합니다
                    drawingFlag = false;          
                }  
            });    

            // 클릭으로 그려진 선을 지도에서 제거하는 함수입니다
            function deleteClickLine() {
                if (clickLine) {
                    clickLine.setMap(null);    
                    clickLine = null;        
                }
            }

            // 마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하거
            // 마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
            function showDistance(content, position) {
                
                if (distanceOverlay) { // 커스텀오버레이가 생성된 상태이면
                    
                    // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
                    distanceOverlay.setPosition(position);
                    distanceOverlay.setContent(content);
                    
                } else { // 커스텀 오버레이가 생성되지 않은 상태이면
                    
                    // 커스텀 오버레이를 생성하고 지도에 표시합니다
                    distanceOverlay = new kakao.maps.CustomOverlay({
                        map: map, // 커스텀오버레이를 표시할 지도입니다
                        content: content,  // 커스텀오버레이에 표시할 내용입니다
                        position: position, // 커스텀오버레이를 표시할 위치입니다.
                        xAnchor: 0,
                        yAnchor: 0,
                        zIndex: 3  
                    });      
                }
            }

            // 그려지고 있는 선의 총거리 정보와 
            // 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
            function deleteDistnce () {
                if (distanceOverlay) {
                    distanceOverlay.setMap(null);
                    distanceOverlay = null;
                }
            }

            // 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여 
            // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
            function displayCircleDot(position, distance) {

                // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
                var circleOverlay = new kakao.maps.CustomOverlay({
                    content: '<span class="dot"></span>',
                    position: position,
                    zIndex: 1
                });

                // 지도에 표시합니다
                circleOverlay.setMap(map);

                if (distance > 0) {
                    // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
                    var distanceOverlay = new kakao.maps.CustomOverlay({
                        content: '<div class="dotOverlay jua">거리 <span class="number">' + distance + '</span>m</div>',
                        position: position,
                        yAnchor: 1,
                        zIndex: 2
                    });

                    // 지도에 표시합니다
                    distanceOverlay.setMap(map);
                }

                // 배열에 추가합니다
                dots.push({circle:circleOverlay, distance: distanceOverlay});
            }

            // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
            function deleteCircleDot() {
                var i;

                for ( i = 0; i < dots.length; i++ ){
                    if (dots[i].circle) { 
                        dots[i].circle.setMap(null);
                    }

                    if (dots[i].distance) {
                        dots[i].distance.setMap(null);
                    }
                }

                dots = [];
            }

            // 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여 
            // 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
            // HTML Content를 만들어 리턴하는 함수입니다
            function getTimeHTML(distance) {

                // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
                var walkkTime = distance / 67 | 0;
                var walkHour = '', walkMin = '';

                // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
                if (walkkTime > 60) {
                    walkHour = '<span class="number">' + Math.floor(walkkTime / 60) + '</span>시간 '
                }
                walkMin = '<span class="number">' + walkkTime % 60 + '</span>분'

                // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
                var bycicleTime = distance / 227 | 0;
                var bycicleHour = '', bycicleMin = '';

                // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
                if (bycicleTime > 60) {
                    bycicleHour = '<span class="number">' + Math.floor(bycicleTime / 60) + '</span>시간 '
                }
                bycicleMin = '<span class="number">' + bycicleTime % 60 + '</span>분'

                // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
                var content = '<ul class="dotOverlay distanceInfo jua">';
                content += '    <li>';
                content += '        <span class="label">총거리</span><span class="number">' + distance + '</span>m';
                content += '    </li>';
                content += '    <li>';
                content += '        <span class="label">도보</span>' + walkHour + walkMin;
                content += '    </li>';
                content += '    <li>';
                content += '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
                content += '    </li>';
                content += '</ul>'

                return content;
            }// getTimehtml 종료부분 // 경로계산 종료

            // 마커 이미지를 커스텀하기 위한 코드
            var imageSrc = require('@/assets/heritage.png'), // 마커이미지의 주소입니다    
                imageSize = new kakao.maps.Size(50, 40), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

            // 장소 검색 객체를 생성합니다
            var ps = new kakao.maps.services.Places();

            // 문화재 정보 검색
            const getheritages = async() => {
                try {
                    const result = await axios.get(`${API_BASE_URL}/heritages/${keyword}`)
                    .then((res)=>{
                        const heritages = res.data
                        return heritages  
                    })
                    return result
                } catch(err) {console.log(err)}
            }
            const keywordheritages = await getheritages()
            // 키워드로 장소를 검색합니다
            ps.keywordSearch(keyword, placesSearchCB);
            // 키워드 검색 완료 시 호출되는 콜백함수 입니다
            function placesSearchCB (data, status) {
                if (status === kakao.maps.services.Status.OK) {
                    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                    // LatLngBounds 객체에 좌표를 추가합니다
                    var bounds = new kakao.maps.LatLngBounds();
                    console.log(data)
                    for (var i=0; i<keywordheritages.length; i++) {
                        displayMarker(keywordheritages[i]);  
                        bounds.extend(new kakao.maps.LatLng(keywordheritages[i].latitude, keywordheritages[i].longitude));
                    }       

                    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                    map.setBounds(bounds);
                } 
            }
            // 지도에 마커를 표시하는 함수입니다(마커 표시와 동시에 마우스오버와 클릭 이벤트도 같이 생성.)
            function displayMarker(place) {
                var markerPosition = new kakao.maps.LatLng(place.latitude, place.longitude);

                // 커스텀 오버레이를 생성합니다
                var customOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    position: markerPosition,
                    yAnchor: 10
                });
                // map에 커스텀오버레이 표시
                customOverlay.setMap(map);
                
                // 마커를 생성하고 지도에 표시합니다
                var marker = new kakao.maps.Marker({
                    map: map,
                    image: markerImage,
                    position: markerPosition,
                    clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
                });
                // 마커에 표시할 인포윈도우 생성
                // 인포위도우 커스터마이징(정보 및 이미지 담고 구조랑 디자인 짜기)
                    var ccbaKdcd = place.ccba_kdcd
                    var ccbaAsno = place.ccba_asno
                    var ccbaCtcd = place.ccba_ctcd   
                    var imageSrc= place.img_url
    
                    // 변수명 사용을 위해 분기점을 나눠준다.
                    var content0 = '<div class="infoHeader jua"><text>클릭하면 상세정보를 볼 수 있어요</text></div>'
                    var content1 = '<img class="infoImg" src="'
                    var content2 = imageSrc
                    var content3 = '">'
                    var content4 = '<div class="infoTitle"><text class="east-sea-Dokdo">'
                    var content5 = place.ccba_mnm
                    var content6 = '</text></div>'
    
                    var infowindow = new kakao.maps.InfoWindow({
                        content: content0+content1+content2+content3+content4+content5+content6 // 인포윈도우에 표시할 내용
                    });
                // 마커에 마우스오버 이벤트를 등록합니다
                kakao.maps.event.addListener(marker, 'mouseover', function() {
                // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
                    infowindow.open(map, marker);
                });

                // 마커에 마우스아웃 이벤트를 등록합니다
                kakao.maps.event.addListener(marker, 'mouseout', function() {
                    // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
                    infowindow.close();
                });
                // 마커에 클릭이벤트를 등록하기 위한 코드 시작.
                kakao.maps.event.addListener(marker, 'click', makeClickListner(ccbaKdcd,ccbaAsno,ccbaCtcd, imageSrc));
            } // displayMarker 종료부분
            function makeClickListner(ccbaKdcd,ccbaAsno,ccbaCtcd,img) {
                return async () => {
                    // 마커 클릭시 모달 열람 기능 추가
                    var modal = document.getElementById("myModal");
                    modal.style.display = "block";
                    // When the user clicks on <span> (x), close the modal
                    var span = document.getElementsByClassName("close")[0];
                    span.onclick = function() {
                        modal.style.display = "none";
                    }
                    // When the user clicks anywhere outside of the modal, close it
                    window.onclick = function(event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                        }
                    }
                    // axios 요청
                    var name = null; 
                    var kind = null; 
                    var content = null; 
                    var loc = null
                    const getdetail = async () => {
                        try {
                            await axios.get(`${API_BASE_URL}/heritage?asno=${ccbaAsno}&ctcd=${ccbaCtcd}&kdcd=${ccbaKdcd}`)
                            .then((res) => {
                                name = res.data.ccba_mnm
                                kind = res.data.mcode_name
                                content = res.data.content
                                loc = res.data.ccba_lcad
                            })
                        } catch(err) {console.log(err)}
                    }
                    await getdetail()
                    // 모달 내용 변경
                    // 이미지 변경
                    var detailimg = document.getElementById("detailimg");
                    detailimg.src = img;
                    // 문화재명
                    var detailtitle = document.getElementById("detailtitle");
                    detailtitle.innerHTML = name;
                    // 문화재 종류
                    var detailkind = document.getElementById("detailkind");
                    detailkind.innerHTML = "분류: "+kind;
                    // 문화재 상세설명
                    var detailcontent = document.getElementById("detailcontent");
                    detailcontent.innerHTML = content;
                    //문화재 주소
                    var detailaddress = document.getElementById("detailaddress")
                    detailaddress.innerHTML = "위치: "+loc
                };
            }
        }, // keyword Marker 종료부분
    }, // methods 종료부분 
}
</script>

<style>
/* search bar */
@import url(https://fonts.googleapis.com/css?family=Open+Sans);

body{
  background: #f2f2f2;
  font-family: 'Open Sans', sans-serif;
}

.mainHeader {
    width: 85rem;
    margin-bottom: 1%;
    margin-left: auto;
    margin-right: auto;
    padding: 2%;
    text-align: left;
}

.search {
  width: 85rem;
  position: relative;
  display: flex;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 1%;
}

.searchTerm {
  width: 100%;
  border: 4px solid #263238;
  border-right: none;
  padding: 5px;
  height: 50px;
  border-radius: 5px 3px 5px 5px;
  outline: none;
  color: #263238;
}

.searchTerm:focus{
  color: #263238;
}

.searchButton {
  width: 50px;
  height: 50px;
  border-left: 3px solid #263238;
  background: #263238;
  text-align: center;
  color: #fff;
  border-radius: 3px 5px 5px 5px;
  cursor: pointer;
  font-size: 25px;
}

/* kakaomap */
.kakaomap {
    width:85rem; height:60rem; margin-left:auto; margin-right:auto;
    z-index: 0;
}

/* 마우스오버 Info Window */
.infoHeader {
    font-size: 16px;
    color: black;
    margin: 0.5rem 0rem 0rem 0rem;

}
.infoImg {
    width: auto; height: auto;
    max-width: 25rem;
    max-height: 15rem;
    padding: 0.5rem 0.5rem 0.5rem 0.5rem;
}
.infoTitle {
    background-color: #513F42;
    color: white;
    margin: 0;
    font-size: 36px;
}

/* 문화재 상세정보 조회 창 */
/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width:100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  height: 65%;
}
/* 모달 이미지 */
.detailimagediv {
    position: absolute;
    left: auto;
    margin-top: 0%;
    width: 36.5%;
    height: 55%;
}
.detailimage {
    max-width: 100%;
    max-height: 100%;
}
/* 모달 콘텐츠들 */
.detailcontents {
    position: absolute;
    right: 13%;
    width: 38%;
    height: 53%;
}
/* 모달 문화재 명 */
.detailtitle {
    margin-top: 0%;
    padding: 1% 1% 1% 1%;
    height: 15%;
    font-size: 44px;
    background-color:  #513F42;
    color: white;
}
.detailkind {
    position: relative;
    padding-top: 3%;
    left: 0%;
    font-size: 20px;
    text-align: left;
}
/* 모달 문화재 상세설명 */
.detaildescription {
    position: relative;
    margin-top: 0%;
    padding: 1% 1% 1% 1%;
    width: 100%;
    height: 55%;
    overflow: auto; /* Enable scroll if needed */
    border: 5px solid #E2D6CE;
    border-left-color:white;
    border-right-color: white;
    text-align: left;
}
.detailaddress {
    position: relative;
    margin-top: 2%;
    font-size: 20px;
    text-align: left;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 40px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

/* 지도 거리계산 부분 */
.dot {overflow:hidden;float:left;width:12px;height:12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png');}    
.dotOverlay {position:relative;bottom:10px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;font-size:15px;padding:5px;background:#fff;}
.dotOverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}    
.number {font-weight:bold;color:#ee6152;}
.dotOverlay:after {content:'';position:absolute;margin-left:-6px;left:50%;bottom:-8px;width:11px;height:8px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png')}
.distanceInfo {position:relative;top:5px;left:5px;list-style:none;margin:0;}
.distanceInfo .label {display:inline-block;width:50px;}
.distanceInfo:after {content:none;}
</style>