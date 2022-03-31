package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.plugins.content.skills.herblore.action.UnfPotAction
import gg.rsmod.plugins.content.skills.herblore.data.UnfPot

private val unfPots = listOf(
    UnfPot(Items.GUAM_LEAF, Items.GUAM_POTION_UNF, 3),
    UnfPot(Items.MARRENTILL, Items.MARRENTILL_POTION_UNF, 5),
    UnfPot(Items.TARROMIN, Items.TARROMIN_POTION_UNF, 12),
    UnfPot(Items.HARRALANDER, Items.HARRALANDER_POTION_UNF, 22),
    UnfPot(Items.RANARR_WEED, Items.RANARR_POTION_UNF, 30),
    UnfPot(Items.TOADFLAX, Items.TOADFLAX_POTION_UNF, 34),
    UnfPot(Items.IRIT_LEAF, Items.IRIT_POTION_UNF, 45),
    UnfPot(Items.AVANTOE, Items.AVANTOE_POTION_UNF, 50),
    UnfPot(Items.KWUARM, Items.KWUARM_POTION_UNF, 55),
    UnfPot(Items.SNAPDRAGON, Items.SNAPDRAGON_POTION_UNF, 63),
    UnfPot(Items.CADANTINE, Items.CADANTINE_POTION_UNF, 66),
    UnfPot(Items.LANTADYME, Items.LANTADYME_POTION_UNF, 69),
    UnfPot(Items.DWARF_WEED, Items.DWARF_WEED_POTION_UNF, 72),
    UnfPot(Items.TORSTOL, Items.TORSTOL_POTION_UNF, 78)
)

unfPots.forEach { unfPot ->
    onItemOnItem(unfPot.cleanHerb, Items.VIAL_OF_WATER) {
        player.queue {
            UnfPotAction.makeUnfPot(this, unfPot)
        }
    }
}