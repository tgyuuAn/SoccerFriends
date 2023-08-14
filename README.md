![image](https://github.com/tgyuuAn/SoccerFriends/assets/116813010/4922ffd1-fb7c-4b70-b9ca-1f3de802ca45)

<br><br><br>

## 주요 기능 (1차)

1. **점수판 기능** : 한 눈에 볼 수 있는 점수판, 경기시간 체크해주는 스톱워치
2. **포메이션/라인업** : 포메이션 및 라인업 설정 자유롭게 드래그 앤 드롭 가능
3. **내 팀 관리** : 선수 명, 포지션, 등번호, 선발/후보 등으로 우리 팀을 더 체계적으로 관리할 수 있게 해줌

<br>
※ 1차 MVP개발 까지는 모두 로컬에서만 작동합니다.

<br><br><br>

## 추가될 기능

1. **팀 초대** : 팀을 만들고 팀원을 초대하는 기능 추가
2. **팀 전적 기록판** : 다른 팀과의 경기 기록을 저장하여 승률과 같은 정보를 제공 
3. **전술판**: 포메이션 및 라인업 기능에서 애니메이션 기능을 추가하여, 축구공과 함께 녹화 기능 구현 예정
<br><br><br>
## UI/UX

<img src="https://github.com/tgyuuAn/SoccerFriends/assets/116813010/eb501bf0-ac98-4842-af65-eee09e1994c3" width="600" height="750"/>

<a href="https://www.figma.com/file/sKZiEIi7zHhAbN3o7U8tQZ/SoccerFriends?type=design&node-id=0%3A1&mode=design&t=GrJgoYCewaWrAu09-1">Figma Link</a>
<br><br><br>

## Android 앱 아키텍처

- **MVVM**
- **Clean Archietcture**
- **App + Presentation + domain + data 모듈화**
<br><br><br>

## 모듈 의존성 그래프

![image](https://github.com/tgyuuAn/SoccerFriends/assets/116813010/8fafced5-502d-4890-9e30-91949f78d102)
<br><br><br>

## Android 사용할 기술

- **UI** : XML + databinding + navigation + ViewModel + Glide + Lottie + Material

- **DI** : hilt

- **Local** : Room + Prefrences dataStore
  
- **Remote** : Retrofit2 + OkHttp3 + Kotlinx Serialization

- **Test** : Junit4 + Mockkito

- **Firebase** : Analytics + Crashlytics + Authentication + Realtime DataBase
  
<br><br><br>

## Git Flow

기본적으로 Git Flow 전략을 이용한다. 작업 시작 시 선행되어야 할 작업은 다음과 같다.
``` kotlin
1. Issue를 생성한다.
2. feature Branch를 생성한다.
3. Add - Commit - Push - Pull Request 의 과정을 거친다.
4. merge된 작업이 있을 경우, 다른 브랜치에서 작업을 진행 중이던 개발자는 본인의 브랜치로 merge된 작업을 Pull 받아온다.
5. 종료된 Issue와 Pull Request의 Label과 Project를 관리한다.
```
