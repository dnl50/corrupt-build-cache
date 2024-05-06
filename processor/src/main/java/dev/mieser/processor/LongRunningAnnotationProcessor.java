package dev.mieser.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.math.BigInteger;
import java.util.Random;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("dev.mieser.processor.SlowDownCompilation")
public class LongRunningAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).stream()
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .forEach(element -> {
                    try {
                        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile( "dev.mieser.generated." + element.getSimpleName() + "_", element);
                        try(var writer = sourceFile.openWriter()) {
                            writer.write("package dev.mieser.generated." + element.getSimpleName() + ";\n\n");
                            writer.write("public class " + element.getSimpleName() + "_ { }");
                        }

                        new BigInteger(9998, new Random()).nextProbablePrime();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
        }

        return false;
    }

}
