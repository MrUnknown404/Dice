package mrunknown404.dice.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mrunknown404.dice.Dice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DiceEntityRenderer extends EntityRenderer<DiceEntity> implements EntityRendererProvider<DiceEntity> {
	private static final ResourceLocation D6_TEX = new ResourceLocation(Dice.MOD_ID, "textures/entity/d6.png");
	private static final D6Model D6_MODEL = new D6Model();
	
	private ResourceLocation tex;
	private final Minecraft minecraft;
	
	public DiceEntityRenderer(Context ctx) {
		super(ctx);
		this.minecraft = Minecraft.getInstance();
	}
	
	@Override
	public void render(DiceEntity dice, float var1, float var2, PoseStack stack, MultiBufferSource buf, int var3) {
		DiceModel model = null;
		switch (dice.getDiceType()) {
			case 4:
				break;
			case 6:
				model = D6_MODEL;
				tex = D6_TEX;
				break;
			case 8:
				break;
			case 10:
				break;
			case 12:
				break;
			case 20:
				break;
		}
		
		if (model == null) {
			System.err.println("Invalid dice? " + dice.getDiceType());
			return;
		}
		
		boolean flag = !dice.isInvisible();
		RenderType rendertype = getRenderType(dice, model, flag, !flag && !dice.isInvisibleTo(minecraft.player), minecraft.shouldEntityAppearGlowing(dice));
		
		if (rendertype != null) {
			VertexConsumer ivertexbuilder = buf.getBuffer(rendertype);
			model.setupRotation(dice);
			model.renderToBuffer(stack, ivertexbuilder, var3, getOverlayCoords(0), dice.getRed() / 255f, dice.getGreen() / 255f, dice.getBlue() / 255f, 1);
		}
	}
	
	// Unsure what this does tbh
	private static int getOverlayCoords(float f) {
		return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(false));
	}
	
	private RenderType getRenderType(DiceEntity dice, DiceModel model, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation resourcelocation = getTextureLocation(dice);
		if (p_230496_3_) {
			return RenderType.itemEntityTranslucentCull(resourcelocation);
		} else if (p_230496_2_) {
			return model.renderType(resourcelocation);
		} else {
			return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(DiceEntity dice) {
		return tex;
	}
	
	@Override
	public EntityRenderer<DiceEntity> create(Context ctx) {
		return new DiceEntityRenderer(ctx);
	}
}
