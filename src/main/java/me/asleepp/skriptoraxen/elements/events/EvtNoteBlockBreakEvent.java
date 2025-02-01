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
import io.th0rgal.oraxen.api.events.noteblock.OraxenNoteBlockBreakEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
@Name("On Custom Note Block Break")
@Description({"Fires when a Oraxen note block gets broken."})
@Examples({"on break of custom note block:"})
@Since("1.0")
public class EvtNoteBlockBreakEvent extends SkriptEvent {

    private Literal<String> noteBlockID;

    static {
        Skript.registerEvent("Custom Note Block Break", EvtNoteBlockBreakEvent.class, OraxenNoteBlockBreakEvent.class, "break of (custom|oraxen) (music|note) block [%string%]");
        EventValues.registerEventValue(OraxenNoteBlockBreakEvent.class, Player.class, new Getter<Player, OraxenNoteBlockBreakEvent>() {
            @Override
            public Player get(OraxenNoteBlockBreakEvent arg) {
                return arg.getPlayer();
            }
        }, 0);
        EventValues.registerEventValue(OraxenNoteBlockBreakEvent.class, Block.class, new Getter<Block, OraxenNoteBlockBreakEvent>() {
            @Override
            public Block get(OraxenNoteBlockBreakEvent arg) {
                return arg.getBlock();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        noteBlockID = (Literal<String>) args[0];
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        if (e instanceof OraxenNoteBlockBreakEvent event) {
            if (noteBlockID == null) {
                return !event.isCancelled();
            } else {
                String id = event.getMechanic().getItemID();
                if (id.equals(noteBlockID.getSingle(e))) {
                    return !event.isCancelled();
                }
            }
        }
        return false;
    }


    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "custom note block break" + (noteBlockID != null ? noteBlockID.toString(e, debug) : "");
    }
}
