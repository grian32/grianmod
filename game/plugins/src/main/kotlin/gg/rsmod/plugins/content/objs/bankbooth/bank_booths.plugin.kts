package gg.rsmod.plugins.content.objs.bankbooth

import gg.rsmod.plugins.content.inter.bank.openBank

private val BOOTHS = setOf(Objs.BANK_BOOTH, Objs.BANK_BOOTH_10355, Objs.BANK_BOOTH_24101)

BOOTHS.forEach { booth ->
    onObjOption(obj = booth, option = "bank") {
        player.openBank()
    }

    onObjOption(obj = booth, option = "collect") {
        open_collect(player)
    }
}

fun open_collect(p: Player) {
    p.setInterfaceUnderlay(color = -1, transparency = -1)
    p.openInterface(interfaceId = 402, dest = InterfaceDestination.MAIN_SCREEN)
}