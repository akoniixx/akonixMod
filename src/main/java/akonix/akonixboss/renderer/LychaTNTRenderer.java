package akonix.akonixboss.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import akonix.akonixboss.entity.LychaTNTEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class LychaTNTRenderer extends EntityRenderer<LychaTNTEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public LychaTNTRenderer(Context context) {
        super(context);
        shadowRadius = 0.5F;
        blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(LychaTNTEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);

        int fuse = entity.getFuse();
        if ((float)fuse - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)fuse - partialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float scale = 1.0F + f * 0.3F;
            poseStack.scale(scale, scale, scale);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

        // ใช้ BlockRenderDispatcher แทน ModelPart
        blockRenderer.renderSingleBlock(
                Blocks.TNT.defaultBlockState(),
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(LychaTNTEntity entity) {
        return new ResourceLocation("textures/block/tnt_side.png");
    }
}