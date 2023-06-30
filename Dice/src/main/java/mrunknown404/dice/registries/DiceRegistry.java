package mrunknown404.dice.registries;

import java.awt.Color;

import mrunknown404.dice.Dice;
import mrunknown404.dice.entity.DiceEntity;
import mrunknown404.dice.item.DiceItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DiceRegistry {
	public static final Color WHITE = Color.WHITE;
	public static final Color ORANGE = new Color(230, 158, 52);
	public static final Color MAGENTA = new Color(219, 122, 213);
	public static final Color LIGHT_BLUE = new Color(143, 185, 244);
	public static final Color YELLOW = new Color(255, 218, 57);
	public static final Color LIME = new Color(129, 206, 27);
	public static final Color PINK = new Color(246, 137, 172);
	public static final Color GRAY = new Color(112, 112, 112);
	public static final Color LIGHT_GRAY = new Color(162, 162, 162);
	public static final Color CYAN = new Color(81, 211, 185);
	public static final Color PURPLE = new Color(176, 100, 216);
	public static final Color BLUE = new Color(74, 124, 215);
	public static final Color BROWN = new Color(151, 103, 70);
	public static final Color GREEN = new Color(86, 150, 49);
	public static final Color RED = new Color(210, 68, 63);
	public static final Color BLACK = new Color(82, 82, 82);
	
	private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Dice.MOD_ID);
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Dice.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Dice.MOD_ID);
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		CREATIVE_TABS.register(bus);
		ENTITIES.register(bus);
		ITEMS.register(bus);
	}
	
	public static final RegistryObject<EntityType<DiceEntity>> DICE_ENTITY = ENTITIES.register("dice_entity",
			() -> EntityType.Builder.<DiceEntity>of(DiceEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f).build(Dice.MOD_ID + ":dice_entity"));
	
	public static final RegistryObject<Item> WHITE_D6 = ITEMS.register("white_d6", () -> new DiceItem(WHITE, (byte) 6));
	public static final RegistryObject<Item> ORANGE_D6 = ITEMS.register("orange_d6", () -> new DiceItem(ORANGE, (byte) 6));
	public static final RegistryObject<Item> MAGENTA_D6 = ITEMS.register("magenta_d6", () -> new DiceItem(MAGENTA, (byte) 6));
	public static final RegistryObject<Item> LIGHT_BLUE_D6 = ITEMS.register("light_blue_d6", () -> new DiceItem(LIGHT_BLUE, (byte) 6));
	public static final RegistryObject<Item> YELLOW_D6 = ITEMS.register("yellow_d6", () -> new DiceItem(YELLOW, (byte) 6));
	public static final RegistryObject<Item> LIME_D6 = ITEMS.register("lime_d6", () -> new DiceItem(LIME, (byte) 6));
	public static final RegistryObject<Item> PINK_D6 = ITEMS.register("pink_d6", () -> new DiceItem(PINK, (byte) 6));
	public static final RegistryObject<Item> GRAY_D6 = ITEMS.register("gray_d6", () -> new DiceItem(GRAY, (byte) 6));
	public static final RegistryObject<Item> LIGHT_GRAY_D6 = ITEMS.register("light_gray_d6", () -> new DiceItem(LIGHT_GRAY, (byte) 6));
	public static final RegistryObject<Item> CYAN_D6 = ITEMS.register("cyan_d6", () -> new DiceItem(CYAN, (byte) 6));
	public static final RegistryObject<Item> PURPLE_D6 = ITEMS.register("purple_d6", () -> new DiceItem(PURPLE, (byte) 6));
	public static final RegistryObject<Item> BLUE_D6 = ITEMS.register("blue_d6", () -> new DiceItem(BLUE, (byte) 6));
	public static final RegistryObject<Item> BROWN_D6 = ITEMS.register("brown_d6", () -> new DiceItem(BROWN, (byte) 6));
	public static final RegistryObject<Item> GREEN_D6 = ITEMS.register("green_d6", () -> new DiceItem(GREEN, (byte) 6));
	public static final RegistryObject<Item> RED_D6 = ITEMS.register("red_d6", () -> new DiceItem(RED, (byte) 6));
	public static final RegistryObject<Item> BLACK_D6 = ITEMS.register("black_d6", () -> new DiceItem(BLACK, (byte) 6));
	
	public static final RegistryObject<CreativeModeTab> DICE_TAB = CREATIVE_TABS.register("dice",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group." + Dice.MOD_ID + ".dice")).icon(() -> new ItemStack(WHITE_D6.get())).displayItems((p, o) -> {
				o.acceptAll(ITEMS.getEntries().stream().map(r -> new ItemStack(r.get())).toList());
			}).withSearchBar().build());
}
