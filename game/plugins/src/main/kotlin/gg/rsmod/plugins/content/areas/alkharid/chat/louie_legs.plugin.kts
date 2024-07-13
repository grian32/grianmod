package gg.rsmod.plugins.content.areas.alkharid.chat

onNpcOption(Npcs.LOUIE_LEGS, "Talk-to") {
    player.queue {
        chatNpc("Hey, wanna buy some armour?")

        val option = options(
            "What have you got?",
            "No, thank you."
        )

        when (option) {
            1 -> {
                chatPlayer("What have you got?")
                chatNpc("I provide items to help you keep your legs!")
                player.openShop("Louie's Armoured Legs Bazaar.")
            }
            2 -> {
                chatPlayer("No, thank you.")
            }
        }
    }
}


onNpcOption(Npcs.LOUIE_LEGS, "Trade") {
    player.openShop("Louie's Armoured Legs Bazaar.")
}


// TODO: add al kharid flyer interaction