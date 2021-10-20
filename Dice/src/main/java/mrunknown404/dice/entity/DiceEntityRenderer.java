package mrunknown404.dice.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mrunknown404.dice.Dice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class DiceEntityRenderer extends EntityRenderer<DiceEntity> implements IRenderFactory<DiceEntity> {
	private static final ResourceLocation TEX = new ResourceLocation(Dice.MOD_ID, "textures/entity/dice.png");
	private final DiceEntityModel model = new DiceEntityModel();
	private final Minecraft minecraft;
	
	public DiceEntityRenderer(EntityRendererManager erm) {
		super(erm);
		this.minecraft = Minecraft.getInstance();
	}
	
	@Override
	public void render(DiceEntity dice, float var1, float var2, MatrixStack stack, IRenderTypeBuffer buf, int p_225623_6_) {
		float r1 = (float) Math.PI / 2f;
		
		switch (dice.getRoll()) {
			case 1:
				model.setRotationAngle((float) Math.PI, 0, 0);
				break;
			case 2:
				model.setRotationAngle(-r1, 0, 0);
				break;
			case 3:
				model.setRotationAngle(0, 0, r1);
				break;
			case 4:
				model.setRotationAngle(0, 0, -r1);
				break;
			case 5:
				model.setRotationAngle(r1, 0, 0);
				break;
			case 6:
				model.setRotationAngle(0, 0, 0);
				break;
		}
		
		boolean flag = !dice.isInvisible();
		RenderType rendertype = getRenderType(dice, flag, !flag && !dice.isInvisibleTo(minecraft.player), minecraft.shouldEntityAppearGlowing(dice));
		
		if (rendertype != null) {
			IVertexBuilder ivertexbuilder = buf.getBuffer(rendertype);
			model.renderToBuffer(stack, ivertexbuilder, p_225623_6_, getOverlayCoords(0), dice.getRed() / 255f, dice.getGreen() / 255f, dice.getBlue() / 255f, 1);
		}
	}
	
	// Unsure what this does tbh
	private static int getOverlayCoords(float f) {
		return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(false));
	}
	
	private RenderType getRenderType(DiceEntity dice, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		ResourceLocation resourcelocation = getTextureLocation(dice);
		if (p_230496_3_) {
			return RenderType.itemEntityTranslucentCull(resourcelocation);
		} else if (p_230496_2_) {
			return this.model.renderType(resourcelocation);
		} else {
			return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(DiceEntity dice) {
		return TEX;
	}
	
	@Override
	public EntityRenderer<? super DiceEntity> createRenderFor(EntityRendererManager erm) {
		return new DiceEntityRenderer(erm);
	}
}
