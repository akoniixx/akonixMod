package akonix.akonixboss.entity;

import akonix.akonixboss.entity.ai.MoveAroundTarget;
import akonix.akonixboss.entity.ai.VexMoveRandomGoal;
import akonix.akonixboss.entity.movement.VexMovementController;
import akonix.akonixboss.registry.MYFEntities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MiniLychaEntity extends Monster {
    private TheLychaEntity owner;
    private int lifeTime = 600; // 30 วินาที (20 ticks = 1 วินาที)

    public MiniLychaEntity(EntityType<? extends MiniLychaEntity> type, Level level) {
        super(type, level);
        moveControl = new VexMovementController(this).slowdown(0.3);
        xpReward = 5;
    }

    public MiniLychaEntity(Level level, TheLychaEntity owner) {
        this(MYFEntities.MINI_LYCHA.get(), level); // ใช้ EntityType ที่ถูกต้อง
        this.owner = owner;
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

        // ลดอายุการใช้งาน
        lifeTime--;
        if (lifeTime <= 0) {
            // ตายเองเมื่อหมดเวลา
            playSound(SoundEvents.VEX_DEATH, 1, 1);
            remove(RemovalReason.KILLED);
        }

        // หากเจ้าของตายแล้ว ก็ตายตามไป
        if (owner != null && !owner.isAlive()) {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        goalSelector.addGoal(2, new MoveAroundTarget(this, 0.8)); // เคลื่อนที่รอบเป้าหมาย
        goalSelector.addGoal(3, new VexMoveRandomGoal(this, 0.5));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20) // HP น้อย
                .add(Attributes.ATTACK_DAMAGE, 6) // ดาเมจน้อย
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.VEX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result && target instanceof Player) {
            // ทำให้ผู้เล่นหงุดหงิด - ใส่ effect ร้ายๆ
            Player player = (Player) target;
            if (random.nextFloat() < 0.3f) { // 30% โอกาส
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.CONFUSION, 60, 0)); // เมา 3 วินาที
            }
        }
        return result;
    }

    public TheLychaEntity getOwner() {
        return owner;
    }
}