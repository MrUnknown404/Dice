package mrunknown404.dice;

import mrunknown404.dice.entity.DiceEntityRenderer;
import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DiceConfig.COMMON_SPEC);
		
		DiceRegistry.register();
	}
	
	private void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent e) {
		RenderingRegistry.registerEntityRenderingHandler(DiceRegistry.DICE_ENTITY.get(), DiceEntityRenderer::new);
	}
	
	@SubscribeEvent
	public void onMissingMap(RegistryEvent.MissingMappings<Item> e) {
		for (Mapping<Item> map : e.getMappings(MOD_ID)) {
			String path = map.key.getPath();
			
			if (path.equals("white_dice")) { //haha ugly fix
				map.remap(DiceRegistry.WHITE_D6.get());
			} else if (path.equals("orange_dice")) {
				map.remap(DiceRegistry.ORANGE_D6.get());
			} else if (path.equals("magenta_dice")) {
				map.remap(DiceRegistry.MAGENTA_D6.get());
			} else if (path.equals("light_blue_dice")) {
				map.remap(DiceRegistry.LIGHT_BLUE_D6.get());
			} else if (path.equals("yellow_dice")) {
				map.remap(DiceRegistry.YELLOW_D6.get());
			} else if (path.equals("lime_dice")) {
				map.remap(DiceRegistry.LIME_D6.get());
			} else if (path.equals("pink_dice")) {
				map.remap(DiceRegistry.PINK_D6.get());
			} else if (path.equals("gray_dice")) {
				map.remap(DiceRegistry.GRAY_D6.get());
			} else if (path.equals("light_gray_dice")) {
				map.remap(DiceRegistry.LIGHT_GRAY_D6.get());
			} else if (path.equals("cyan_dice")) {
				map.remap(DiceRegistry.CYAN_D6.get());
			} else if (path.equals("purple_dice")) {
				map.remap(DiceRegistry.PURPLE_D6.get());
			} else if (path.equals("blue_dice")) {
				map.remap(DiceRegistry.BLUE_D6.get());
			} else if (path.equals("brown_dice")) {
				map.remap(DiceRegistry.BROWN_D6.get());
			} else if (path.equals("green_dice")) {
				map.remap(DiceRegistry.GREEN_D6.get());
			} else if (path.equals("red_dice")) {
				map.remap(DiceRegistry.RED_D6.get());
			} else if (path.equals("black_dice")) {
				map.remap(DiceRegistry.BLACK_D6.get());
			}
		}
	}
}
