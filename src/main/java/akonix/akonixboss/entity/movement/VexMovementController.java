package akonix.akonixboss.entity.movement;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class VexMovementController extends MoveControl {
	//Speed multiplied by this every tick when we reach the destination
	private double slowdown = 0.5;
	//This is just the Vex's movement controller but separated and a bit cleaned up
	public VexMovementController(Mob mob) {
		super(mob);
	}
	
	public VexMovementController slowdown(double slowdown) {
		this.slowdown = slowdown;
		return this;
	}

    @Override
	public void tick() {
       if (operation == MoveControl.Operation.MOVE_TO) {
          Vec3 vector3d = new Vec3(wantedX - mob.getX(), wantedY - mob.getY(), wantedZ - mob.getZ());
          double d0 = vector3d.length();
          if (d0 < mob.getBoundingBox().getSize()) {
             operation = MoveControl.Operation.WAIT;
             mob.setDeltaMovement(mob.getDeltaMovement().scale(slowdown));
          } else {
             mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.05D / d0)));
             if (mob.getTarget() == null) {
                Vec3 vector3d1 = mob.getDeltaMovement();
                mob.setYRot(-((float)Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float)Math.PI));
                mob.yBodyRot = mob.getYRot();
             } else {
                double d2 = mob.getTarget().getX() - mob.getX();
                double d1 = mob.getTarget().getZ() - mob.getZ();
                mob.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                mob.yBodyRot = mob.getYRot();
             }
          }

       }
    }

}
