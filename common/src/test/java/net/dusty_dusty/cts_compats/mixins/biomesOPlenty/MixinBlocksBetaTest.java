package net.dusty_dusty.cts_compats.mixins.biomesOPlenty;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Pseudo;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MixinBlocksBetaTest {
    @Test
    void betaLayoutTargetsRemainOptionalForOlderBopVersions() throws Exception {
        try (InputStream classBytes = getClass().getResourceAsStream("MixinBlocksBeta.class")) {
            assertNotNull(classBytes);
            ClassNode mixinClass = new ClassNode();
            new ClassReader(classBytes).accept(mixinClass, ClassReader.SKIP_CODE);
            List<AnnotationNode> annotations = mixinClass.invisibleAnnotations == null
                    ? List.of()
                    : mixinClass.invisibleAnnotations;

            assertTrue(annotations.stream().anyMatch(annotation ->
                    Type.getDescriptor(Pseudo.class).equals(annotation.desc)
            ));
        }
    }
}
