package net.dusty_dusty.cts_compats.fabric;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CTSCompatsFabricEntrypointTest {
    @Test
    void waitsForEveryLoadedOptionalInitializerInAnyOrder() {
        List.of(
                List.of("biomesoplenty", "meadow", "vanillabackport"),
                List.of("biomesoplenty", "vanillabackport", "meadow"),
                List.of("meadow", "biomesoplenty", "vanillabackport"),
                List.of("meadow", "vanillabackport", "biomesoplenty"),
                List.of("vanillabackport", "biomesoplenty", "meadow"),
                List.of("vanillabackport", "meadow", "biomesoplenty")
        ).forEach(CTSCompatsFabricEntrypointTest::assertWaitsForOrder);
    }

    @Test
    void supportsLoadedOptionalSubsets() {
        assertTrue(CTSCompatsFabric.allLoadedOptionalModsInitialized(
                Set.of("biomesoplenty", "meadow"),
                Set.of("biomesoplenty", "meadow")::contains
        ));
        assertTrue(CTSCompatsFabric.allLoadedOptionalModsInitialized(
                Set.of("biomesoplenty"),
                Set.of("biomesoplenty")::contains
        ));
        assertTrue(CTSCompatsFabric.allLoadedOptionalModsInitialized(Set.of(), ignored -> false));
    }

    private static void assertWaitsForOrder(List<String> order) {
        Set<String> allOptionalMods = Set.of("biomesoplenty", "meadow", "vanillabackport");
        Set<String> initialized = new HashSet<>();

        initialized.add(order.get(0));
        assertFalse(CTSCompatsFabric.allLoadedOptionalModsInitialized(initialized, allOptionalMods::contains));
        initialized.add(order.get(1));
        assertFalse(CTSCompatsFabric.allLoadedOptionalModsInitialized(initialized, allOptionalMods::contains));
        initialized.add(order.get(2));
        assertTrue(CTSCompatsFabric.allLoadedOptionalModsInitialized(initialized, allOptionalMods::contains));
    }

    @Test
    void mainEntrypointStartsSharedLifecycle() throws Exception {
        assertCallsStaticMethod(
                CTSCompatsFabric.class,
                "onInitialize",
                "net/dusty_dusty/cts_compats/CTSCompats",
                "init"
        );
    }

    @Test
    void clientEntrypointStartsSharedClientLifecycle() throws Exception {
        assertCallsStaticMethod(
                CTSCompatsFabricClient.class,
                "onInitializeClient",
                "net/dusty_dusty/cts_compats/CTSCompatsClient",
                "init"
        );
    }

    private static void assertCallsStaticMethod(
            Class<?> entrypoint,
            String entrypointMethod,
            String owner,
            String methodName
    ) throws Exception {
        try (InputStream classBytes = entrypoint.getResourceAsStream(entrypoint.getSimpleName() + ".class")) {
            assertNotNull(classBytes);
            ClassNode entrypointClass = new ClassNode();
            new ClassReader(classBytes).accept(entrypointClass, 0);

            MethodNode method = entrypointClass.methods.stream()
                    .filter(candidate -> candidate.name.equals(entrypointMethod))
                    .findFirst()
                    .orElseThrow();

            assertTrue(StreamSupport.stream(method.instructions.spliterator(), false).anyMatch(instruction ->
                    instruction instanceof MethodInsnNode call
                            && call.getOpcode() == Opcodes.INVOKESTATIC
                            && call.owner.equals(owner)
                            && call.name.equals(methodName)
                            && call.desc.equals("()V")
            ));
        }
    }
}
