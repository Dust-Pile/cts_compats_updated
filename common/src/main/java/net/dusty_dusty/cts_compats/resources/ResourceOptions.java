package net.dusty_dusty.cts_compats.resources;

import java.util.Set;

public final class ResourceOptions {
    public static Set<Class<? extends IResourceOption>> availableTypes() {
        return Set.of(BlockModelOption.class);
    }

    public interface IResourceOption {}

    public enum BlockModelOption implements IResourceOption {
        UV_TOP_EDGE, // Use texture with top uv
        UV_OFF_BY_ONE, // Offset uv by 1 pixel relative to edge
        UV_TOP_EDGE_OVERLAY,
        UV_OFF_BY_ONE_OVERLAY,
        STATE_ALL_ROTATIONS
    }
}
