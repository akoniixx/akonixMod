package akonix.akonixboss.registry;

import akonix.akonixboss.AkonixBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MYFSounds {
	public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AkonixBoss.MODID);
	//Bosses
	public static RegistryObject<SoundEvent> bellringerIdle, bellringerHurt, bellringerDeath;
	public static RegistryObject<SoundEvent> dameFortunaIdle, dameFortunaHurt, dameFortunaDeath, dameFortunaAttack, dameFortunaShoot;
	public static RegistryObject<SoundEvent> dameFortunaChipsStart, dameFortunaChipsFire, dameFortunaCardStart, dameFortunaCardRight, dameFortunaCardWrong;
	public static RegistryObject<SoundEvent> dameFortunaSpinStart, dameFortunaSpinLoop, dameFortunaSpinStop, dameFortunaSnap, dameFortunaClap;
	public static RegistryObject<SoundEvent> swampjawIdle, swampjawHurt, swampjawDeath, swampjawCharge, swampjawBomb, swampjawStun;
	public static RegistryObject<SoundEvent> theJockpadHurt, theJockpadDeath, theJockpadCrack, theJockpadSwing, theJockpadSwingPrepare;
	public static RegistryObject<SoundEvent> roseSpiritIdle, roseSpiritHurt, roseSpiritHurtBig, roseSpiritDeath, roseSpiritWarn, roseSpiritShoot;
	//Items
	public static RegistryObject<SoundEvent> slicersDiceProc, aceOfIronProc, cagedHeartProc;
	//Music
	public static RegistryObject<SoundEvent> musicSwampjaw, musicBellringer, musicFortuna, musicTheJockpad;
	
	static {
		//Bosses
		bellringerIdle = initSound("entity.bellringer.idle");
		bellringerHurt = initSound("entity.bellringer.hurt");
		bellringerDeath = initSound("entity.bellringer.death");
		
		dameFortunaIdle = initSound("entity.dame_fortuna.idle");
		dameFortunaHurt = initSound("entity.dame_fortuna.hurt");
		dameFortunaDeath = initSound("entity.dame_fortuna.death");
		dameFortunaAttack = initSound("entity.dame_fortuna.attack");
		dameFortunaShoot = initSound("entity.dame_fortuna.shoot");
		dameFortunaChipsStart = initSound("entity.dame_fortuna.chips.start");
		dameFortunaChipsFire = initSound("entity.dame_fortuna.chips.fire");
		dameFortunaCardStart = initSound("entity.dame_fortuna.card.start");
		dameFortunaCardRight = initSound("entity.dame_fortuna.card.right");
		dameFortunaCardWrong = initSound("entity.dame_fortuna.card.wrong");
		dameFortunaSpinStart = initSound("entity.dame_fortuna.spin.start");
		dameFortunaSpinLoop = initSound("entity.dame_fortuna.spin.loop");
		dameFortunaSpinStop = initSound("entity.dame_fortuna.spin.stop");
		dameFortunaSnap = initSound("entity.dame_fortuna.snap");
		dameFortunaClap = initSound("entity.dame_fortuna.clap");
		
		swampjawIdle = initSound("entity.swampjaw.idle");
		swampjawHurt = initSound("entity.swampjaw.hurt");
		swampjawDeath = initSound("entity.swampjaw.death");
		swampjawCharge = initSound("entity.swampjaw.charge");
		swampjawBomb = initSound("entity.swampjaw.bomb");
		swampjawStun = initSound("entity.swampjaw.stun");

		theJockpadHurt = initSound("entity.the_jockpad.hurt");
		theJockpadDeath = initSound("entity.the_jockpad.death");
		theJockpadCrack = initSound("entity.the_jockpad.crack");
		theJockpadSwing = initSound("entity.the_jockpad.swing");
		theJockpadSwingPrepare = initSound("entity.the_jockpad.swing.prepare");

		
		roseSpiritIdle = initSound("entity.rosespirit.idle");
		roseSpiritHurt = initSound("entity.rosespirit.hurt");
		roseSpiritHurtBig = initSound("entity.rosespirit.hurt.big");
		roseSpiritDeath = initSound("entity.rosespirit.death");
		roseSpiritWarn = initSound("entity.rosespirit.warn");
		roseSpiritShoot = initSound("entity.rosespirit.shoot");

		//Items
		slicersDiceProc = initSound("item.proc.slicers_dice");
		aceOfIronProc = initSound("item.proc.ace_of_iron");
		cagedHeartProc = initSound("item.proc.caged_heart");

		//Music
		musicSwampjaw = initSound("music.swampjaw");
		musicBellringer = initSound("music.bellringer");
		musicFortuna = initSound("music.fortuna");
		musicTheJockpad = initSound("music.the_jockpad");
	}
	public static RegistryObject<SoundEvent> initSound(String name) {
		return REG.register(name, () -> SoundEvent.createVariableRangeEvent(AkonixBoss.rl(name)));
	}
}
