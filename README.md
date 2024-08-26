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

<img src="https://github.com/tgyuuAn/SoccerFriends/assets/116813010/56102c46-30e3-4e94-a9e5-5e52bd30d1dc" width="700" height="750"/>

<a href="https://www.figma.com/file/sKZiEIi7zHhAbN3o7U8tQZ/SoccerFriends?type=design&node-id=0%3A1&mode=design&t=GrJgoYCewaWrAu09-1">Figma Link</a>
<br><br><br>

## Android 사용할 기술

- **MVVM**
- 
- **UI** : XML + databinding + navigation + ViewModel + Glide + Lottie + Material

- **DI** : hilt

- **Local** : Room + Prefrences dataStore
  
- **Remote** : Retrofit2 + OkHttp3 + Kotlinx Serialization

- **Test** : Junit4 + mockk

- **Firebase** : Analytics + Crashlytics + Authentication + Realtime DataBase
 
<br><br><br>

 ## Git Convention
 ### Commit Convention
 ``` kotlin
- [CHORE] : 동작에 영향 없는 코드 or 변경 없는 변경사항(주석 추가 등)
- [FEAT] : 새로운 기능 구현
- [ADD] : Feat 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성
- [FIX] : 버그, 오류 해결
- [DEL] : 쓸모없는 코드 삭제
- [DOCS] : README나 WIKI 등의 문서 수정
- [CORRECT] : 주로 문법의 오류나 타입의 변경, 이름 변경시
- [RENAME] : 파일 이름 변경시
- [REFACTOR] : 전면 수정
- [MERGE]: 다른 브랜치와 병합
```
`ex ) git commit -m "#1 [FEAT] 회원가입 기능 완료"`

<br>

### Branch Convention
``` kotlin
- [develop] : 최종 배포
- [feature] : 기능 추가
- [fix] : 에러 수정, 버그 수정
- [docs] : README, 문서
- [refactor] : 코드 리펙토링 (기능 변경 없이 코드만 수정할 때)
- [modify] : 코드 수정 (기능의 변화가 있을 때)
- [chore] : gradle 세팅, 위의 것 이외에 거의 모든 것
```
`ex) feature/#issue-user-api`
