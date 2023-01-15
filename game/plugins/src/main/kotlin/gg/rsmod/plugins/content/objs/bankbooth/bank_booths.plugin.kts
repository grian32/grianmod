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

    onItemOnObj(obj = booth, item = Items.COINS_995) {
        player.queue {
            val option = options(
                    "Yes",
                    "No",
                    title = "<col=800000>Exchange your coins for platinum tokens?</col>"
            )

            when (option) {
                1 -> {
                    val platinumTokenAmount = player.inventory.getItemCount(Items.COINS_995) / 1000

                    // we multiply the plat token amount by 1000 to leave the remainder of coins that cannot be
                    // converted
                    val removal = player.inventory.remove(Items.COINS_995, platinumTokenAmount * 1000, true)

                    if (removal.hasSucceeded()) {
                        player.inventory.add(Items.PLATINUM_TOKEN, platinumTokenAmount, true)
                    }

                    doubleItemMessageBox(
                            "The bank exchanges your coins for platinum tokens.",
                            Items.COINS_995,
                            Items.PLATINUM_TOKEN,
                            100,
                            100
                    )
                }
                2 -> return@queue
                else -> println("Something went really wrong with Coins -> Plat. Tokens")
            }
        }
    }

    onItemOnObj(obj = booth, item = Items.PLATINUM_TOKEN) {
        player.queue {
            val option = options(
                    "Yes",
                    "No",
                    title = "<col=800000>Exchange your platinum tokens for coins?</col>"
            )

            when (option) {
                1 -> {
                    val coinsAmount = player.inventory.getItemCount(Items.PLATINUM_TOKEN) * 1000

                    val removal = player.inventory.remove(Items.PLATINUM_TOKEN, coinsAmount / 1000, true)

                    if (removal.hasSucceeded()) {
                        player.inventory.add(Items.COINS_995, coinsAmount, true)
                    }

                    doubleItemMessageBox(
                            "The bank exchanges your platinum tokens for coins.",
                            Items.COINS_995,
                            Items.PLATINUM_TOKEN,
                            100,
                            100
                    )
                }
                2 -> return@queue
                else -> println("Something went really wrong with Plat. Tokens -> Coins")
            }
        }
    }
}

fun open_collect(p: Player) {
    p.setInterfaceUnderlay(color = -1, transparency = -1)
    p.openInterface(interfaceId = 402, dest = InterfaceDestination.MAIN_SCREEN)
}