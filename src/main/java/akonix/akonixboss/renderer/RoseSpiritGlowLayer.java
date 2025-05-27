package akonix.akonixboss.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.RoseSpiritEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RoseSpiritGlowLayer extends RenderLayer<RoseSpiritEntity, RoseSpiritModel> {
	//And I copied Rosalyne
	private static final RenderType NEUTRAL = RenderType.entityTranslucentEmissive(AkonixBoss.rl("textures/entity/rose_spirit_neutral.png"), false), 
			SHOOTING = RenderType.entityTranslucentEmissive(AkonixBoss.rl("textures/entity/rose_spirit_shooting.png"), false), 
			HURT = RenderType.entityTranslucentEmissive(AkonixBoss.rl("textures/entity/rose_spirit_hurt.png"), false);

	public RoseSpiritGlowLayer(RenderLayerParent<RoseSpiritEntity, RoseSpiritModel> parent) {
		super(parent);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int p_117351_, RoseSpiritEntity entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
		int status = entity.getStatus();
		//Copied from EyesLayer, so lots of numbers I don't know what they mean
		RenderType texture = NEUTRAL;
		if (status == RoseSpiritEntity.ATTACKING) texture =  SHOOTING;
		else if (status == RoseSpiritEntity.HURT || status == RoseSpiritEntity.RETRACTING_HURT) texture = HURT;
		VertexConsumer vertexconsumer = buffer.getBuffer(texture);
		getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}

}
