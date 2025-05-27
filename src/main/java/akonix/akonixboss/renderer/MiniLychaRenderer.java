package akonix.akonixboss.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.MiniLychaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MiniLychaRenderer extends MobRenderer<MiniLychaEntity, MiniLychaModel> {
    private static final ResourceLocation TEXTURE = AkonixBoss.rl("textures/entity/mini_lycha.png");

    public MiniLychaRenderer(Context context) {
        super(context, new MiniLychaModel(context.bakeLayer(MiniLychaModel.MODEL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(MiniLychaEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(MiniLychaEntity entity, PoseStack poseStack, float partialTicks) {
        // ขนาดใหญ่ขึ้น (เดิม 0.6f)
        poseStack.scale(0.8f, 0.8f, 0.8f);
    }
}
