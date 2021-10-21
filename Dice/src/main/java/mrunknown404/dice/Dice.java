package mrunknown404.dice;

import mrunknown404.dice.entity.DiceEntityRenderer;
import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Dice.MOD_ID)
public class Dice {
	public static final String MOD_ID = "dice";
	
	public Dice() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DiceConfig.COMMON_SPEC);
		
		DiceRegistry.register();
	}
	
	private void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(DiceRegistry.DICE_ENTITY.get(), DiceEntityRenderer::new);
	}
}
