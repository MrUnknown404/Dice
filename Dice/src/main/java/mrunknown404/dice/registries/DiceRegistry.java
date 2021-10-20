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
	
	public static final RegistryObject<Item> WHITE_DICE = ITEMS.register("white_dice", () -> new DiceItem(Color.WHITE));
	public static final RegistryObject<Item> ORANGE_DICE = ITEMS.register("orange_dice", () -> new DiceItem(new Color(255, 100, 0)));
	public static final RegistryObject<Item> MAGENTA_DICE = ITEMS.register("magenta_dice", () -> new DiceItem(new Color(255, 0, 255)));
	public static final RegistryObject<Item> LIGHT_BLUE_DICE = ITEMS.register("light_blue_dice", () -> new DiceItem(new Color(100, 250, 255)));
	public static final RegistryObject<Item> YELLOW_DICE = ITEMS.register("yellow_dice", () -> new DiceItem(new Color(255, 255, 0)));
	public static final RegistryObject<Item> LIME_DICE = ITEMS.register("lime_dice", () -> new DiceItem(new Color(100, 255, 0)));
	public static final RegistryObject<Item> PINK_DICE = ITEMS.register("pink_dice", () -> new DiceItem(new Color(255, 110, 190)));
	public static final RegistryObject<Item> GRAY_DICE = ITEMS.register("gray_dice", () -> new DiceItem(new Color(100, 100, 100)));
	public static final RegistryObject<Item> LIGHT_GRAY_DICE = ITEMS.register("light_gray_dice", () -> new DiceItem(new Color(150, 150, 150)));
	public static final RegistryObject<Item> CYAN_DICE = ITEMS.register("cyan_dice", () -> new DiceItem(new Color(0, 255, 255)));
	public static final RegistryObject<Item> PURPLE_DICE = ITEMS.register("purple_dice", () -> new DiceItem(new Color(150, 0, 210)));
	public static final RegistryObject<Item> BLUE_DICE = ITEMS.register("blue_dice", () -> new DiceItem(new Color(50, 50, 255)));
	public static final RegistryObject<Item> BROWN_DICE = ITEMS.register("brown_dice", () -> new DiceItem(new Color(100, 70, 30)));
	public static final RegistryObject<Item> GREEN_DICE = ITEMS.register("green_dice", () -> new DiceItem(new Color(0, 255, 0)));
	public static final RegistryObject<Item> RED_DICE = ITEMS.register("red_dice", () -> new DiceItem(new Color(255, 50, 50)));
	public static final RegistryObject<Item> BLACK_DICE = ITEMS.register("black_dice", () -> new DiceItem(new Color(50, 50, 50)));
	
	public static final ModItemGroup DICE = new ModItemGroup("dice", () -> new ItemStack(WHITE_DICE.get()));
	
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
