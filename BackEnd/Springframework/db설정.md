# ========================= application.properties =========================
# 파일위치: src/main/resources/application.properties

# Server Configuration
server.port=8080
server.servlet.context-path=/bookstore

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Database Initialization
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
spring.sql.init.continue-on-error=false

# Logging Configuration
logging.level.com.bookstore.management=DEBUG
logging.level.org.springframework.jdbc=DEBUG
logging.level.org.springframework.web=INFO

# DevTools Configuration
spring.devtools.restart.enabled=true
