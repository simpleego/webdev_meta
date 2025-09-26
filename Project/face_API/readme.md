# 얼굴인식으로 출석관리
> LMS 프로젝트에 Java Spring Boot와 순수 JavaScript 얼굴 인식을 결합하여
> 카메라 기반 자동 출석 관리 시스템을 구축하는 방법

전체 아키텍처는 다음과 같습니다.

1.  **Backend (Spring Boot)**: 학생 정보 및 얼굴 특징 데이터(Descriptor)를 관리하고, 출석 기록을 처리하는 REST API 서버 역할을 합니다.
2.  **Database (JPA)**: 학생 정보와 얼굴 특징 데이터를 영구적으로 저장합니다.
3.  **Frontend (HTML, Pure JavaScript)**: 웹캠으로 사용자의 얼굴을 촬영하고, `face-api.js` 라이브러리를 사용해 얼굴을 인식한 후, 인식 결과를 백엔드 서버로 전송합니다.

-----

### **프로젝트 구축 단계별 가이드**

#### **Phase 1: 프로젝트 환경설정 및 데이터베이스 설계**

가장 먼저 프로젝트의 뼈대를 만들고 데이터를 저장할 테이블을 설계합니다.

**1. Spring Boot 프로젝트 생성**

  * [start.spring.io](https://start.spring.io)에서 아래 의존성을 추가하여 프로젝트를 생성합니다.
      * `Spring Web`: REST API 서버 구축
      * `Spring Data JPA`: 데이터베이스 연동 및 관리
      * `Lombok`: 코드 작성을 편리하게 해주는 라이브러리
      * 사용할 데이터베이스 드라이버 (예: `MySQL Driver`, `PostgreSQL Driver`, `H2 Database` 등)

**2. 데이터베이스 테이블 및 JPA 엔티티 설계**

  * **`Student` (학생 정보)**: 학생의 기본 정보를 저장합니다.
  * **`FaceDescriptor` (얼굴 특징 데이터)**: 학생의 얼굴에서 추출한 고유한 특징 벡터(128차원의 숫자 배열)를 저장합니다. 한 학생이 여러 개의 얼굴 데이터를 등록할 수 있도록 1:N 관계로 설계하는 것이 정확도에 유리합니다.
  * **`Attendance` (출석 기록)**: 출석 성공 시 기록을 저장합니다.

**SQL 테이블 구조 예시 (MySQL 기준)**

```sql
-- 학생 테이블
CREATE TABLE student (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 얼굴 특징 데이터 테이블
CREATE TABLE face_descriptor (
    descriptor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    descriptor TEXT NOT NULL, -- 128차원 배열을 JSON 형태의 문자열로 저장
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);

-- 출석 기록 테이블
CREATE TABLE attendance (
    attendance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    check_in_time TIMESTAMP NOT NULL,
    -- course_id BIGINT, -- 필요시 강의 정보 추가
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);
```

**JPA 엔티티 클래스 작성**

```java
// Student.java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String studentNumber; // 학번
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaceDescriptor> faceDescriptors = new ArrayList<>();
    // 생성자, 연관관계 편의 메소드 등...
}

// FaceDescriptor.java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FaceDescriptor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long descriptorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Lob // TEXT 타입 매핑
    private String descriptor; // Float32Array를 JSON 문자열로 저장
    // 생성자, 연관관계 편의 메소드 등...
}

// Attendance.java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDateTime checkInTime;
    // 생성자 등...
}
```

-----

#### **Phase 2: Spring Boot 백엔드 API 개발**

프론트엔드와 통신할 REST API 엔드포인트를 개발합니다.

**1. Repository 인터페이스 생성**

```java
public interface StudentRepository extends JpaRepository<Student, Long> {}
public interface FaceDescriptorRepository extends JpaRepository<FaceDescriptor, Long> {}
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {}
```

**2. DTO (Data Transfer Object) 클래스 작성**

  * 프론트엔드와 데이터를 주고받기 위한 DTO를 만듭니다.

<!-- end list -->

```java
// 얼굴 등록 요청 DTO
public record FaceRegistrationRequest(String studentNumber, List<Float> descriptor) {}

// 출석 체크 요청 DTO
public record AttendanceCheckRequest(String studentNumber) {}

// 얼굴 인식에 필요한 모든 학생 데이터 응답 DTO
public record StudentDescriptorResponse(String studentNumber, String name, List<String> descriptors) {}
```

**3. Service 및 Controller 클래스 개발**

  * **얼굴 등록 API**: 학생이 자신의 얼굴 특징 데이터를 등록합니다.
  * **전체 얼굴 데이터 조회 API**: 프론트엔드에서 얼굴을 비교하기 위해 DB에 저장된 모든 학생의 얼굴 데이터를 가져옵니다.
  * **출석 처리 API**: 얼굴 인식이 성공했을 때 출석 기록을 저장합니다.

<!-- end list -->

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 1. 얼굴 등록 API
    @PostMapping("/students/register-face")
    public ResponseEntity<Void> registerFace(@RequestBody FaceRegistrationRequest request) {
        attendanceService.registerFace(request);
        return ResponseEntity.ok().build();
    }

    // 2. 전체 얼굴 데이터 조회 API
    @GetMapping("/students/descriptors")
    public ResponseEntity<List<StudentDescriptorResponse>> getAllStudentDescriptors() {
        return ResponseEntity.ok(attendanceService.getAllStudentDescriptors());
    }

    // 3. 출석 처리 API
    @PostMapping("/attendance/check")
    public ResponseEntity<String> checkAttendance(@RequestBody AttendanceCheckRequest request) {
        String studentName = attendanceService.checkAttendance(request);
        return ResponseEntity.ok(studentName + "님, 출석 처리되었습니다.");
    }
}

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    // ... Repository 주입

    // 얼굴 등록 로직
    public void registerFace(FaceRegistrationRequest request) {
        Student student = studentRepository.findByStudentNumber(request.studentNumber())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));
        
        // Float 리스트를 JSON 문자열로 변환하여 저장
        String descriptorJson = convertListToJsonString(request.descriptor());
        FaceDescriptor faceDescriptor = new FaceDescriptor(student, descriptorJson);
        faceDescriptorRepository.save(faceDescriptor);
    }

    // 모든 학생의 얼굴 데이터 조회 로직 (이름, 학번, descriptor 리스트)
    @Transactional(readOnly = true)
    public List<StudentDescriptorResponse> getAllStudentDescriptors() {
        return studentRepository.findAll().stream()
                .map(student -> new StudentDescriptorResponse(
                        student.getStudentNumber(),
                        student.getName(),
                        student.getFaceDescriptors().stream()
                               .map(FaceDescriptor::getDescriptor)
                               .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    // 출석 처리 로직
    public String checkAttendance(AttendanceCheckRequest request) {
        Student student = studentRepository.findByStudentNumber(request.studentNumber())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학생입니다."));

        // 오늘 이미 출석했는지 확인하는 로직 (선택)
        // ...

        Attendance attendance = new Attendance(student, LocalDateTime.now());
        attendanceRepository.save(attendance);
        return student.getName();
    }

    // ... 기타 헬퍼 메소드 (e.g., convertListToJsonString)
}

```

-----

#### **Phase 3: 프론트엔드 개발 (Pure JavaScript & face-api.js)**

이제 실제로 웹캠을 사용해 얼굴을 인식하는 프론트엔드 코드를 작성합니다.

**1. `face-api.js` 라이브러리 및 모델 파일 준비**

  * HTML 파일에 `face-api.js` 스크립트를 추가합니다. CDN을 사용하는 것이 가장 간편합니다.
  * 얼굴 인식을 위한 사전 훈련된 모델 파일(`.weights`)이 필요합니다. 이 파일들을 프로젝트의 정적 리소스 폴더(e.g., `src/main/resources/static/models`)에 다운로드하여 위치시킵니다.

**HTML 파일 (`attendance.html`)**

```html
<!DOCTYPE html>
<html>
<head>
    <title>얼굴 인식 출석 시스템</title>
    <script src="https://cdn.jsdelivr.net/npm/face-api.js@0.22.2/dist/face-api.min.js"></script>
    <style>
        /* 비디오와 캔버스 스타일링 */
        video, canvas { position: absolute; }
    </style>
</head>
<body>
    <h1>얼굴을 카메라 중앙에 맞춰주세요.</h1>
    <video id="video" width="720" height="560" autoplay muted></video>
    <div id="message"></div>
    <script src="/js/attendance.js"></script>
</body>
</html>
```

**2. JavaScript 파일 (`attendance.js`)**

**A. 초기 설정 및 모델 로딩**

```javascript
const video = document.getElementById('video');
const messageDiv = document.getElementById('message');

// face-api.js 모델 로딩
Promise.all([
    faceapi.nets.tinyFaceDetector.loadFromUri('/models'),
    faceapi.nets.faceLandmark68Net.loadFromUri('/models'),
    faceapi.nets.faceRecognitionNet.loadFromUri('/models'),
    faceapi.nets.ssdMobilenetv1.loadFromUri('/models') // 얼굴 등록 시 더 정확한 모델 사용 가능
]).then(startVideo);

async function startVideo() {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: {} });
        video.srcObject = stream;
    } catch (err) {
        console.error(err);
    }
}
```

**B. 얼굴 등록 로직 (별도 페이지 또는 기능으로 구현)**

```javascript
// 예시: 특정 버튼을 눌렀을 때 얼굴 등록
async function registerFace() {
    const studentNumber = prompt("학번을 입력하세요:");
    if (!studentNumber) return;

    messageDiv.innerText = '얼굴을 정면으로 보여주세요. 3초 후 촬영합니다.';
    await new Promise(resolve => setTimeout(resolve, 3000));

    const detections = await faceapi.detectSingleFace(video, new faceapi.TinyFaceDetectorOptions())
        .withFaceLandmarks().withFaceDescriptor();

    if (detections) {
        // Float32Array를 일반 배열로 변환
        const descriptorArray = Array.from(detections.descriptor);

        // 백엔드 API로 전송
        await fetch('/api/students/register-face', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ studentNumber: studentNumber, descriptor: descriptorArray })
        });
        messageDiv.innerText = '얼굴 등록이 완료되었습니다.';
    } else {
        messageDiv.innerText = '얼굴을 인식하지 못했습니다. 다시 시도해주세요.';
    }
}
```

**C. 얼굴 인식 및 출석 처리 로직 (핵심)**

```javascript
video.addEventListener('play', async () => {
    // 1. DB에 저장된 모든 학생의 얼굴 데이터를 가져옴
    const labeledFaceDescriptors = await loadLabeledImages();
    if (labeledFaceDescriptors.length === 0) {
        console.log("등록된 얼굴 데이터가 없습니다.");
        return;
    }
    
    // 2. 얼굴 매칭 객체 생성
    const faceMatcher = new faceapi.FaceMatcher(labeledFaceDescriptors, 0.45); // 0.45: 임계값 (조정 필요)

    const canvas = faceapi.createCanvasFromMedia(video);
    document.body.append(canvas);
    const displaySize = { width: video.width, height: video.height };
    faceapi.matchDimensions(canvas, displaySize);

    let isProcessing = false;

    // 3. 1초마다 웹캠 영상에서 얼굴을 감지하고 매칭
    setInterval(async () => {
        if (isProcessing) return;
        isProcessing = true;

        const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions())
            .withFaceLandmarks().withFaceDescriptors();
        
        const resizedDetections = faceapi.resizeResults(detections, displaySize);
        canvas.getContext('2d').clearRect(0, 0, canvas.width, canvas.height);
        
        // 감지된 얼굴과 DB 데이터를 비교
        const results = resizedDetections.map(d => faceMatcher.findBestMatch(d.descriptor));
        
        results.forEach((result, i) => {
            const box = resizedDetections[i].detection.box;
            const drawBox = new faceapi.draw.DrawBox(box, { label: result.toString() });
            drawBox.draw(canvas);

            // 'unknown'이 아니고, 아직 처리되지 않은 학생이라면 출석 처리
            if (result.label !== 'unknown' && !isStudentCheckedIn(result.label)) {
                checkIn(result.label);
            }
        });
        isProcessing = false;
    }, 1000);
});

// 백엔드에서 모든 학생의 얼굴 데이터를 가져오는 함수
async function loadLabeledImages() {
    const response = await fetch('/api/students/descriptors');
    const students = await response.json();
    
    return Promise.all(
        students.map(async (student) => {
            const descriptors = [];
            for (const descriptorStr of student.descriptors) {
                // JSON 문자열을 Float32Array로 변환
                const descriptor = new Float32Array(JSON.parse(descriptorStr));
                descriptors.push(descriptor);
            }
            return new faceapi.LabeledFaceDescriptors(student.studentNumber, descriptors); // 라벨을 학번으로 설정
        })
    );
}

// 출석 처리 API를 호출하는 함수
const checkedInStudents = new Set(); // 중복 출석 방지용
async function checkIn(studentNumber) {
    checkedInStudents.add(studentNumber); // 처리 목록에 추가
    messageDiv.innerText = `${studentNumber}님, 출석 처리 중...`;

    try {
        const response = await fetch('/api/attendance/check', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ studentNumber: studentNumber })
        });
        const resultMessage = await response.text();
        messageDiv.innerText = resultMessage;
    } catch (error) {
        console.error("출석 처리 실패:", error);
        messageDiv.innerText = `${studentNumber}님, 출석 처리에 실패했습니다.`;
        checkedInStudents.delete(studentNumber); // 실패 시 목록에서 제거
    }
}

function isStudentCheckedIn(studentNumber) {
    return checkedInStudents.has(studentNumber);
}
```

-----

### **Phase 4: 중요 고려사항 및 개선 방향**

1.  **정확도 및 성능**:

      * `TinyFaceDetector`는 빠르지만 정확도가 낮습니다. 더 정확한 인식이 필요하다면 `SsdMobilenetv1` 모델을 사용하세요. (단, 클라이언트 PC 성능에 영향을 줍니다.)
      * 인식률을 높이려면 학생마다 **여러 각도와 표정의 얼굴을 3\~5장 정도 등록**하는 것이 매우 중요합니다.
      * `FaceMatcher`의 임계값(Threshold)을 조절하여 오인식률을 조정해야 합니다. (기본값 0.6, 낮출수록 엄격해짐)

2.  **보안**:

      * 현재 구조는 학번만 알면 누구나 출석 요청을 보낼 수 있어 매우 취약합니다.
      * **개선 방안**: 로그인 기능(Spring Security, JWT 등)을 도입하여, 출석 요청 시 인증된 사용자의 토큰을 함께 보내 서버에서 해당 학생이 맞는지 검증하는 과정이 **반드시** 필요합니다.

3.  **UI/UX**:

      * 인식 중일 때, 성공했을 때, 실패했을 때 명확한 시각적/청각적 피드백을 제공해야 합니다. (예: "인식 성공\!" 메시지와 함께 박스 색상 변경)
      * 중복 출석을 방지하는 로직을 백엔드와 프론트엔드 양쪽에서 모두 처리하는 것이 안전합니다.

4.  **데이터 관리**:

      * 얼굴 특징 데이터는 민감한 생체 정보입니다. 데이터베이스에 저장 및 전송 시 보안 규정을 준수하고, 암호화 등의 조치를 고려해야 합니다.

위 단계를 따라가시면 Spring Boot와 JavaScript 기반의 얼굴 인식 출석 시스템의 핵심 기능을 구현하실 수 있습니다. 여기서부터 보안, UI/UX, 예외 처리 등을 추가하며 프로젝트의 완성도를 높여나가시면 됩니다.
