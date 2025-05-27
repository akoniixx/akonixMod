package akonix.akonixboss.entity;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.entity.ai.MoveFrontOfTarget;
import akonix.akonixboss.entity.ai.VexMoveRandomGoal;
import akonix.akonixboss.entity.movement.VexMovementController;
import akonix.akonixboss.registry.MYFEntities;
import akonix.akonixboss.registry.MYFSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class TheLychaEntity extends BossEntity {
    public int attackCooldown;
    private int nextAttack = 0; // 0 = TNT, 1 = Summon, 2 = Lava
    private static final int TNT_ATTACK = 0, SUMMON_ATTACK = 1, LAVA_ATTACK = 2;

    public TheLychaEntity(EntityType<? extends TheLychaEntity> type, Level worldIn) {
        super(type, worldIn);
        moveControl = new VexMovementController(this);
        xpReward = 150;
    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {
        super.move(typeIn, pos);
        checkInsideBlocks();
    }

    @Override
    public void tick() {
        noPhysics = true;
        super.tick();
        noPhysics = false;
        setNoGravity(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new TNTAttack(this));
        goalSelector.addGoal(2, new SummonAttack(this));
        goalSelector.addGoal(3, new LavaAttack(this));
        goalSelector.addGoal(7, new MoveFrontOfTarget(this, 1));
        goalSelector.addGoal(8, new VexMoveRandomGoal(this, 0.25));
        goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.FOLLOW_RANGE, 64);
    }

    public static void spawn(Player player, Level world) {
        RandomSource rand = player.getRandom();
        TheLychaEntity lycha = MYFEntities.THE_LYCHA.get().create(world);
        lycha.moveTo(player.getX() + rand.nextInt(7) - 3,
                player.getY() + rand.nextInt(5) + 3,
                player.getZ() + rand.nextInt(7) - 3,
                rand.nextFloat() * 360 - 180, 0);
        lycha.attackCooldown = 100;
        if (!player.getAbilities().instabuild) lycha.setTarget(player);
        lycha.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2));

        ForgeEventFactory.onFinalizeSpawn(lycha, (ServerLevel) world,
                world.getCurrentDifficultyAt(lycha.blockPosition()), MobSpawnType.EVENT, null, null);
        world.addFreshEntity(lycha);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, net.minecraft.sounds.SoundSource.PLAYERS, 2, 1);
    }

    @Override
    public void customServerAiStep() {
        if (attackCooldown > 0) attackCooldown--;
        super.customServerAiStep();
    }

    private void rollNextAttack() {
        nextAttack = random.nextInt(3); // 0, 1, or 2
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("AttackCooldown")) attackCooldown = compound.getInt("AttackCooldown");
        if (compound.contains("NextAttack")) nextAttack = compound.getInt("NextAttack");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AttackCooldown", attackCooldown);
        compound.putInt("NextAttack", nextAttack);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected SoundEvent getMusic() {
        return MYFSounds.musicFortuna.get(); // ใช้เพลงของ Bellringer ชั่วคราว
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return AkonixBoss.rl("the_lycha");
    }

    // TNT Attack Goal
    private static class TNTAttack extends Goal {
        private TheLychaEntity lycha;
        private LivingEntity target;
        private int attackDelay;
        private int tntCount;

        public TNTAttack(TheLychaEntity lycha) {
            this.lycha = lycha;
        }

        @Override
        public boolean canUse() {
            return lycha.nextAttack == TNT_ATTACK && lycha.attackCooldown <= 0
                    && lycha.getTarget() != null && lycha.getTarget().isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            lycha.attackCooldown = 2;
            attackDelay = 10;
            tntCount = 2 + lycha.random.nextInt(3); // 3-5 TNT
            target = lycha.getTarget();
            lycha.playSound(SoundEvents.BLAZE_SHOOT, 2, 1);
        }

        @Override
        public void tick() {
            lycha.attackCooldown = 2;
            attackDelay--;
            if (attackDelay <= 0 && tntCount > 0) {
                tntCount--;
                performTNTAttack();
                attackDelay = 15; // ระยะห่างระหว่าง TNT
            }
        }

        private void performTNTAttack() {
            // สร้าง TNT ที่ตำแหน่งใกล้ผู้เล่น
            double tx = target.getX() + lycha.random.nextGaussian() * 2;
            double ty = target.getY() + lycha.random.nextDouble() * 2;
            double tz = target.getZ() + lycha.random.nextGaussian() * 2;

            // สร้าง Custom TNT Entity ที่ไม่ทำร้าย boss และพังบล็อกน้อยกว่า
            LychaTNTEntity tnt = new LychaTNTEntity(lycha.level(), tx, ty, tz, lycha);
            tnt.setFuse(40); // 2 วินาที
            lycha.level().addFreshEntity(tnt);

            lycha.playSound(SoundEvents.TNT_PRIMED, 1, 1);
        }

        @Override
        public void stop() {
            lycha.attackCooldown = 60 + lycha.random.nextInt(41);
            lycha.rollNextAttack();
        }

        @Override
        public boolean canContinueToUse() {
            return tntCount > 0 && target.isAlive();
        }
    }

    // Summon Attack Goal
    private static class SummonAttack extends Goal {
        private TheLychaEntity lycha;
        private int summonDelay;

        public SummonAttack(TheLychaEntity lycha) {
            this.lycha = lycha;
        }

        @Override
        public boolean canUse() {
            return lycha.nextAttack == SUMMON_ATTACK && lycha.attackCooldown <= 0
                    && lycha.getTarget() != null && lycha.getTarget().isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            lycha.attackCooldown = 2;
            summonDelay = 30;
            lycha.playSound(SoundEvents.EVOKER_CAST_SPELL, 2, 1);
        }

        @Override
        public void tick() {
            lycha.attackCooldown = 2;
            summonDelay--;
            if (summonDelay <= 0) {
                performSummon();
            }
        }

        private void performSummon() {
            // สร้าง Mini Lycha 3 ตัว
            for (int i = 0; i < 3; i++) {
                MiniLychaEntity mini = new MiniLychaEntity(lycha.level(), lycha);
                double angle = (i * 120) * Math.PI / 180; // 120 องศาห่างกัน
                double distance = 3;
                double x = lycha.getX() + Math.cos(angle) * distance;
                double z = lycha.getZ() + Math.sin(angle) * distance;
                mini.moveTo(x, lycha.getY(), z, lycha.random.nextFloat() * 360, 0);
                mini.setTarget(lycha.getTarget());
                lycha.level().addFreshEntity(mini);
            }
            lycha.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 2, 1);
        }

        @Override
        public void stop() {
            lycha.attackCooldown = 80 + lycha.random.nextInt(41);
            lycha.rollNextAttack();
        }

        @Override
        public boolean canContinueToUse() {
            return summonDelay > 0;
        }
    }

    // Lava Attack Goal
    private static class LavaAttack extends Goal {
        private TheLychaEntity lycha;
        private LivingEntity target;
        private int lavaDelay;
        private int lavaCount;

        public LavaAttack(TheLychaEntity lycha) {
            this.lycha = lycha;
        }

        @Override
        public boolean canUse() {
            return lycha.nextAttack == LAVA_ATTACK && lycha.attackCooldown <= 0
                    && lycha.getTarget() != null && lycha.getTarget().isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            lycha.attackCooldown = 5;
            lavaDelay = 25;
            lavaCount =  lycha.random.nextInt(2); // 2-3 ครั้ง
            target = lycha.getTarget();
            lycha.playSound(SoundEvents.LAVA_AMBIENT, 2, 1);
        }

        @Override
        public void tick() {
            lycha.attackCooldown = 2;
            lavaDelay--;
            if (lavaDelay <= 0 && lavaCount > 0) {
                lavaCount--;
                performLavaAttack();
                lavaDelay = 30; // ระยะห่างระหว่างการเสก lava
            }
        }

        private void performLavaAttack() {
            BlockPos centerPos = target.blockPosition();

            // สร้าง lava ในพื้นที่ 3x3 รอบผู้เล่น
            for (int x = 0; x <= 1; x++) {
                for (int z = 0; z <= 1; z++) {
                    BlockPos pos = centerPos.offset(x, 0, z);
                    if (lycha.level().getBlockState(pos).canBeReplaced() ||
                            lycha.level().getBlockState(pos).is(Blocks.GRASS) ||
                            lycha.level().getBlockState(pos).is(Blocks.DIRT)) {
                        lycha.level().setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
                    }
                }
            }

            lycha.playSound(SoundEvents.LAVA_POP, 1, 1);
        }

        @Override
        public void stop() {
            lycha.attackCooldown = 50 + lycha.random.nextInt(31);
            lycha.rollNextAttack();
        }

        @Override
        public boolean canContinueToUse() {
            return lavaCount > 0 && target.isAlive();
        }
    }
}