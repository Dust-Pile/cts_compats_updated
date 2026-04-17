package net.dusty_dusty.cts_compats.registry;

import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public interface IColorRegistry {

    void onColorHandlerEventBlock( RegisterColorHandlersEvent.Block event );

    void onColorHandlerEventItem( RegisterColorHandlersEvent.Item event );

    default void onModelBake( ModelEvent.ModifyBakingResult event ) {};

    default void onRegisterAdditionalModels( ModelEvent.RegisterAdditional event ) {};

}
