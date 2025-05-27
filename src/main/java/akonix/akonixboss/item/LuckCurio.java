package akonix.akonixboss.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class LuckCurio extends CurioBaseItem {
	public static final String TOOLTIP_LUCK = "item.akonixboss .desc.luck";
	
	public LuckCurio(Properties properties, Object... args) {
		super(properties, true, args);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable(TOOLTIP_LUCK).withStyle(ChatFormatting.GRAY));
	}

}
