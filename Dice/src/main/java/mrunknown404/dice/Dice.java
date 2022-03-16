package mrunknown404.dice;

import mrunknown404.dice.entity.DiceEntityRenderer;
import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Dice.MOD_ID)
public class Dice {
	public static final String MOD_ID = "dice";
	
	public Dice() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DiceConfig.COMMON_SPEC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityRenderers);
		DiceRegistry.register();
	}
	
	@SubscribeEvent
	public void entityRenderers(EntityRenderersEvent.RegisterRenderers e) {
		e.registerEntityRenderer(DiceRegistry.DICE_ENTITY.get(), DiceEntityRenderer::new);
	}
}
