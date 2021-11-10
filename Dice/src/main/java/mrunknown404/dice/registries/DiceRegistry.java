package mrunknown404.dice.registries;

import java.awt.Color;
import java.util.function.Supplier;

import mrunknown404.dice.Dice;
import mrunknown404.dice.entity.DiceEntity;
import mrunknown404.dice.item.DiceItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DiceRegistry {
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Dice.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Dice.MOD_ID);
	
	public static void register() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ENTITIES.register(bus);
		ITEMS.register(bus);
	}
	
	public static final RegistryObject<EntityType<DiceEntity>> DICE_ENTITY = ENTITIES.register("dice_entity",
			() -> EntityType.Builder.<DiceEntity>of(DiceEntity::new, EntityClassification.MISC).sized(0.3125f, 0.3125f).build(Dice.MOD_ID + ":dice_entity"));
	
	public static final Color WHITE = Color.WHITE;
	public static final Color ORANGE = new Color(255, 100, 0);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color LIGHT_BLUE = new Color(100, 250, 255);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color LIME = new Color(100, 255, 0);
	public static final Color PINK = new Color(255, 110, 190);
	public static final Color GRAY = new Color(100, 100, 100);
	public static final Color LIGHT_GRAY = new Color(150, 150, 150);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color PURPLE = new Color(150, 0, 210);
	public static final Color BLUE = new Color(50, 50, 255);
	public static final Color BROWN = new Color(100, 70, 30);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color RED = new Color(255, 50, 50);
	public static final Color BLACK = new Color(50, 50, 50);
	
	/*
	public static final RegistryObject<Item> WHITE_D4 = ITEMS.register("white_d4", () -> new DiceItem(WHITE, 4));
	public static final RegistryObject<Item> ORANGE_D4 = ITEMS.register("orange_d4", () -> new DiceItem(ORANGE, 4));
	public static final RegistryObject<Item> MAGENTA_D4 = ITEMS.register("magenta_d4", () -> new DiceItem(MAGENTA, 4));
	public static final RegistryObject<Item> LIGHT_BLUE_D4 = ITEMS.register("light_blue_d4", () -> new DiceItem(LIGHT_BLUE, 4));
	public static final RegistryObject<Item> YELLOW_D4 = ITEMS.register("yellow_d4", () -> new DiceItem(YELLOW, 4));
	public static final RegistryObject<Item> LIME_D4 = ITEMS.register("lime_d4", () -> new DiceItem(LIME, 4));
	public static final RegistryObject<Item> PINK_D4 = ITEMS.register("pink_d4", () -> new DiceItem(PINK, 4));
	public static final RegistryObject<Item> GRAY_D4 = ITEMS.register("gray_d4", () -> new DiceItem(GRAY, 4));
	public static final RegistryObject<Item> LIGHT_GRAY_D4 = ITEMS.register("light_gray_d4", () -> new DiceItem(LIGHT_GRAY, 4));
	public static final RegistryObject<Item> CYAN_D4 = ITEMS.register("cyan_d4", () -> new DiceItem(CYAN, 4));
	public static final RegistryObject<Item> PURPLE_D4 = ITEMS.register("purple_d4", () -> new DiceItem(PURPLE, 4));
	public static final RegistryObject<Item> BLUE_D4 = ITEMS.register("blue_d4", () -> new DiceItem(BLUE, 4));
	public static final RegistryObject<Item> BROWN_D4 = ITEMS.register("brown_d4", () -> new DiceItem(BROWN, 4));
	public static final RegistryObject<Item> GREEN_D4 = ITEMS.register("green_d4", () -> new DiceItem(GREEN, 4));
	public static final RegistryObject<Item> RED_D4 = ITEMS.register("red_d4", () -> new DiceItem(RED, 4));
	public static final RegistryObject<Item> BLACK_D4 = ITEMS.register("black_d4", () -> new DiceItem(BLACK, 4));
	*/
	
	public static final RegistryObject<Item> WHITE_D6 = ITEMS.register("white_d6", () -> new DiceItem(WHITE, 6));
	public static final RegistryObject<Item> ORANGE_D6 = ITEMS.register("orange_d6", () -> new DiceItem(ORANGE, 6));
	public static final RegistryObject<Item> MAGENTA_D6 = ITEMS.register("magenta_d6", () -> new DiceItem(MAGENTA, 6));
	public static final RegistryObject<Item> LIGHT_BLUE_D6 = ITEMS.register("light_blue_d6", () -> new DiceItem(LIGHT_BLUE, 6));
	public static final RegistryObject<Item> YELLOW_D6 = ITEMS.register("yellow_d6", () -> new DiceItem(YELLOW, 6));
	public static final RegistryObject<Item> LIME_D6 = ITEMS.register("lime_d6", () -> new DiceItem(LIME, 6));
	public static final RegistryObject<Item> PINK_D6 = ITEMS.register("pink_d6", () -> new DiceItem(PINK, 6));
	public static final RegistryObject<Item> GRAY_D6 = ITEMS.register("gray_d6", () -> new DiceItem(GRAY, 6));
	public static final RegistryObject<Item> LIGHT_GRAY_D6 = ITEMS.register("light_gray_d6", () -> new DiceItem(LIGHT_GRAY, 6));
	public static final RegistryObject<Item> CYAN_D6 = ITEMS.register("cyan_d6", () -> new DiceItem(CYAN, 6));
	public static final RegistryObject<Item> PURPLE_D6 = ITEMS.register("purple_d6", () -> new DiceItem(PURPLE, 6));
	public static final RegistryObject<Item> BLUE_D6 = ITEMS.register("blue_d6", () -> new DiceItem(BLUE, 6));
	public static final RegistryObject<Item> BROWN_D6 = ITEMS.register("brown_d6", () -> new DiceItem(BROWN, 6));
	public static final RegistryObject<Item> GREEN_D6 = ITEMS.register("green_d6", () -> new DiceItem(GREEN, 6));
	public static final RegistryObject<Item> RED_D6 = ITEMS.register("red_d6", () -> new DiceItem(RED, 6));
	public static final RegistryObject<Item> BLACK_D6 = ITEMS.register("black_d6", () -> new DiceItem(BLACK, 6));
	
	public static final ModItemGroup DICE = new ModItemGroup("dice", () -> new ItemStack(WHITE_D6.get()));
	
	public static class ModItemGroup extends ItemGroup {
		private Supplier<ItemStack> displayStack;
		
		@SuppressWarnings("deprecation")
		public ModItemGroup(String name, Supplier<ItemStack> displayStack) {
			super(Dice.MOD_ID + "_" + name);
			this.displayStack = displayStack;
			setBackgroundSuffix("item_search.png");
		}
		
		@Override
		public ItemStack makeIcon() {
			return displayStack.get();
		}
		
		@Override
		public boolean hasSearchBar() {
			return true;
		}
	}
}
