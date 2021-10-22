package gg.rsmod.plugins.content.items.necklaceofpassage

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

load_metadata {
    author = "Grian"
    description = "Implements the Necklace of Passage"
    name = "Necklace of Passage"
}

val NECKLACES = listOf(
    Items.NECKLACE_OF_PASSAGE1,
    Items.NECKLACE_OF_PASSAGE2,
    Items.NECKLACE_OF_PASSAGE3,
    Items.NECKLACE_OF_PASSAGE4,
    Items.NECKLACE_OF_PASSAGE5
)

private val LOCATIONS = mapOf(
    "Wizards' Tower" to Tile(3113, 3180),
    "The Outpost" to Tile(2428, 3350),
    "Eagles' Eyrie" to Tile(3405, 3158)
)

NECKLACES.forEach { necklace ->
    LOCATIONS.forEach { (name, tile) ->
        on_equipment_option(necklace, name) {
            world.spawn(AreaSound(player.tile, 200, 5, 1))
            player.teleport(tile, TeleportType.MODERN)
            when (player.equipment[2]?.id) {
                Items.NECKLACE_OF_PASSAGE5 -> player.equipment[2] = Item(Items.NECKLACE_OF_PASSAGE4, 1)
                Items.NECKLACE_OF_PASSAGE4 -> player.equipment[2] = Item(Items.NECKLACE_OF_PASSAGE3, 1)
                Items.NECKLACE_OF_PASSAGE3 -> player.equipment[2] = Item(Items.NECKLACE_OF_PASSAGE2, 1)
                Items.NECKLACE_OF_PASSAGE2 -> player.equipment[2] = Item(Items.NECKLACE_OF_PASSAGE1, 1)
                Items.NECKLACE_OF_PASSAGE1 -> player.equipment[2] = null
                else -> println("Something went really wrong with Necklaces Of Passage! D:")
            }
        }
    }

    on_item_option(necklace, "rub") {
        player.queue {
            val option = options(
                "Wizards' Tower",
                "The Outpost",
                "Eagle's Eyrie",
                "Cancel",
                title = "<col=800000>Teleport to...</col>"
            )

            when (option) {
                /**
                 * TODO: figure out a way to see which item in inventory got rubbed, and degrade it from there
                 * Currently doesn't degrade because of this.
                 */
                // TODO: abstract this into a function once code is finalized
                // was getting @ me over nulls so i just hardcoded the tile
                1 -> {
                    player.teleport(Tile(3113, 3180), TeleportType.MODERN)
                    world.spawn(AreaSound(player.tile, 200, 5, 1))
                }
                2 -> {
                    player.teleport(Tile(2428, 3350), TeleportType.MODERN)
                    world.spawn(AreaSound(player.tile, 200, 5, 1))
                }
                3 -> {
                    player.teleport(Tile(3405, 3158), TeleportType.MODERN)
                    world.spawn(AreaSound(player.tile, 200, 5, 1))
                }
                4 -> return@queue
                else -> println("Something went really wrong with Necklaces Of Passage! D:")
           }
        }
    }
}
