# 🏠 집넘기기
<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189599604-36c291f8-821f-461d-aa5f-31564292c0bb.png" width="900" height="430"/></p>

## 🚀 프로젝트 기획 의도
월세, 전세, 매매를 중개해주는 플랫폼은 많지만 집을 양도를 중개해주는 플랫폼은 존재하지 않습니다. 
  
  
금전적으로 여유롭지 못하거나 급하게 임시로 집을 구해야하는 사람들에게는 양도로 집을 구하는 것이 효율적일 수 있다고 생각합니다.  
  
  
따라서 저희는 이러한 양도를 중개해주는 플랫폼을 앱으로 만들어 보았습니다. 

</br>

## 🔥 팀원 소개
|Name|Part|Github|
|---|---|---|
|Stark|android|[jminie-o8o](https://github.com/jminie-o8o)|
|Funny|android|[ese111](https://github.com/ese111)|
|Lee|backend|[street62](https://github.com/street62)|
|케이|backend|[leekm0310](https://github.com/leekm0310)|
|Eva|designer|[Eva](https://evachu.design)|

</br>

## 📃 Project Rule

리포지토리 내 위키에 프로젝트를 진행하면서 준수할 규칙들을 정리해놓았습니다.
- [Ground Roll 바로가기](https://github.com/street62/Home-Rent-App/wiki/%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%A1%A4)
- [회의록 바로가기](https://github.com/street62/Home-Rent-App/wiki/%ED%9A%8C%EC%9D%98%EB%A1%9D)
- [브랜치 전략 바로가기](https://github.com/street62/Home-Rent-App/wiki/%ED%98%91%EC%97%85-%EC%A0%84%EB%9E%B5)
- [Android Convention Roll 바로가기](https://github.com/street62/Home-Rent-App/wiki/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BB%A8%EB%B2%A4%EC%85%98)

</br>

## ⚙ 주요 기능
- 카카오, 네이버 소셜로그인 및 자동로그인
- 양도글, 양수글 검색
- 양도글, 양수글 추가
- 매물을 올린 사람과 채팅
- 원하는 매물 관심목록에 추가, 삭제
- 내가 올린 매물 보기, 삭제
- 내 프로필 등록, 삭제
- 로그아웃

</br>

## 📌 안드로이드 기술 스택
- Architecture
  - MVVM Architecture ( View - Databinding - ViewModel - Model )
- 언어 [Kotlin](https://kotlinlang.org/)
- 비동기 및 반응형 프로그래밍을 위한 [Coroutines](https://developer.android.com/kotlin/coroutines) + [Flow](https://developer.android.com/kotlin/flow)
- [Jetpack](https://developer.android.com/jetpack)
  - UI 관련 데이터를 저장하고 관리하기 위한 [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjwq5-WBhB7EiwAl-HEkrzYCgxFBbYLSC4yenlZRy5NtxWbTHP-xThSz_yMY_JUTl3TCklhnBoCDIcQAvD_BwE&gclsrc=aw.ds)
  - 데이터 목록을 표시하기 위한 [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=CjwKCAjwsfuYBhAZEiwA5a6CDExaCCkZkSghgMvbHs6a80REENk05bEMNW1cW1j37ZAeNJrmF1efxRoCnr0QAvD_BwE&gclsrc=aw.ds)
  - 앱내 화면 이동을 위한 [Navigation](https://developer.android.com/guide/navigation)
  - 앱내 간단한 데이터 저장을 위한 [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQjwjvaYBhDlARIsAO8PkE0p3ASRb3EbJTGUtzY1aiUiGqQJwR9n8dEeo1g76RxQjpOciuUP5-QaAr6eEALw_wcB&gclsrc=aw.ds)
  - 이미지 슬라이더를 위한 [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2)
  - 데이터 페이징을 위한 [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- REST API 통신을 위한 [Retrofit](https://square.github.io/retrofit/)
- 이미지 로더를 위한 [Coil](https://square.github.io/retrofit/)
- DI를 위한 [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- 채팅을 위한 [Stream SDK](https://getstream.io/chat/?utm_source={google}&utm_medium={cpc}&utm_campaign=GOO|S|BRN|ROW|ALL-EN%26utm_adgroup=Chat%26utm_custom%3D15702481917%26utm_content%3D571825906719%26utm_term%3Dstream%20chat%20api%26matchtype=e%26device=c%26location=1009871&_bt=571825906719&_bk=stream%20chat%20api&_bm=e&_bn=g&gclid=CjwKCAjwsfuYBhAZEiwA5a6CDAJOBXFLKt0q8_ib1I1_fwzsfxM4Ii2B3aKzyNZ8O6xY42BcG_VVzRoCJPwQAvD_BwE)
- 주소 검색을 위한 [다음 우편번호 API](https://postcode.map.daum.net/guide)
- 주소를 지도에 표현하기 위한 [Naver Cloud Maps API](https://www.ncloud.com/product/applicationService/maps)

</br>

## 📱 동작 화면
<Blockquote>
집넘기기의 실제 동작화면 입니다.🏠
</Blockquote>

| 로그인 및 프로필 등록 | 양도글 추가 | 양수글 추가 | 매물 검색 |
|:--------:|:--------:|:--------:|:--------:|
| ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EB%A1%9C%EA%B7%B8%EC%9D%B8%20%EB%B0%8F%20%ED%94%84%EB%A1%9C%ED%95%84%20%EB%93%B1%EB%A1%9D.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EC%96%91%EB%8F%84%EA%B8%80%20%EB%93%B1%EB%A1%9D.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EC%96%91%EC%88%98%EA%B8%80%20%EB%93%B1%EB%A1%9D.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EB%A7%A4%EB%AC%BC%20%EA%B2%80%EC%83%89.gif) |

| 채팅 | 관심목록 추가 및 제거 | 프로필 | 로그아웃 |
|:--------:|:--------:|:--------:|:--------:|
| ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EC%B1%84%ED%8C%85.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EA%B4%80%EC%8B%AC%EB%AA%A9%EB%A1%9D%20%EC%B6%94%EA%B0%80%20%EB%B0%8F%20%EC%A0%9C%EA%B1%B0.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%ED%94%84%EB%A1%9C%ED%95%84.gif) | ![](https://github.com/street62/Home-Rent-App/blob/android_develop/screenshot/%EB%A1%9C%EA%B7%B8%EC%95%84%EC%9B%83.gif) |

</br>

## 시연영상
<Blockquote>
이미지를 클릭하면 시연영상 링크로 이동합니다. 
</Blockquote>
 
[![시연영상 바로가기](https://i9.ytimg.com/vi/EhMm6OVVgiE/mq1.jpg?sqp=CLC4_ZgG&rs=AOn4CLA50qaec9-FhblWkf03fNxdExFqKQ)](https://www.youtube.com/watch?v=EhMm6OVVgiE)
