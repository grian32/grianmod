package gg.rsmod.plugins.content.skills.herblore.action

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.ChatMessageType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.herblore.data.Herb

object CleaningAction {

    suspend fun cleanHerb(it: QueueTask, herb: Herb) {
        val player = it.player
        val inventory = player.inventory

        val grimyName = player.world.definitions.get(ItemDef::class.java, herb.grimyHerb).name

        if (player.getSkills().getCurrentLevel(Skills.HERBLORE) < herb.requiredLevel) {
            player.message(
                "You need level ${herb.requiredLevel} herblore to clean the $grimyName.",
                ChatMessageType.GAME_MESSAGE
            )
            return
        }

        inventory[player.getInteractingItemSlot()] = Item(herb.cleanHerb)
        player.message("You clean the $grimyName.", ChatMessageType.SPAM)
        player.playSound(3923)
        player.addXp(Skills.HERBLORE, herb.xpGained)

        val amount = inventory.getItemCount(herb.grimyHerb)

        repeat(amount) { index ->
            if (index == 0) {
                // since it cleans the clicked herb first, waits 2 to not do the same action twice in 1 tick
                it.wait(3)
            }

            val itemIndex = inventory.getItemIndex(herb.grimyHerb, true)

            if (itemIndex == -1) {
                return
            }

            inventory[itemIndex] = Item(herb.cleanHerb)
            player.message("You clean the $grimyName.", ChatMessageType.SPAM)
            player.playSound(3923)
            player.addXp(Skills.HERBLORE, herb.xpGained)
            it.wait(2)
        }
    }
}