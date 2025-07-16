# 메타버스 기반 커피 자판기
좋아요 jong님! Unity에서 **커피 자판기 + 사용자 상호작용 프로젝트**를 단계별로 구현하는 방법을 아래에 구조화해서 안내드릴게요. 실습 기반으로 따라 하실 수 있도록 구성했어요 ☕🎮

---

## 🏁 1단계: Unity 프로젝트 환경 설정

🔹 **Unity 설치**
- Unity Hub 설치 후 버전은 `2020.3 LTS` 이상 추천

🔹 **새 프로젝트 생성**
- 3D 템플릿으로 `CoffeeSimulation` 프로젝트 생성

🔹 **폴더 구조 정리**
```plaintext
Assets/
 ┣ Scripts/
 ┣ Prefabs/
 ┣ Materials/
 ┣ Models/
 ┗ UI/
```

---

## 📦 2단계: 자판기와 사용자 아바타 모델 구현

🔹 **자판기 모델**
- Unity Asset Store 또는 Blender에서 가져오기
- 버튼, 컵 슬롯, 화면 등 파트별로 나눠 Prefab으로 구성

🔹 **사용자 아바타**
- 기본 Capsule 캐릭터로 시작 가능
- 이후 Mixamo나 Ready Player Me로 실제 사람 같은 아바타 적용 가능
- NavMesh 또는 Character Controller로 이동 구현

---

## 🧠 3단계: 커피 자판기 동작 스크립트 작성

🔹 **스크립트 예시 (CoffeeMachine.cs)**
```csharp
public class CoffeeMachine : MonoBehaviour
{
    public GameObject coffeeCupPrefab;
    public Transform cupSpawnPoint;
    private int stock = 10;

    public void ServeCoffee()
    {
        if (stock > 0)
        {
            Instantiate(coffeeCupPrefab, cupSpawnPoint.position, Quaternion.identity);
            stock--;
        }
    }
}
```

🔹 **버튼 누름 감지**
- 버튼에 Collider + `OnMouseDown()` 또는 `EventTrigger` 활용

---

## 👣 4단계: 사용자 상호작용 구현

🔹 **아바타 이동**
- `NavMeshAgent` 활용하여 자판기 앞까지 자동 이동

🔹 **상호작용 트리거**
- 자판기 근처에 들어오면 UI 팝업 활성화 (`Press E to order coffee`)
- 사용자가 E 키 누르면 `CoffeeMachine.ServeCoffee()` 실행

🔹 **컵 잡기와 마시기**
- 컵을 아바타 손 위치에 `transform.parent = handTransform;`로 연결
- 마시는 애니메이션은 `Animator`에서 설정

---

## 🎨 5단계: UI 구성

🔹 **Canvas UI**
- 커피 종류 선택 버튼 (`Espresso`, `Latte`, `Mocha`)
- 재고 표시 / 사용자 포인트 표시
- 버튼 클릭 시 음료 처리 로직 연결

🔹 **사용자 피드백**
- “커피를 받았습니다!” 메시지
- 포인트 차감 또는 잔액 표시

---

## 🔄 6단계: 확장 기능 (선택)

💡 아래 기능은 추가적으로 적용 가능해요:
- ☕ 음료 종류에 따른 컵 색상/속성 변경
- 💳 포인트 시스템 (잔액 소모 / 적립)
- 👥 멀티 유저 대응 (Photon Unity Network 연동)
- 🧍‍♀️ NPC 등장 → 대화 → 추천 커피 제공

---

## ✅ 7단계: 최종 빌드 및 테스트

🔹 **기기 테스트**: PC, Android, WebGL 등으로 빌드 후 확인  
🔹 **버그 수정 & 최적화**: FPS 저하, 충돌 문제, UI 오류 등 개선

---
