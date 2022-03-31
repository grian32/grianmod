package gg.rsmod.plugins.content.areas.runeessencemine

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

onObjOption(Objs.PORTAL_34774, "use") {
    player.teleport(Tile(3107, 9572), TeleportType.RUNE_ESSENCE_MINE)
}