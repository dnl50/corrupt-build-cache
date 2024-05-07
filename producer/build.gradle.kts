plugins {
    java
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// Generates lots of java source files so the compiler is busy compiling
val generateSourceFiles by tasks.registering {
    val outputDir = layout.buildDirectory.dir("generated-source-files")
    outputs.dir(outputDir)

    doLast {
        val sourcesDir = outputDir.get().dir("dev/mieser/producer/generated").asFile
        sourcesDir.mkdirs()

        (1..15_000).forEach { index ->
            sourcesDir.resolve("Generated$index.java").writeText("""
                package dev.mieser.producer.generated;

                public class Generated$index {

                }
            """.trimIndent())
        }
    }
}

sourceSets {
    main {
        java {
            srcDir(generateSourceFiles)
        }
    }
}

