# 성적처리 클래스 연습
---

### 📚 클래스 구성

#### 1. `Student` 클래스
```java
public class Student {
    private String name;
    private String studentId;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    public void enroll(Course course) {
        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    public void printTranscript() {
        System.out.println("Transcript for " + name);
        for (Enrollment e : enrollments) {
            System.out.println(e.getCourse().getCourseName() + " - " + e.getGrade());
        }
    }
}
```

#### 2. `Professor` 클래스
```java
public class Professor {
    private String name;
    private String professorId;

    public Professor(String name, String professorId) {
        this.name = name;
        this.professorId = professorId;
    }
}
```

#### 3. `Course` 클래스
```java
public class Course {
    private String courseName;
    private String courseCode;
    private Professor professor;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course(String courseName, String courseCode, Professor professor) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.professor = professor;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public String getCourseName() {
        return courseName;
    }
}
```

#### 4. `Enrollment` 클래스 (수강 정보 및 학점 포함)
```java
public class Enrollment {
    private Student student;
    private Course course;
    private String grade; // A, B, C...

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public void assignGrade(String grade) {
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }
}
```

---

### 📝 사용 예시

```java
public class Main {
    public static void main(String[] args) {
        Professor profKim = new Professor("김교수", "P001");
        Course javaCourse = new Course("자바프로그래밍", "C101", profKim);

        Student studentLee = new Student("이학생", "S001");
        studentLee.enroll(javaCourse);

        // 수강한 과목에 학점 부여
        studentLee.printTranscript(); // 아직 학점 없음
        studentLee.enroll(javaCourse); // 수강 정보 가져오기 (간단화된 구조)

        // 학점 부여 예시
        for (Enrollment e : javaCourse.enrollments) {
            if (e.getGrade() == null) {
                e.assignGrade("A");
            }
        }

        studentLee.printTranscript(); // 이제 학점 표시됨
    }
}
```

---

이 설계를 기반으로 기능을 더 확장하고 싶다면:
- 여러 강좌 등록 기능
- GPA 계산 기능
- 교수에 의해 학점이 결정되도록 변경 등

원하시겠어요? 😄 어떤 기능이 더 있었으면 좋겠는지도 알려줘요!


좋아요! 그 기능들을 추가해보면 성적처리 시스템이 훨씬 현실적인 구조가 될 거예요. 아래는 각 기능을 반영한 자바 프로그램 확장 예시입니다.

---

### ✏️ 기능 확장 포인트

#### ✅ 여러 강좌 등록 기능  
`Student` 클래스는 이미 여러 개의 수강 정보를 가질 수 있도록 리스트를 사용하고 있어요. 사용자가 여러 강좌에 등록할 수 있도록 메서드를 활용하면 됩니다.

#### 🧮 GPA 계산 기능 추가  
평균 평점을 계산하도록 `Student` 클래스에 GPA 계산 메서드를 추가합니다.

#### 🧑‍🏫 교수에 의해 학점이 결정되도록 변경  
`Professor` 클래스에 `assignGrade()` 메서드를 추가하여, 교수만이 학점을 부여할 수 있도록 구조화합니다.

---

### 💻 예시 코드 업데이트

#### `Student` 클래스 - GPA 계산 포함
```java
public double calculateGPA() {
    int totalPoints = 0;
    int count = 0;
    for (Enrollment e : enrollments) {
        if (e.getGrade() != null) {
            totalPoints += gradeToPoint(e.getGrade());
            count++;
        }
    }
    return count == 0 ? 0.0 : (double) totalPoints / count;
}

private int gradeToPoint(String grade) {
    switch (grade) {
        case "A": return 4;
        case "B": return 3;
        case "C": return 2;
        case "D": return 1;
        case "F": return 0;
        default: return 0;
    }
}
```

#### `Professor` 클래스 - 학점 부여 기능 포함
```java
public void assignGrade(Student student, Course course, String grade) {
    for (Enrollment e : course.getEnrollments()) {
        if (e.getStudent().equals(student)) {
            e.assignGrade(grade);
        }
    }
}
```

#### `Course` 클래스 - 수강 목록 접근자 추가
```java
public List<Enrollment> getEnrollments() {
    return enrollments;
}
```

#### `Enrollment` 클래스 - 학생 정보 접근자 추가
```java
public Student getStudent() {
    return student;
}
```

---

### 🧪 작동 예시

```java
Professor profKim = new Professor("김교수", "P001");
Course javaCourse = new Course("자바프로그래밍", "C101", profKim);
Course dataCourse = new Course("데이터구조", "C102", profKim);

Student studentLee = new Student("이학생", "S001");
studentLee.enroll(javaCourse);
studentLee.enroll(dataCourse);

// 교수에 의한 성적 부여
profKim.assignGrade(studentLee, javaCourse, "A");
profKim.assignGrade(studentLee, dataCourse, "B");

studentLee.printTranscript();
System.out.println("GPA: " + studentLee.calculateGPA());
```

---

이제 학생은 여러 강좌를 듣고 교수에게 직접 학점을 받을 수 있으며, GPA도 계산할 수 있어요! 🎓  
다음으로 확장하고 싶은 기능이 있다면 말씀해 주세요 — 예를 들어 학기별 성적, 패스/페일 과목, 출결 시스템 같은 것도 추가할 수 있죠! 😄
