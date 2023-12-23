package gg.rsmod.plugins.content.areas.edgeville.chat

arrayOf(Npcs.SHOP_KEEPER_2821, Npcs.SHOP_ASSISTANT_2822).forEach { shop ->
    onNpcOption(npc = shop, option = "talk-to") {
        player.queue { dialog(this) }
    }

    onNpcOption(npc = shop, option = "trade") {
        openShop(player)
    }
}

suspend fun dialog(it: QueueTask) {
    it.chatNpc("Can I help you at all?", animation = 567)
    when (it.options("Yes please. What are you selling?", "No thanks.")) {
        1 -> openShop(it.player)
        2 -> it.chatPlayer("No thanks.", animation = 588)
    }
}

fun openShop(p: Player) {
    p.openShop("Edgeville General Store")
}