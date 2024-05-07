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
val generateSourceFiles by tasks.registering(SourceFileGenerator::class) {
    outputDir.set(layout.buildDirectory.dir("generated-source-files"))
    classCount.set(15_000)
}

@CacheableTask
abstract class SourceFileGenerator : DefaultTask() {

    @get:Input
    abstract val classCount: Property<Int>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val sourcesDir = outputDir.get().dir("dev/mieser/producer/generated").asFile
        sourcesDir.mkdirs()

        (1..classCount.get()).forEach { index ->
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

