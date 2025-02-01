package me.asleepp.skriptoraxen.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurniturePlaceEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
@Name("On Custom Furniture Place")
@Description({"Fires when an Oraxen furniture gets placed."})
@Examples({"on place of custom furniture:"})
@Since("1.0")
public class EvtFurniturePlaceEvent extends SkriptEvent {
    private Literal<String> furnitureID;

    static {
        Skript.registerEvent("Furniture Place", EvtFurniturePlaceEvent.class, OraxenFurniturePlaceEvent.class, "place of (custom|oraxen) furniture [%string%]");
        EventValues.registerEventValue(OraxenFurniturePlaceEvent.class, Player.class, new Getter<Player, OraxenFurniturePlaceEvent>() {
            @Override
            public Player get(OraxenFurniturePlaceEvent arg) {
                return arg.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(OraxenFurniturePlaceEvent.class, ItemStack.class, new Getter<ItemStack, OraxenFurniturePlaceEvent>() {
            @Override
            public ItemStack get(OraxenFurniturePlaceEvent arg) {
                return arg.getItemInHand();
            }
        }, 0);

        EventValues.registerEventValue(OraxenFurniturePlaceEvent.class, Block.class, new Getter<Block, OraxenFurniturePlaceEvent>() {
            @Override
            public Block get(OraxenFurniturePlaceEvent arg) {
                return arg.getBlock();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        furnitureID = (Literal<String>) args[0];
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        if (e instanceof OraxenFurniturePlaceEvent event) {
            if (furnitureID == null) {
                return !event.isCancelled();
            } else {
                String id = event.getMechanic().getItemID();
                if (id.equals(furnitureID.getSingle(e))) {
                    return !event.isCancelled();
                }
            }
        }
        return false;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "custom furniture " + (furnitureID != null ? furnitureID.toString(e, debug) : "");
    }
}
