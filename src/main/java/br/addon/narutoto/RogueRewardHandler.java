package br.addon.narutoto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.capabilities.Capability;

@Mod.EventBusSubscriber(modid = NarutotoAddon.MODID)
public final class RogueRewardHandler {
    private RogueRewardHandler() {}

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity victim = event.getEntity();
        if (victim.level().isClientSide) return;
        if (!(event.getSource().getEntity() instanceof ServerPlayer killer)) return;

        String id = victim.getType().builtInRegistryHolder().key().location().toString();
        if (!id.equals("narutoto:missing_nin")
                && !id.equals("narutoto:missing_nin_wild")
                && !id.equals("narutoto:missing_nin_camp")) return;

        try {
            Class<?> variables = Class.forName("net.mcreator.narutoto.network.NarutotoModVariables");
            Field capField = variables.getField("PLAYER_VARIABLES_CAPABILITY");
            @SuppressWarnings("unchecked")
            Capability<Object> capability = (Capability<Object>) capField.get(null);

            killer.getCapability(capability).ifPresent(data -> {
                try {
                    add(data, "ninja_experience", 3000.0);
                    add(data, "attribute_point", 200.0);
                    add(data, "jutsu_point", 100.0);
                    Method sync = data.getClass().getMethod("syncPlayerVariables", Entity.class);
                    sync.invoke(data, killer);
                } catch (ReflectiveOperationException ex) {
                    throw new IllegalStateException("Falha ao aplicar recompensas NarutoTo", ex);
                }
            });
            killer.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                    "+3000 EX  +200 SP  +100 JP"));
        } catch (ReflectiveOperationException ex) {
            NarutotoAddon.LOGGER.error("Não foi possível acessar as variáveis do NarutoTo para entregar recompensas.", ex);
        }
    }

    private static void add(Object data, String fieldName, double amount) throws ReflectiveOperationException {
        Field field = data.getClass().getField(fieldName);
        field.setDouble(data, field.getDouble(data) + amount);
    }
}
