package akonix.akonixboss.entity;

import javax.annotation.Nullable;

import akonix.akonixboss.registry.MYFEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class LychaTNTEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(LychaTNTEntity.class, EntityDataSerializers.INT);
    private LivingEntity owner; // The Lycha ที่สร้าง TNT นี้
    private static final double GRAVITY = -0.04;

    public LychaTNTEntity(EntityType<? extends LychaTNTEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        blocksBuilding = true;
    }

    public LychaTNTEntity(Level worldIn, double x, double y, double z, @Nullable LivingEntity owner) {
        this(MYFEntities.LYCHA_TNT.get(), worldIn);
        this.setPos(x, y, z);
        double angle = worldIn.random.nextDouble() * Math.PI * 2;
        setDeltaMovement(-Math.sin(angle) * 0.02, 0.2, -Math.cos(angle) * 0.02);
        setFuse(80);
        xo = x;
        yo = y;
        zo = z;
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_FUSE_ID, 80);
    }

    public void setFuse(int fuse) {
        entityData.set(DATA_FUSE_ID, fuse);
    }

    public int getFuse() {
        return entityData.get(DATA_FUSE_ID);
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    @Override
    public void tick() {
        if (!isNoGravity()) {
            setDeltaMovement(getDeltaMovement().add(0.0D, GRAVITY, 0.0D));
        }

        move(MoverType.SELF, getDeltaMovement());
        setDeltaMovement(getDeltaMovement().scale(0.98));

        if (onGround()) {
            setDeltaMovement(getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int fuse = getFuse() - 1;
        setFuse(fuse);

        if (fuse <= 0) {
            remove(RemovalReason.KILLED);
            if (!level().isClientSide) {
                explode();
            }
        } else {
            updateInWaterStateAndDoFluidPushing();
            if (level().isClientSide) {
                level().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.5D, getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void explode() {
        // ระเบิดแบบ Mob (พังบล็อกน้อยกว่า TNT ธรรมดา)
        level().explode(owner != null ? owner : this,
                getX(), getY(), getZ(),
                1.5f, // รัศมีเล็ก
                Level.ExplosionInteraction.MOB); // พังบล็อกแบบ Mob
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putShort("Fuse", (short) getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        setFuse(compound.getShort("Fuse"));
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.15F;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

