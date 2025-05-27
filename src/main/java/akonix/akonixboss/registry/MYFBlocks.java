package akonix.akonixboss.registry;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.block.MYFSkullBlock;
import akonix.akonixboss.block.MYFSkullBlockEntity;
import akonix.akonixboss.block.MYFWallSkullBlock;
import akonix.akonixboss.misc.MYFHeads;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MYFBlocks {
	public static final DeferredRegister<Block> REG = DeferredRegister.create(ForgeRegistries.BLOCKS, AkonixBoss.MODID);
	public static final DeferredRegister<BlockEntityType<?>> REG_BE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AkonixBoss.MODID);
	public static RegistryObject<Block> theMormayiiHead, theMormayiiHeadWall;
	public static RegistryObject<Block> fortunaHead, fortunaHeadWall;
	public static RegistryObject<Block> swampjawHead, swampjawHeadWall;
	public static RegistryObject<Block> theJockpadHead, theJockpadHeadWall, theJockpadCracked, theJockpadCrackedWall;
	
	public static RegistryObject<BlockEntityType<MYFSkullBlockEntity>> headType;
	
	static {
		theMormayiiHead = REG.register("the_mormayii_head", () -> new MYFSkullBlock(MYFHeads.THE_MORMAYII, skull()));
		theMormayiiHeadWall = REG.register("the_mormayii_wall_head", () -> new MYFWallSkullBlock(MYFHeads.THE_MORMAYII, skull().lootFrom(theMormayiiHead)));
		fortunaHead = REG.register("dame_fortuna_head", () -> new MYFSkullBlock(MYFHeads.DAME_FORTUNA, skull()));
		fortunaHeadWall = REG.register("dame_fortuna_wall_head", () -> new MYFWallSkullBlock(MYFHeads.DAME_FORTUNA, skull().lootFrom(fortunaHead)));
		swampjawHead = REG.register("swampjaw_head", () -> new MYFSkullBlock(MYFHeads.SWAMPJAW, skull()));
		//TODO custom block and renderer for collision and offset when on the wall (it gets buried in the wall)
		swampjawHeadWall = REG.register("swampjaw_wall_head", () -> new MYFWallSkullBlock(MYFHeads.SWAMPJAW, skull().lootFrom(swampjawHead)));
		theJockpadHead = REG.register("the_jockpad_head", () -> new MYFSkullBlock(MYFHeads.THE_JOCKPAD, skull()));
		theJockpadHeadWall = REG.register("the_jockpad_wall_head", () -> new MYFWallSkullBlock(MYFHeads.THE_JOCKPAD, skull().lootFrom(theJockpadHead)));
		theJockpadCracked = REG.register("the_jockpad_head_cracked", () -> new MYFSkullBlock(MYFHeads.THE_JOCKPAD_CRACKED, skull()));
		theJockpadCrackedWall = REG.register("the_jockpad_wall_head_cracked", () -> new MYFWallSkullBlock(MYFHeads.THE_JOCKPAD_CRACKED, skull().lootFrom(theJockpadCracked)));
		
		headType = REG_BE.register("head", () -> BlockEntityType.Builder.of(MYFSkullBlockEntity::new,
				theMormayiiHead.get(), theMormayiiHeadWall.get(),
				fortunaHead.get(), fortunaHeadWall.get(),
				swampjawHead.get(), swampjawHeadWall.get(),
				theJockpadHead.get(), theJockpadHeadWall.get(),
				theJockpadCracked.get(), theJockpadCrackedWall.get()
				).build(null));
	}
	
	private static BlockBehaviour.Properties skull() {
		return BlockBehaviour.Properties.of().strength(1).pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.CUSTOM_HEAD);
	}

}
