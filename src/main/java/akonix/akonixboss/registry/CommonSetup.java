package akonix.akonixboss.registry;

import akonix.akonixboss.AkonixBoss;
import akonix.akonixboss.item.CocktailCutlass;
import akonix.akonixboss.item.compat.CocktailShotgun;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = AkonixBoss.MODID)
public class CommonSetup {	
	@SubscribeEvent
	public static void commonSetup(final FMLCommonSetupEvent event) {
		CocktailCutlass.initEffects();
		if (AkonixBoss.loadedGunsWithoutRoses()) CocktailShotgun.initEffects();
	}
}
