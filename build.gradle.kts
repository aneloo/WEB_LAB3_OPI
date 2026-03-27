plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-pre-final"

repositories {
    mavenCentral()
}

dependencies {
    // Jakarta EE APIs (WildFly provides implementations)
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    compileOnly("jakarta.persistence:jakarta.persistence-api:3.1.0")
    compileOnly("jakarta.faces:jakarta.faces-api:4.0.1")

    // PrimeFaces Jakarta
    implementation("org.primefaces:primefaces:13.0.10:jakarta")

    // PostgreSQL driver
    implementation("org.postgresql:postgresql:42.6.0")

    // EclipseLink JPA
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:4.0.2")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")



    implementation ("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")


}

tasks.war {
    archiveFileName.set("WEB_LAB3.war")
}
