package akonix.akonixboss;

import akonix.akonixboss.misc.MYFHeads;
import akonix.akonixboss.registry.CompatGWRItems;
import akonix.akonixboss.registry.MYFBlocks;
import akonix.akonixboss.registry.MYFEntities;
import akonix.akonixboss.registry.MYFItems;
import akonix.akonixboss.renderer.*;
import akonix.akonixboss.renderer.*;
import net.minecraft.Util;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = AkonixBoss.MODID, value = Dist.CLIENT)
public class ClientStuff {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		//Entities
		event.registerEntityRenderer(MYFEntities.THE_MORMAYII.get(), (context) -> new BellringerRenderer(context));
    	event.registerEntityRenderer(MYFEntities.DAME_FORTUNA.get(), (context) -> new DameFortunaRenderer(context));
    	event.registerEntityRenderer(MYFEntities.SWAMPJAW.get(), (context) -> new SwampjawRenderer(context));
		event.registerEntityRenderer(MYFEntities.THE_JOCKPAD.get(), (context) -> new RosalyneRenderer(context));
		event.registerEntityRenderer(MYFEntities.JOCKPAD_SPIRIT.get(), (context) -> new RoseSpiritRenderer(context));

    	event.registerEntityRenderer(MYFEntities.PROJECTILE_LINE.get(), (context) -> new ProjectileLineRenderer(context));
    	event.registerEntityRenderer(MYFEntities.PROJECTILE_TARGETED.get(), (context) -> new ProjectileTargetedRenderer(context));
    	event.registerEntityRenderer(MYFEntities.FORTUNA_BOMB.get(), (context) -> new FortunaBombRenderer(context));
    	event.registerEntityRenderer(MYFEntities.FORTUNA_CARD.get(), (context) -> new FortunaCardRenderer(context));
		event.registerEntityRenderer(MYFEntities.SWAMP_MINE.get(), (context) -> new SwampMineRenderer(context));

		event.registerBlockEntityRenderer(MYFBlocks.headType.get(), MYFSkullBlockRenderer::new);

		event.registerEntityRenderer(MYFEntities.THE_LYCHA.get(), (context) -> new TheLychaRenderer(context));
		event.registerEntityRenderer(MYFEntities.MINI_LYCHA.get(), (context) -> new MiniLychaRenderer(context));
		event.registerEntityRenderer(MYFEntities.LYCHA_TNT.get(), (context) -> new LychaTNTRenderer(context));
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    	event.registerLayerDefinition(BellringerModel.MODEL, () -> BellringerModel.createBodyLayer(CubeDeformation.NONE));
    	event.registerLayerDefinition(BellringerModel.MODEL_HEAD, SkullModel::createHumanoidHeadLayer);
    	event.registerLayerDefinition(DameFortunaModel.MODEL, () -> DameFortunaModel.createBodyLayer(CubeDeformation.NONE));
    	event.registerLayerDefinition(DameFortunaModel.MODEL_ARMOR, () -> DameFortunaModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION));
    	event.registerLayerDefinition(DameFortunaModel.MODEL_HEAD, SkullModel::createHumanoidHeadLayer);
    	event.registerLayerDefinition(SwampjawModel.MODEL, SwampjawModel::createBodyLayer);
    	event.registerLayerDefinition(SwampjawModel.MODEL_HEAD, SwampjawHeadModel::createLayer);
    	event.registerLayerDefinition(RosalyneModel.MODEL, () -> RosalyneModel.createBodyLayer(CubeDeformation.NONE, true));
    	event.registerLayerDefinition(RosalyneModel.MODEL_ARMOR, () -> RosalyneModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION, false));
    	event.registerLayerDefinition(RosalyneModel.MODEL_HEAD, SkullModel::createHumanoidHeadLayer);
    	event.registerLayerDefinition(RoseSpiritModel.MODEL, RoseSpiritModel::createBodyLayer);

    	event.registerLayerDefinition(ProjectileLineModel.MODEL, ProjectileLineModel::createBodyLayer);
    	event.registerLayerDefinition(ProjectileChipsModel.MODEL, ProjectileChipsModel::createBodyLayer);
    	event.registerLayerDefinition(FortunaBombModel.MODEL, FortunaBombModel::createBodyLayer);
    	event.registerLayerDefinition(FortunaCardModel.MODEL, FortunaCardModel::createBodyLayer);
    	event.registerLayerDefinition(SwampMineModel.MODEL, SwampMineModel::createBodyLayer);
		event.registerLayerDefinition(MiniLychaModel.MODEL, MiniLychaModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerHeads(EntityRenderersEvent.CreateSkullModels event) {
    	EntityModelSet set = event.getEntityModelSet();
		event.registerSkullModel(MYFHeads.THE_MORMAYII, new SkullModel(set.bakeLayer(BellringerModel.MODEL_HEAD)));
    	event.registerSkullModel(MYFHeads.DAME_FORTUNA, new SkullModel(set.bakeLayer(DameFortunaModel.MODEL_HEAD)));
    	//this one separated to work around extending the vanilla renderer for the block
    	MYFSkullBlockRenderer.swampjawModel = new SwampjawHeadModel(set.bakeLayer(SwampjawModel.MODEL_HEAD));
    	event.registerSkullModel(MYFHeads.SWAMPJAW, MYFSkullBlockRenderer.swampjawModel);
		event.registerSkullModel(MYFHeads.THE_JOCKPAD, new SkullModel(set.bakeLayer(RosalyneModel.MODEL_HEAD)));
		event.registerSkullModel(MYFHeads.THE_JOCKPAD_CRACKED, new SkullModel(set.bakeLayer(RosalyneModel.MODEL_HEAD)));
    	//I don't know when I should do this or if this is the correct way but it works and prevents a crash
    	//TODO get bellringer and fortuna their glow, which won't work through my renderer cause it's only for the block
		SkullBlockRenderer.SKIN_BY_TYPE.put(MYFHeads.THE_MORMAYII, BellringerRenderer.TEXTURE);
    	SkullBlockRenderer.SKIN_BY_TYPE.put(MYFHeads.DAME_FORTUNA, DameFortunaRenderer.TEXTURE);
    	SkullBlockRenderer.SKIN_BY_TYPE.put(MYFHeads.SWAMPJAW, SwampjawRenderer.TEXTURE);
		SkullBlockRenderer.SKIN_BY_TYPE.put(MYFHeads.THE_JOCKPAD, RosalyneRenderer.BASE);
		SkullBlockRenderer.SKIN_BY_TYPE.put(MYFHeads.THE_JOCKPAD_CRACKED, RosalyneRenderer.CRACKED);
    }

	@SubscribeEvent
    public static void itemColors(final RegisterColorHandlersEvent.Item event) {
		event.register((s, t) -> t == 1 ? Mth.hsvToRgb(((Util.getMillis() / 1000) % 360) / 360f, 1, 1) : -1, MYFItems.cocktailCutlass.get());
		if (AkonixBoss.loadedGunsWithoutRoses()) event.register((s, t) -> t == 1 ? Mth.hsvToRgb(((Util.getMillis() / 1000) % 360) / 360f, 0.75f, 0.75f) : -1, CompatGWRItems.cocktailShotgun.get());
    }

	@SubscribeEvent
	public static void clientStuff(final FMLClientSetupEvent event) {

		//Same as Bow
		ItemProperties.register(MYFItems.depthStar.get(), AkonixBoss.rl("charge"),
				(stack, world, entity, someint) -> entity == null || entity.getUseItem() != stack ? 0 : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F);
		ItemProperties.register(MYFItems.depthStar.get(), AkonixBoss.rl("charging"),
				(stack, world, entity, someint) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
	}

}
