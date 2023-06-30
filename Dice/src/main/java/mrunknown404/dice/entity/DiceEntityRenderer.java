package mrunknown404.dice.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import mrunknown404.dice.Dice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DiceEntityRenderer extends EntityRenderer<DiceEntity> {
	private static final ResourceLocation D6_TEX = new ResourceLocation(Dice.MOD_ID, "textures/entity/d6.png");
	private static D6Model d6Model;
	
	private final Minecraft minecraft;
	
	public DiceEntityRenderer(Context ctx) {
		super(ctx);
		this.minecraft = Minecraft.getInstance();
		d6Model = new D6Model(ctx.bakeLayer(D6Model.LAYER_LOCATION));
	}
	
	@Override
	public void render(DiceEntity dice, float noidea, float partial, PoseStack stack, MultiBufferSource buffer, int packedLight) {
		DiceModel model = switch (dice.getDiceType()) {
			case 6 -> d6Model;
			default -> throw new IllegalArgumentException("Unexpected value: " + dice.getRolled());
		};
		
		boolean flag = !dice.isInvisible();
		boolean flag1 = !flag && !dice.isInvisibleTo(minecraft.player);
		
		RenderType rendertype = getRenderType(dice, model, flag, flag1);
		if (rendertype != null) {
			model.setupRotation(dice);
			model.renderToBuffer(stack, buffer.getBuffer(rendertype), packedLight, OverlayTexture.NO_OVERLAY, dice.getRed() / 255f, dice.getGreen() / 255f, dice.getBlue() / 255f,
					flag1 ? 0.15f : 1f);
		}
		
		super.render(dice, noidea, partial, stack, buffer, packedLight);
	}
	
	@Override
	public ResourceLocation getTextureLocation(DiceEntity dice) {
		return switch (dice.getDiceType()) {
			case 6 -> D6_TEX;
			default -> throw new IllegalArgumentException("Unexpected value: " + dice.getDiceType());
		};
	}
	
	private RenderType getRenderType(DiceEntity dice, DiceModel model, boolean flag1, boolean flag2) {
		ResourceLocation resourcelocation = getTextureLocation(dice);
		if (flag2) {
			return RenderType.itemEntityTranslucentCull(resourcelocation);
		} else if (flag1) {
			return model.renderType(resourcelocation);
		}
		
		return minecraft.shouldEntityAppearGlowing(dice) ? RenderType.outline(resourcelocation) : null;
	}
}
