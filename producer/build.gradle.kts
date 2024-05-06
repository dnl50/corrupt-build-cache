plugins {
    java
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
}

dependencies {
    compileOnly(project(":processor"))
    annotationProcessor(project(":processor"))
}

