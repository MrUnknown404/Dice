package mrunknown404.dice.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;

public class D6Model extends DiceModel {
	private final ModelRenderer cube;
	
	public D6Model() {
		texWidth = 32;
		texHeight = 32;
		
		cube = new ModelRenderer(this);
		cube.setPos(0, 2.5f, 0);
		cube.texOffs(0, 0).addBox(-2.5f, -2.5f, -2.5f, 5, 5, 5, 0, false);
	}
	
	@Override
	public void setupRotation(DiceEntity dice) {
		float r1 = (float) Math.PI / 2f;
		
		switch (dice.getRoll()) {
			case 1:
				setRotationAngle((float) Math.PI, 0, 0);
				break;
			case 2:
				setRotationAngle(-r1, 0, 0);
				break;
			case 3:
				setRotationAngle(0, 0, r1);
				break;
			case 4:
				setRotationAngle(0, 0, -r1);
				break;
			case 5:
				setRotationAngle(r1, 0, 0);
				break;
			case 6:
				setRotationAngle(0, 0, 0);
				break;
		}
	}
	
	@Override
	public void renderToBuffer(MatrixStack stack, IVertexBuilder buf, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		cube.render(stack, buf, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	protected void setRotationAngle(float x, float y, float z) {
		cube.xRot = x;
		cube.yRot = y;
		cube.zRot = z;
	}
}
