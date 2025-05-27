package akonix.akonixboss.renderer;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.BellringerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BellringerRenderer extends HumanoidMobRenderer<BellringerEntity, HumanoidModel<BellringerEntity>> {
	public static final ResourceLocation TEXTURE = AkonixBoss.rl("textures/entity/bellringer.png");
	public static final ResourceLocation GLOW = AkonixBoss.rl("textures/entity/bellringer_glow.png");

	public BellringerRenderer(Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);

		addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
		addLayer(new GenericGlowLayer<>(this, GLOW));
	}

	@Override
	public ResourceLocation getTextureLocation(BellringerEntity entity) {
		return TEXTURE;
	}
}