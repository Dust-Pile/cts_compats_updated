package net.dusty_dusty.cts_compats.forge;

import net.dusty_dusty.cts_compats.CTSCompats;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ForgePackageIsolationTest {
    @Test
    void commonAndForgeOutputsDoNotSplitPackages() throws Exception {
        Set<String> commonPackages = packagesFor(CTSCompats.class);
        Set<String> forgePackages = packagesFor(CTSCompatsForge.class);

        forgePackages.retainAll(commonPackages);

        assertTrue(forgePackages.isEmpty(), "Split packages: " + forgePackages);
    }

    private Set<String> packagesFor(Class<?> anchor) throws Exception {
        Path output = Path.of(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (output.toString().endsWith(".jar")) {
            try (JarFile jar = new JarFile(output.toFile())) {
                return jar.stream().filter(entry -> entry.getName().endsWith(".class"))
                        .map(entry -> entry.getName().substring(0, entry.getName().lastIndexOf('/')))
                        .collect(Collectors.toSet());
            }
        }
        try (Stream<Path> paths = Files.walk(output)) {
            return paths.filter(path -> path.toString().endsWith(".class"))
                    .map(Path::getParent)
                    .map(output::relativize)
                    .map(Path::toString)
                    .map(path -> path.replace('\\', '/'))
                    .collect(Collectors.toSet());
        }
    }
}
