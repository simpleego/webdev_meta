# AWS에 스프링 프로젝트를 배포하는 방법
> 두 가지 방식은 **EC2 인스턴스에 직접 배포**하는 방법
> **Elastic Beanstalk을 사용하는 자동화된 배포** 방식
---

## 🛠️ 1. EC2 인스턴스에 직접 배포하기

### ✅ 개요
Amazon EC2는 가상 서버를 제공하는 서비스로, 직접 서버를 설정하고 애플리케이션을 배포할 수 있어요. 자유도가 높지만 설정은 수동이에요.

### 📦 준비물
- AWS 계정
- EC2 인스턴스 (Ubuntu 또는 Amazon Linux 추천)
- SSH 클라이언트 (예: 터미널, PuTTY)
- JDK 설치
- Spring Boot JAR 파일
- (선택) Nginx 또는 Apache 설정

### 🚀 배포 단계

1. **EC2 인스턴스 생성**
   - AWS 콘솔 → EC2 → 인스턴스 시작
   - OS 선택 (Ubuntu 22.04 LTS 추천)
   - 인스턴스 유형 선택 (t2.micro는 프리티어)
   - 키 페어 생성 및 다운로드 (.pem 파일)

2. **보안 그룹 설정**
   - 인바운드 규칙에 포트 22 (SSH), 80 (HTTP), 8080 (Spring Boot 기본 포트) 열기

3. **SSH 접속**
   ```bash
   ssh -i "your-key.pem" ubuntu@your-ec2-public-ip
   ```

4. **JDK 설치**
   ```bash
   sudo apt update
   sudo apt install openjdk-17-jdk
   java -version
   ```

5. **JAR 파일 업로드**
   - `scp` 명령어로 로컬에서 EC2로 전송:
     ```bash
     scp -i "your-key.pem" your-app.jar ubuntu@your-ec2-public-ip:~
     ```

6. **애플리케이션 실행**
   ```bash
   java -jar your-app.jar
   ```

7. **(선택) Nginx Reverse Proxy 설정**
   - 80 포트로 접속 시 8080으로 포워딩
   - SSL 인증서 적용 가능 (Let's Encrypt)

---

## 🌱 2. Elastic Beanstalk으로 배포하기

### ✅ 개요
Elastic Beanstalk은 AWS에서 제공하는 PaaS로, 인프라 설정 없이 애플리케이션을 자동으로 배포하고 관리해줘요.

### 📦 준비물
- AWS 계정
- AWS CLI 설치
- Spring Boot JAR 파일
- `Procfile` (애플리케이션 실행 명령어 포함)

### 🚀 배포 단계

1. **AWS CLI 설치 및 설정**
   ```bash
   aws configure
   ```
   - Access Key, Secret Key, Region 입력

2. **프로젝트 디렉토리 구성**
   ```
   your-app/
   ├── your-app.jar
   └── Procfile
   ```

   **Procfile 내용 예시**:
   ```
   web: java -jar your-app.jar
   ```

3. **애플리케이션 생성 및 배포**
   ```bash
   eb init -p java your-app
   eb create your-env-name
   eb deploy
   ```

4. **배포 완료 후 확인**
   - Elastic Beanstalk 콘솔에서 URL 확인
   - 로그 및 상태 모니터링 가능

---

## 📊 비교 요약

| 항목 | EC2 직접 배포 | Elastic Beanstalk |
|------|----------------|--------------------|
| 자유도 | 높음 | 중간 |
| 설정 난이도 | 높음 | 낮음 |
| 자동화 | 없음 | 있음 (자동 스케일링, 로드 밸런싱) |
| 유지보수 | 직접 관리 | AWS가 일부 관리 |
| 추천 대상 | DevOps 경험자 | 빠른 배포 원하는 개발자 |

---

## 🎯 다음 단계 제안
- 처음이라면 **Elastic Beanstalk**이 훨씬 간편하고 빠르게 배포
- EC2는 더 많은 제어권이 필요할 때 적합해요 (예: 커스텀 서버 설정, Docker 등).
