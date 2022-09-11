# 집넘기기
  
#### 팀원소개
|Name|Part|Github|
|---|---|---|
|Stark|android|[jminie-o8o](https://github.com/jminie-o8o)|
|Funny|android|[ese111](https://github.com/ese111)|
|Lee|backend|[street62](https://github.com/street62)|
|케이|backend|[leekm0310](https://github.com/leekm0310)|
|Eva|designer|[Eva](https://evachu.design)|

## Project Rule

리포지토리 내 위키에 프로젝트를 진행하면서 준수할 규칙들을 정리해놓았습니다.
- [Ground Roll 바로가기](https://github.com/street62/Home-Rent-App/wiki/%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%A1%A4)
- [회의록 바로가기](https://github.com/street62/Home-Rent-App/wiki/%ED%9A%8C%EC%9D%98%EB%A1%9D)
- [브랜치 전략 바로가기](https://github.com/street62/Home-Rent-App/wiki/%ED%98%91%EC%97%85-%EC%A0%84%EB%9E%B5)
- [Android Convention Roll 바로가기](https://github.com/street62/Home-Rent-App/wiki/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BB%A8%EB%B2%A4%EC%85%98)


##  기능
- 카카오, 네이버 소셜로그인 및 자동로그인
- 양도글, 양수글 검색
- 양도글, 양수글 추가
- 매물을 올린 사람과 채팅
- 원하는 매물 관심목록에 추가, 삭제
- 내가 올린 매물 보기, 삭제
- 내 프로필 등록, 삭제
- 로그아웃

## 안드로이드 기술 스택
- Architecture
  - MVVM Architecture ( View - Databinding - ViewModel - Model )
- 100% [Kotlin](https://kotlinlang.org/)
- 비동기 및 반응형 프로그래밍 [Coroutines](https://developer.android.com/kotlin/coroutines) + [Flow](https://developer.android.com/kotlin/flow)
- [Jetpack](https://developer.android.com/jetpack)
  - UI 관련 데이터를 저장하고 관리하는 [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAjwq5-WBhB7EiwAl-HEkrzYCgxFBbYLSC4yenlZRy5NtxWbTHP-xThSz_yMY_JUTl3TCklhnBoCDIcQAvD_BwE&gclsrc=aw.ds)
  - 앱내 화면 이동을 위한 [Navigation](https://developer.android.com/guide/navigation)
  - 앱내 간단한 데이터 저장을 위한 [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=Cj0KCQjwjvaYBhDlARIsAO8PkE0p3ASRb3EbJTGUtzY1aiUiGqQJwR9n8dEeo1g76RxQjpOciuUP5-QaAr6eEALw_wcB&gclsrc=aw.ds)
  - 이미지 슬라이더 [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2)
  - 데이터 페이징을 위한 [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- REST API 통신을 위한 [Retrofit](https://square.github.io/retrofit/)
- 이미지 로더 라이브러리 [Coil](https://square.github.io/retrofit/)
- DI를 위한 [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
-  [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)


| 회원가입 및 로그인  | GitHub OAuth 로그인 | 이슈 추가 | 이슈 닫기 |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/183874034-4253dc6c-7c72-4e4a-a0f5-2df3a06d2960.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183874953-b8c4ee09-9dec-4d38-9e9f-f4b94d18e7a7.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183877513-b1838be6-5e5a-4462-b437-1a1835411447.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183875847-1b086c37-3289-4f0a-8b12-aadf7d1731d6.gif width=200> |

| 이슈 필터 | 이슈 검색 | 레이블 추가 | 마일스톤 추가 |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/183876156-8cc0684f-181c-4ed8-a021-ddcba04f0a43.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183877330-6bd6b04e-833c-40f5-b03c-399ac53ed2c7.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/177966815-7abcda81-aca1-487d-bd36-847e304eacf8.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/183878295-cf489780-46ea-4faf-8bec-a7019e292e1f.gif width=200> |

## MAD Scorecard
<img src="https://user-images.githubusercontent.com/79504043/177989379-37cf40dc-0e11-4944-a1a9-8a3ee113065b.png">  

