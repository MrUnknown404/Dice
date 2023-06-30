package mrunknown404.dice.utils;

import mrunknown404.dice.Dice;
import mrunknown404.dice.entity.D6Model;
import mrunknown404.dice.entity.DiceEntityRenderer;
import mrunknown404.dice.registries.DiceRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Dice.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
		e.registerEntityRenderer(DiceRegistry.DICE_ENTITY.get(), DiceEntityRenderer::new);
	}
	
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
		e.registerLayerDefinition(D6Model.LAYER_LOCATION, D6Model::createBodyLayer);
	}
}
