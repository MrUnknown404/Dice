package mrunknown404.dice.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mrunknown404.dice.Dice;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class D6Model extends DiceModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Dice.MOD_ID, "dice_entity"), "main");
	private final ModelPart bb_main;
	
	public D6Model(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, -2.5f, -2.5f, 5, 5, 5, new CubeDeformation(0)),
				PartPose.offset(0, 2.5f, 0));
		
		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	
	@Override
	public void setupAnim(DiceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public void setupRotation(DiceEntity dice) {
		final float r1 = (float) Math.PI / 2f;
		
		switch (dice.getRolled()) {
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
	protected void setRotationAngle(float x, float y, float z) {
		bb_main.xRot = x;
		bb_main.yRot = y;
		bb_main.zRot = z;
	}
}
