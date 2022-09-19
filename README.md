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

## 😎 안드로이드 기술적 고민
### 📌 코드 컨벤션 통일
<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189822483-f6f6bf50-11f2-416c-85ae-3185979b0042.png" width="560" height="300"/></p>

> 집넘기기🏠는 coding convention, style guide 준수를 위해 ktlint를 사용했습니다.  

</br>

### 🤷‍♂️ 왜 ktlint?

- 유지보수성, 결합도, 응집도 등의 특성들이 소프트웨어의 품질을 향상시키기 위한 전략이라고 한다면, 코드 품질을 개선하기 위한 전략으로는 코드 리뷰, 정적 분석, 코딩 컨벤션 등이 존재
- 프로젝트 내에서 코드 스타일이 강제로 통일되기 때문에 서로 다른 스타일로 작성될 일이 없고 모든 프로젝트 내의 코드가 통일성이 생김 
- 이를 통해 코드 리뷰 시에 서로 다른 스타일로 인한 피로감이 줄어들고 가독성 저하 또는 불필요한 소통의 비용이 줄어들게 됨

저희는 여러 정적 분석 도구 중 공식 style을 기본으로 제공하는 ktlint를 적용하기로 하였습니다.

실제로 이번 집넘기기 프로젝트를 하면서 ktlint를 적용하였더니 팀원의 PR 코드리뷰 때 
한 줄로 길게 쓴 코드가 사라지고 코드가 더 눈에 잘 들어와서 좋았습니다.

</br>

### ktlint 적용 전
```kotlin
private fun setDefaultResult() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(WantHomeResultRequest(keyword, true))             
        }
    }
```

### ktlint 적용 후
```kotlin
private fun setDefaultResult() {
        collectStateFlow(viewModel.searchWord) { keyword ->
            viewModel.getWantHomeResult(
                WantHomeResultRequest(
                    keyword,
                    true
                )
            )
        }
    }
```

</div>
</details>

### 📌 Clean Architecture & MVVM

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189823885-b9eddb33-861b-4bdf-b152-01ce5fac4a7e.png" width="560" height="400"/></p>

> 집넘기기🏠는 프로젝트 아키텍처 패턴으로 MVVM 패턴을 사용했습니다.

</br>

### 🤷‍♂️ 왜 아키텍처 패턴?

아키텍처패턴을 적용한 가장 큰 이유는 [안드로이드 공식문서](https://developer.android.com/topic/architecture)에서 말하는 **Seperation of concerns** 즉 **관심사의 분리**를 하기 위해서 입니다.

코드를 관심사 단위로 나누게 되면 한쪽에서 코드가 변경된다고 해도 다른쪽에서 신경쓸 필요가 없어지므로 유지 보수가 용이하다는 장점이 있습니다.

프로젝트를 꾸준히 유지보수 하기 위해 안드로이드 아키텍처패턴의 적용은 꼭 필요하다고 판단했습니다.

</br>

### 🤷‍♂️ 그렇다면 왜 MVVM?

**MVC 패턴의 단점**
- View와 Model 사이의 의존성이 높음
- Controller가 안드로이드에 종속되기 때문에 테스트가 어려워잠
- Controller에 많은 코드가 모이게 되어 Activity가 비대해잠
- 안드로이드 특성상 Activity가 View 표시와 Controller 역할을 같이 수행해야 하기 때문에 두 요소의 결합도가 높아잠

**MVP 패턴의 단점**
- View와 Presenter가 1:1로 강한 의존성을 가지게 됨
- 각각의 View마다 Presenter가 존재하게 되어서 코드량이 많아져 유지 보수가 힘들어질 수 있음

</br>

**이에 비해 MVVM 패턴은**
- View와 Model 사이의 의존성이 없음
- View는 ViewModel을 참조하지만 ViewModel은 View를 참조하지 않음
- 각각 부분이 독립적이라 모듈화 개발에 적합
- DataBinding을 함께 활용하면 View와 ViewModel 간의 의존성을 낮추고 View에서 처리하는 로직을 감소시킬 수 있음

이에 따라 집넘기기는 MVVM 패턴을 사용하게 되었습니다.

</div>
</details>


### 📌 의존성 주입 DI

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189824297-fff7ce52-6b99-40fa-835a-894678bb25e5.png" width="660" height="400"/></p>

> 집넘기기🏠는 의존성 주입(DI)을 위해 Hilt를 사용했습니다.

</br>

### 🤷‍♂️ 의존성주입이 필요한 이유?
- 의존성 주입을 사용하지 않는다면 클래스 내부에서 직접 의존 항목의 인스턴스를 생성하거나, 직접 DI 객체를 만들어 수동으로 의존성을 주입해야 합니다.
- 이러한 방식은 코드의 재사용이 어렵고 리팩토링이 힘듭니다. 또한 ViewModelFactory의 경우 보일러 플레이트 코드가 발생하게 됩니다.

```kotlin
// ViewModel이 Repository를 가지고 있고, Repository가 DataSource를, DataSource는 AssetLoader를 ...
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(HomeRepository(HomeAssetDataSource(AssetLoader(context)))) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                val repository = CategoryRepository(CategoryRemoteDataSource(ApiClient.create()))
                CategoryViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}
```

</br>

### 🤷‍♂️ 그렇다면 왜 Hilt?

안드로이드 DI 라이브러리로는 Dagger2, Koin 등의 다른 선택지도 있었지만
- 안드로이드 애플리케이션을 위한 Dagger와 관련 기반 코드들을 간소화
- 쉬운 설정과 가독성/이해도 그리고 앱간 코드 공유를 위한 표준 컴포넌트, 스코프 세트를 생성
- 다양한 빌드 유형에 대한 서로 다른 바인딩을 제공하는 쉬운 방법을 제공

위의 이유와 최근 기업 기술블로그를 보면 DI라이브러리를 Hilt로 이전하는 글을 많이 볼 수 있기에 Hilt를 선택했습니다.


</div>
</details>

### 📌 페이징

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189824780-88d9439f-9f1d-4d69-a663-53db92c6e195.jpeg" width="660" height="350"/></p>

> 집넘기기🏠는 서버에서 데이터 페이징 처리를 위해 **Paging3** 와 **Scrool Listener 를 통한 커스텀 구현** 2가지를 이용했습니다.

</br>

### 🤷‍♂️ 왜 페이징인가?

집 매물 데이터 전체를 한번에 요청하여 가져오는 경우, 아래와 같은 문제가 있었습니다.

- 과도한 데이터 요청에 따른 메모리, 데이터 손해

	⇒ 끝까지 피드를 보는 경우를 제외하면, 필요 이상의 메모리와 데이터가 소모됨

- Layer 사이의 불필요한 데이터 전달

	⇒ UI Layer, Data Layer 사이에서 필요 이상의 데이터를 주고 받음

즉 **속도는 빠르게, 부하는 적게** 하기 위해 지금 당장 필요한 데이터만 가져올 수 있도록 데이터를 분리하는 작업을 위해 페이징을 적용했습니다.

</br>

### 🤷‍♂️ 왜 두가지 방법을 사용했는가?

기본적으로 매물의 Item 삭제 로직이 없는 부분에서는 
- 페이징 된 데이터에 대한 인-메모리 캐싱을 지원, 앱이 페이징 된 데이터로 작업하는 동안 시스템 리소스를 효율적으로 사용할 수 있음
- 내장된 요청 중복 제거를 통해 앱이 네트워크 대역폭과 시스템 리소스를 효율적으로 사용
- 로드 한 데이터의 끝으로 스크롤 할 때 데이터를 자동으로 요청하는 구성이 가능한 RecyclerView 어댑터(PagingDataAdapter)를 제공
- 코틀린 코루틴과 Flow를 우선적으로 지원
- 오류 처리를 위한 기본적인 방법을 제공

위와 같은 이유로 Paging3 라이브러리를 사용했습니다.

</br>

하지만 데이터 삭제 로직을 수행하는데 이슈상황이 있었습니다.
기획상 관심목록, 내가 쓴 매물을 삭제하면 서버에 삭제 요청을 하면서 즉각적으로 UI에도 매물삭제가 반영되었어야하는데
특정 데이터 삭제 후 해당 데이터가 정상적으로 삭제 됐는지 콜백을 받고 Extention을 통해 삭제로직을 수행했을 때 UI에 반영되지 않았습니다.
```kotlin
// PaigingData로 감싸져 있다면 반응하지 않음
fun <E> MutableStateFlow<MutableList<E>>.removeElement(element: E) {
    if (element == null) {
        return
    }
    val tempMutableList = mutableListOf<E>()
    tempMutableList.addAll(this.value)
    tempMutableList.remove(element)
    this.value = tempMutableList
}
```
</br>

또한 콜백을 받고 refresh() 함수를 만들어 서버에서 새로운 데이터를 받아와도 전체 Size는 변화하였지만 index의 오류가 발생했습니다. 

</br>

![스크린샷 2022-09-13 오후 3 23 25](https://user-images.githubusercontent.com/79504043/189825604-3d83df8b-8349-4ca7-b4e4-e620700b17ea.png)

</br>

따라서 삭제 로직을 수행하는 부분에서는 Scrool Listener를 통한 커스텀 구현으로 페이징을 사용했습니다.


</div>
</details>


### 📌 자동 로그인

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189825339-4e046daa-f0cb-4396-80fa-3a405027088b.jpeg" width="660" height="350"/></p>

> 집넘기기🏠는 자동 로그인 구현을 위해 DataStore를 이용했습니다.

</br>

### 🤷‍♂️ 왜 DataStore?

유저의 토큰과 로그인 상태인지 아닌지를 저장하기 위해 SharedPrefernces와 DataStore 중 한 가지를 선택해야 했습니다.

SharedPreferences에는 다음과 같은 한계점이 존재했습니다.

- UI 스레드(메인 스레드)에서 호출할 수 있도록 API가 설계되었지만, UI 스레드를 블로킹해 ANR을 발생시킬 수 있음
- 런타임에 예외가 생기면 에러가 발생해 앱이 강제 종료될 수도 있음
- Type Safey가 보장되지 않아 어떤 데이터가 저장되고 추출되는지 일일히 데이터로 Type Convertind(형 변환) 해주어야 함

또한 공식문서에서도 만약 SharedPrefereces를 사용하고 있다면 DataStore로 이전할 것을 권고하고 있었습니다.

</br>

![공식문서 datastore](https://user-images.githubusercontent.com/79504043/189825824-b502a79c-509a-4836-8197-9edca1b66af5.png)

</br>

SharedPreferences에 비교하여 DataStore의 장점은 이러했습니다.
- 경량 스레드 모델을 구현하는 Coroutine을 사용해 내부를 구현함으로써 더욱 효율적으로 데이터를 저장할 수 있도록 함
	- 기존 UI 스레드에서 호출되어 ANR을 발생시킬 수 있었던 SharedPreferences와 다름
	- 내부에서 Coroutine의 IO Dispathcer를 사용해 IO를 담당하는 스레드 풀에서 데이터를 조작하도록 강제
	- 또한 Flow를 사용해 데이터를 추출할 수 있도록 만들어 데이터의 입출력을 모두 Coroutine에서 사용할 수 있도록 함
- Strong Consistency(강한 일관성)을 보장하는 Transaction API를 제공
	- Strong Consistency(강한 일관성)이란 다중 스레드 환경에서 안전하게 데이터를 입력하거나 조회하게 하는 것
	- 이를 통해 다중 스레드 환경에서 안전하게 데이터를 입력, 조회할 수 있도록 함
	- 이는 동시성 프로그래밍에서 중요한 요소

<p align="center"><img src="https://user-images.githubusercontent.com/79504043/189825944-fe84ba2e-581e-4e84-bdd3-db5683331e06.png" width="760" height="300"/></p>

위와 같은 이유로 DataStore를 선택하였습니다.

</div>
</details>

### 📌 JWT 갱신

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

> 집넘기기🏠는 유저 인증 유지를 위해 JWT의 RefreshToken을 사용했습니다.

### 🤷‍♂ 왜 Refresh JWT?

JWT를 이용할 경우 클라이언트에서 Access Token을 헤더에 넣어서 요청을 하게 되면 서버 측에서 Access Token을 확인을 하여 본인을 인증하고, 요청을 확인하게됩니다.
그러나 서버 측에서는 Access Token만 가지고는 정말 해당 클라이언트가 맞는지 확인이 불가능합니다.

그에 대한 해결책으로 저희 프로젝트에서는 Refresh Token을 이용해서 Access Token의 기간을 짧게 하고, 만료가 되면 Refresh Token과 함께 서버에 요청을 하여 Access Token을 갱신하고 다시 재요청을 보내는 방식으로 Access Token이 탈취당하더라도 금방 만료되어 다시 사용할 수 없도록 해서 피해를 최소화할 수 있도록 JWT를 갱신하는 방식을 채택하였습니다.

### Client Detail

안드로이드의 과제는 두 가지였습니다.
- 유저가 알지 못하게 재로그인을 하지않아도 토큰이 갱신 될 것
- 토큰이 갱신된 후 갱신된 토큰으로 보냈던 요청을 다시 보낼 것

이 두 가지의 문제를 해결하기 위해서 Okhttp3의 Authenticator와 Coroutine의 runblocking을 사용하였습니다.
- Access Token 만료 시 401코드가 내려옴
- 401이 응답으로 오면 Authenticator가 자동으로 Access Token과 Refresh Token을 헤더에 넣어주는 Interceptor를 이용해서 refresh 요청을 보냄
- 갱신된 토큰을 Appsession에 저장하고, 원래 진행하던 요청을 다시 요청

갱신된 토큰을 동기화하고 갱신된 토큰으로 다신 요청을 하기위해서 runblocking을 사용하여 잠시 스레드를 정지하였습니다.


</div>
</details>

### 📌 Flow를 이용한 다양한 기능

<details>
<summary>토글 접기/펼치기</summary>
<div markdown="1">

> 집넘기기🏠는 반응형 프로그래밍을 구현하기 위헤 **Flow**의 다양한 기능을 이용했습니다.	
	
### 🤷‍♂️ 왜 Flow?

- Coroutine Flow는 단일 값을 반환하는 suspend 함수와 다르게 순차적으로 여러값을 내보낼 수 있음
- 실시간으로 데이터를 내보내며 값을 소비하지 않고도 처리할 수 있는 장점이 있음

<img width="607" alt="Flow" src="https://user-images.githubusercontent.com/79504043/189902836-daefd6b7-54d2-4cd6-867f-796b01f772ca.png">

<br>
</br>

이 장점들을 이용하여 사용자의 이벤트를 받아서 처리하는 기능들을 구현하는데 Flow를 사용하였습니다.


### Client Detail

- 순간 검색기능
    - Flow의 debounce와 양방향 데이터 바인딩을 이용해서 사용자의 검색 내용을 바탕으로 검색을 합니다.
    - 사용자가 입력을 멈추면 해당 단어로 검색을 자동으로 시작합니다.
    - 양방향 데이터 바인딩으로 StateFlow에 값을 저장하고, StateFlow이 일정 시간 이후에 방출한 최신의 값을 이용해서 검색을 진행
    
- 다중클릭 방지
	- 좋아요 버튼같이 버튼 클릭으로 API 요청이 가는 경우 사용자가 악의(?)를 품고 연속으로 수 많은 클릭을 하게 되면 서버에 부하가 올 수도 있습니다.
	- 따라서 이를 방지하기 위해 일정 시간동안 들어온 값 중에서 가장 첫 번째 이벤트만 발행하고 나머지는 무시하는 RxJava의 throttleFirst() 기능이 필요했습니다.
	- 하지만 Flow에는 throttleFirst()의 기능을 하는 연산자가 없어 직접 확잠함수로 만들어 사용했습니다.

```kotlin
// 클릭 이벤트를 flow로 변환
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        this.trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

// 마지막 발행 시간과 현재 시간 비교해서 이벤트 발행, 나머지는 무시.
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > windowDuration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
```

</div>
</details>

</br>

## 시연영상
<Blockquote>
이미지를 클릭하면 시연영상 링크로 이동합니다. 
</Blockquote>
 
[![시연영상 바로가기](https://user-images.githubusercontent.com/79504043/189822255-776cf84d-057d-4f0a-8662-423253a6bd62.jpeg)](https://www.youtube.com/watch?v=EhMm6OVVgiE)


