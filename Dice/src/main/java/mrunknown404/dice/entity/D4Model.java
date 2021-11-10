package mrunknown404.dice.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.BufferBuilder;

public class D4Model extends DiceModel {
	public D4Model() {
		
	}
	
	@Override
	public void setupRotation(DiceEntity dice) {
		
	}
	
	@Override
	protected void setRotationAngle(float x, float y, float z) {
		
	}
	
	@Override
	public void renderToBuffer(MatrixStack stack, IVertexBuilder buf, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		BufferBuilder b = (BufferBuilder) buf;
	}
}
