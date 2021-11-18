package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.plugins.content.skills.herblore.action.CleaningAction
import gg.rsmod.plugins.content.skills.herblore.data.Herb

private val HERBS = listOf(
    Herb(Items.GRIMY_GUAM_LEAF, Items.GUAM_LEAF, 2.5, 3),
    Herb(Items.GRIMY_MARRENTILL, Items.MARRENTILL, 3.8, 5),
    Herb(Items.GRIMY_TARROMIN, Items.TARROMIN, 5.0, 11),
    Herb(Items.GRIMY_HARRALANDER, Items.HARRALANDER, 6.3, 20),
    Herb(Items.GRIMY_RANARR_WEED, Items.RANARR_WEED, 7.5, 25),
    Herb(Items.GRIMY_TOADFLAX, Items.TOADFLAX, 8.0, 30),
    Herb(Items.GRIMY_IRIT_LEAF, Items.IRIT_LEAF, 8.8, 40),
    Herb(Items.GRIMY_AVANTOE, Items.AVANTOE, 10.0, 48),
    Herb(Items.GRIMY_KWUARM, Items.KWUARM, 11.3, 54),
    Herb(Items.GRIMY_SNAPDRAGON, Items.SNAPDRAGON, 11.8, 59),
    Herb(Items.GRIMY_CADANTINE, Items.CADANTINE, 12.5, 65),
    Herb(Items.GRIMY_LANTADYME, Items.LANTADYME, 13.1, 67),
    Herb(Items.GRIMY_DWARF_WEED, Items.DWARF_WEED, 13.8, 70),
    Herb(Items.GRIMY_TORSTOL, Items.TORSTOL, 15.0, 75)
)


HERBS.forEach { herb ->
    on_item_option(herb.grimyHerb, "clean") {
        player.queue {
            CleaningAction.cleanHerb(this, herb)
        }
    }
}