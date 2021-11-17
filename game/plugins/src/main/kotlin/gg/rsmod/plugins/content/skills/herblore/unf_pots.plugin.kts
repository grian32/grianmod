package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.plugins.content.skills.herblore.action.UnfPotAction
import gg.rsmod.plugins.content.skills.herblore.data.UnfPot

private val unfPots = listOf(
    UnfPot(Items.GUAM_LEAF, Items.GUAM_POTION_UNF, 3, "Guam Leaf"),
    UnfPot(Items.MARRENTILL, Items.MARRENTILL_POTION_UNF, 5, "Marrentill"),
    UnfPot(Items.TARROMIN, Items.TARROMIN_POTION_UNF, 12, "Tarromin"),
    UnfPot(Items.HARRALANDER_POTION_UNF, Items.HARRALANDER_POTION_UNF, 22, "Harralander"),
    UnfPot(Items.RANARR_WEED, Items.RANARR_POTION_UNF, 30, "Ranarr Weed"),
    UnfPot(Items.TOADFLAX, Items.TOADFLAX_POTION_UNF, 34, "Toadflax"),
    UnfPot(Items.IRIT_LEAF, Items.IRIT_POTION_UNF, 45, "Irit Leaf"),
    UnfPot(Items.AVANTOE, Items.AVANTOE_POTION_UNF, 50, "Avantoe"),
    UnfPot(Items.KWUARM, Items.KWUARM_POTION_UNF, 55, "Kwuarm"),
    UnfPot(Items.SNAPDRAGON, Items.SNAPDRAGON_POTION_UNF, 63, "Snapdragon"),
    UnfPot(Items.CADANTINE, Items.CADANTINE_POTION_UNF, 66, "Cadantine"),
    UnfPot(Items.LANTADYME, Items.LANTADYME_POTION_UNF, 69, "Lantadyme"),
    UnfPot(Items.DWARF_WEED, Items.DWARF_WEED_POTION_UNF, 72, "Dwarf Weed"),
    UnfPot(Items.TORSTOL, Items.TORSTOL_POTION_UNF, 78, "Torstol")
)

unfPots.forEach { unfPot ->
    on_item_on_item(unfPot.cleanHerb, Items.VIAL_OF_WATER) {
        player.queue {
            UnfPotAction.makeUnfPot(this, unfPot)
        }
    }
}