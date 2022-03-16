package mrunknown404.dice.entity;

import java.util.Arrays;
import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class D6Model extends DiceModel {
	private final ModelPart cube;
	
	public D6Model() {
		cube = new ModelPart(Arrays.asList(new ModelPart.Cube(0, 0, -2.5f, -2.5f, -2.5f, 5, 5, 5, 0, 0, 0, false, 32, 32)), new HashMap<>());
		cube.setPos(0, 2.5f, 0);
	}
	
	@Override
	public void setupRotation(DiceEntity dice) {
		float r1 = (float) Math.PI / 2f;
		
		switch (dice.getRoll()) {
			case 1:
				cube.setRotation((float) Math.PI, 0, 0);
				break;
			case 2:
				cube.setRotation(-r1, 0, 0);
				break;
			case 3:
				cube.setRotation(0, 0, r1);
				break;
			case 4:
				cube.setRotation(0, 0, -r1);
				break;
			case 5:
				cube.setRotation(r1, 0, 0);
				break;
			case 6:
				cube.setRotation(0, 0, 0);
				break;
		}
	}
	
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer buf, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		cube.render(stack, buf, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
