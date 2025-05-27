package akonix.akonixboss.renderer;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.TheLychaEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TheLychaRenderer extends MobRenderer<TheLychaEntity, PlayerModel<TheLychaEntity>> {
    // ใช้ path ที่ถูกต้อง
    private static final ResourceLocation TEXTURE = AkonixBoss.rl("textures/entity/the_lycha.png");

    @SuppressWarnings({"unchecked", "rawtypes"})
    public TheLychaRenderer(Context context) {
        // ใช้ PlayerModel แทน
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(TheLychaEntity entity) {
        return TEXTURE;
    }
}