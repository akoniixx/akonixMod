package akonix.akonixboss.block;

import akonix.akonixboss.registry.MYFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MYFSkullBlockEntity extends SkullBlockEntity {

	public MYFSkullBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return MYFBlocks.headType.get();
	}
}
