📄 **WBS 엑셀 파일 다운로드**
👉 [LMS_WBS.xlsx 다운로드](sandbox:/mnt/data/LMS_WBS.xlsx)

📊 **Gantt 차트 이미지 다운로드**
👉 [LMS_Gantt.png 다운로드](sandbox:/mnt/data/LMS_Gantt.png)

---

# 📌 MyBatis 기반 DB 구조 / 업무 WBS 세분화

아래는 **LMS 프로젝트에서 MyBatis를 기반으로 하는 DB 및 데이터 접근 업무**를 별도로 세분화한 WBS입니다.

---

# ✅ **MyBatis 기반 DB 구조/업무 WBS**

## **6. MyBatis 기반 DB 및 Persistence Layer 개발**

### **6.1 DB 모델링**

* **6.1.1 요구사항 기반 엔터티 추출**
* **6.1.2 ERD 설계**
* **6.1.3 정규화 및 테이블 구조 설계**
* **6.1.4 인덱스 전략 수립**
* **6.1.5 관계 설정(1:N, N:N 등)**

---

### **6.2 MyBatis 설정 구성**

* **6.2.1 MyBatis 환경 설정파일(mybatis-config.xml) 정의**
* **6.2.2 DataSource & Connection Pool 설정(HikariCP)**
* **6.2.3 TypeAlias 설정**
* **6.2.4 TypeHandler 구현 또는 추가 필요 여부 확인**
* **6.2.5 Mapper 스캔 전략 설정**

---

### **6.3 매퍼 파일 개발 (Mapper XML)**

* **6.3.1 사용자(User) 매퍼 개발**

  * userMapper.xml 생성
  * select / insert / update / delete 쿼리 작성
  * 로그인 / 권한 / 프로필 조회 쿼리

* **6.3.2 강의(Course) 매퍼 개발**

  * 강의 생성/수정/삭제
  * 강의 목록 및 상세 조회
  * 강의별 사용자 권한 조회

* **6.3.3 콘텐츠(Content) 매퍼 개발**

  * 파일 업로드 기록 저장
  * 스트리밍 정보 조회
  * 콘텐츠 권한 및 접근 로그

* **6.3.4 과제/시험(Assignment/Exam) 매퍼 개발**

  * 제출물 관리
  * 자동 채점 대상 조회
  * 평가 결과 저장

* **6.3.5 알림/출석/통계 매퍼 개발**

  * 알림 히스토리
  * 출석 이벤트
  * 관리자 통계용 집계 쿼리

---

### **6.4 Repository 계층 개발**

* **6.4.1 CRUD 기반 Repository 인터페이스 작성**
* **6.4.2 MyBatis 매퍼와의 매핑 검증**
* **6.4.3 트랜잭션 적용 구간 정의**
* **6.4.4 Batch 처리 필요 여부 검토 및 구현**

---

### **6.5 DB 운영 및 품질 관리**

* **6.5.1 SQL 성능 테스트 / 튜닝**
* **6.5.2 Deadlock 및 Locking 전략 수립**
* **6.5.3 장기 실행 쿼리 모니터링**
* **6.5.4 DB 마이그레이션 스크립트 작성**
* **6.5.5 운영환경 리소스 모니터링(Connections, Slow Query)**

---

# 🔍 필요하면 더 만들어 드릴 수 있어요!

추가로 제공 가능합니다:

✅ **MyBatis 매퍼 XML 샘플 코드**
✅ **ERD(가상 데이터 기준) 자동 생성**
✅ **DB 테이블 CREATE SCRIPT 제작**
✅ **전체 WBS를 PDF로 정리한 문서**
✅ **LMS 전체 아키텍처 다이어그램**

원하시는 항목이 있으신가요?
