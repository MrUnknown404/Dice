package mrunknown404.dice;

import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Dice.MOD_ID)
public class Dice {
	public static final String MOD_ID = "dice";
	
	public Dice() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DiceConfig.COMMON_SPEC);
		DiceRegistry.register();
	}
}
