package akonix.akonixboss.registry;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.*;
import akonix.akonixboss.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = AkonixBoss.MODID)
public class MYFEntities {
	public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AkonixBoss.MODID);
	//Bosses
	public static RegistryObject<EntityType<BellringerEntity>> THE_MORMAYII;
	public static RegistryObject<EntityType<DameFortunaEntity>> DAME_FORTUNA;
	public static RegistryObject<EntityType<SwampjawEntity>> SWAMPJAW;
	public static RegistryObject<EntityType<RosalyneEntity>> THE_JOCKPAD;
	public static RegistryObject<EntityType<TheLychaEntity>> THE_LYCHA;
	public static RegistryObject<EntityType<MiniLychaEntity>> MINI_LYCHA;
	//Summons
	public static RegistryObject<EntityType<RoseSpiritEntity>> JOCKPAD_SPIRIT;
	
	//Projectiles
	public static RegistryObject<EntityType<ProjectileLineEntity>> PROJECTILE_LINE;
	public static RegistryObject<EntityType<ProjectileTargetedEntity>> PROJECTILE_TARGETED;
	public static RegistryObject<EntityType<FortunaBombEntity>> FORTUNA_BOMB;
	public static RegistryObject<EntityType<FortunaCardEntity>> FORTUNA_CARD;
	public static RegistryObject<EntityType<SwampMineEntity>> SWAMP_MINE;
	public static RegistryObject<EntityType<LychaTNTEntity>> LYCHA_TNT;

	static {
		THE_LYCHA = REG.register("the_lycha", () -> EntityType.Builder.<TheLychaEntity>of(TheLychaEntity::new, MobCategory.MONSTER)
				.sized(0.8f, 2.0f)
				.setUpdateInterval(1).setTrackingRange(128).setShouldReceiveVelocityUpdates(true).build(""));

		MINI_LYCHA = REG.register("mini_lycha", () -> EntityType.Builder.<MiniLychaEntity>of(MiniLychaEntity::new, MobCategory.MONSTER)
				.sized(0.4f, 1.0f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
		LYCHA_TNT = REG.register("lycha_tnt", () -> EntityType.Builder.<LychaTNTEntity>of(LychaTNTEntity::new, MobCategory.MISC)
				.sized(0.98F, 0.98F)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));

		THE_MORMAYII = REG.register("the_mormayii", () -> EntityType.Builder.<BellringerEntity>of(BellringerEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f)
				.setUpdateInterval(1).setTrackingRange(128).setShouldReceiveVelocityUpdates(true).build(""));
		DAME_FORTUNA = REG.register("dame_fortuna", () -> EntityType.Builder.<DameFortunaEntity>of(DameFortunaEntity::new, MobCategory.MONSTER).sized(0.6f, 2.325f)
				.setUpdateInterval(1).setTrackingRange(128).setShouldReceiveVelocityUpdates(true).build(""));
		SWAMPJAW = REG.register("swampjaw", () -> EntityType.Builder.<SwampjawEntity>of(SwampjawEntity::new, MobCategory.MONSTER).sized(2.6f, 1.6f)
				.setUpdateInterval(1).setTrackingRange(128).setShouldReceiveVelocityUpdates(true).build(""));
		THE_JOCKPAD = REG.register("the_jockpad", () -> EntityType.Builder.<RosalyneEntity>of(RosalyneEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f)
				.setUpdateInterval(1).setTrackingRange(128).setShouldReceiveVelocityUpdates(true).build(""));
		JOCKPAD_SPIRIT = REG.register("jockpad_spirit", () -> EntityType.Builder.<RoseSpiritEntity>of(RoseSpiritEntity::new, MobCategory.MONSTER).sized(0.75f, 1.3125f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));

		PROJECTILE_LINE = REG.register("projectile_line", () -> EntityType.Builder.<ProjectileLineEntity>of(ProjectileLineEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
		PROJECTILE_TARGETED = REG.register("projectile_targeted", () -> EntityType.Builder.<ProjectileTargetedEntity>of(ProjectileTargetedEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
		FORTUNA_BOMB = REG.register("fortuna_bomb", () -> EntityType.Builder.<FortunaBombEntity>of(FortunaBombEntity::new, MobCategory.MISC).sized(0.3125f, 0.3125f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
		FORTUNA_CARD = REG.register("fortuna_card", () -> EntityType.Builder.<FortunaCardEntity>of(FortunaCardEntity::new, MobCategory.MISC).sized(1.75f, 2.5f)
				.setUpdateInterval(1).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
		SWAMP_MINE = REG.register("swamp_mine", () -> EntityType.Builder.<SwampMineEntity>of(SwampMineEntity::new, MobCategory.MISC).sized(1, 1).setUpdateInterval(1)
				.setTrackingRange(64).setShouldReceiveVelocityUpdates(true).build(""));
	}

	@SubscribeEvent
	public static void registerEntityAttributes(final EntityAttributeCreationEvent event) {
		event.put(THE_MORMAYII.get(), BellringerEntity.createAttributes().build());
		event.put(DAME_FORTUNA.get(), DameFortunaEntity.createAttributes().build());
		event.put(SWAMPJAW.get(), SwampjawEntity.createAttributes().build());
		event.put(THE_JOCKPAD.get(), RosalyneEntity.createAttributes().build());
		event.put(JOCKPAD_SPIRIT.get(), RoseSpiritEntity.createAttributes().build());
		event.put(THE_LYCHA.get(), TheLychaEntity.createAttributes().build());
		event.put(MINI_LYCHA.get(), MiniLychaEntity.createAttributes().build());
	}
}
