package gg.rsmod.plugins.content.skills.prayer.burying

val BURYING_ANIMATION = 827

BoneBury.values().forEach {
    onItemOption(it.item, "Bury") {
        if (player.getSkills().getMaxLevel(Skills.PRAYER) < it.levelToBury) {
            player.message(
                "You need level ${it.levelToBury} prayer to bury this bone.",
                ChatMessageType.GAME_MESSAGE
            )
            return@onItemOption
        }

        player.inventory.remove(item = it.item, amount = 1, beginSlot = player.getInteractingItemSlot())
        player.queue {
            animateBlocking(BURYING_ANIMATION)
        }
        player.addXp(Skills.PRAYER, it.xpGained)
    }
}