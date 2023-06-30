package mrunknown404.dice.utils;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class DiceConfig {
	public static class Common {
		private static final int DEFAULT_DICE_EXPIRE_TIME = 10;
		
		public final ConfigValue<Integer> diceExpireTime;
		
		public Common(ForgeConfigSpec.Builder builder) {
			builder.push("dice");
			this.diceExpireTime = builder.comment("How many seconds should the dice last before exploding?").worldRestart().defineInRange("dice expire time",
					DEFAULT_DICE_EXPIRE_TIME, 1, 60);
			builder.pop();
		}
	}
	
	public static final Common COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	static {
		Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON = commonSpecPair.getLeft();
		COMMON_SPEC = commonSpecPair.getRight();
	}
}
