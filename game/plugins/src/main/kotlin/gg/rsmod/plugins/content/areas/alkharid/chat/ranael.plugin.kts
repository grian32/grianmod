package gg.rsmod.plugins.content.areas.alkharid.chat


onNpcOption(Npcs.RANAEL, "Talk-to") {
    player.queue {
        chatNpc("Do you want to buy any armoured skirts? Designed especially for ladies who like to fight.")
        val option = options(
            "Yes please.",
            "No thank you, that's not my scene."
        )

        when (option) {
            1 -> {
                player.openShop("Ranael's Super Skirt Store.")
            }
            2 -> {
                chatPlayer("No thank you, that's not my scene.")
            }
        }
    }
}

onNpcOption(Npcs.RANAEL, "Trade") {
    player.openShop("Ranael's Super Skirt Store.")
}

// TODO: add al kharid flyer interaction