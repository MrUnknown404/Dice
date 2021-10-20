package mrunknown404.dice.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DiceEntityModel extends EntityModel<DiceEntity> {
	private final ModelRenderer cube;
	
	public DiceEntityModel() {
		texWidth = 32;
		texHeight = 32;
		
		cube = new ModelRenderer(this);
		cube.setPos(0, 2.5f, 0);
		cube.texOffs(0, 0).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);
	}
	
	@Override
	public void setupAnim(DiceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
	}
	
	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		cube.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	public void setRotationAngle(float x, float y, float z) {
		cube.xRot = x;
		cube.yRot = y;
		cube.zRot = z;
	}
}
