package net.dusty_dusty.cts_compats.fabric;

import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPBaseRegistry;
import net.dusty_dusty.cts_compats.mods.biomesOPlenty.registry.BOPReference;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BOPReferenceTest {
    @Test
    void resolvesBOPBlocksWhenRegistrySuppliersRun() throws Exception {
        ClassNode references = readClass(BOPReference.class);
        Set<String> fields = Set.of(
                "WHITE_SAND", "ORANGE_SAND", "MOSSY_BLACK_SAND", "BLACK_SAND",
                "DRIED_SALT", "FLESH", "POROUS_FLESH", "BRIMSTONE"
        );

        assertEquals(8, references.fields.stream()
                .filter(field -> fields.contains(field.name))
                .filter(field -> field.desc.equals("Ljava/util/function/Supplier;"))
                .count());

        ClassNode registry = readClass(BOPBaseRegistry.class);
        long deferredLookups = registry.methods.stream()
                .flatMap(method -> StreamSupport.stream(method.instructions.spliterator(), false))
                .filter(instruction -> instruction instanceof MethodInsnNode call
                        && call.getOpcode() == Opcodes.INVOKEINTERFACE
                        && call.owner.equals("java/util/function/Supplier")
                        && call.name.equals("get"))
                .count();
        assertTrue(deferredLookups >= 8);
    }

    private static ClassNode readClass(Class<?> type) throws Exception {
        try (InputStream classBytes = type.getResourceAsStream(type.getSimpleName() + ".class")) {
            assertNotNull(classBytes);
            ClassNode classNode = new ClassNode();
            new ClassReader(classBytes).accept(classNode, 0);
            return classNode;
        }
    }
}
