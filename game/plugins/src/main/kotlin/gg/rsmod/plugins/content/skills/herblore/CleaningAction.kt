package gg.rsmod.plugins.content.skills.herblore

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.ChatMessageType
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.ext.getInteractingItemSlot
import gg.rsmod.plugins.api.ext.message
import gg.rsmod.plugins.api.ext.playSound
import gg.rsmod.plugins.api.ext.player

object CleaningAction {

    suspend fun cleanHerb(it: QueueTask, herb: Herb) {
        val player = it.player

        if (player.getSkills().getCurrentLevel(Skills.HERBLORE) < herb.requiredLevel) {
            // TODO: check what the actual message is on rs
            player.message("You do not have a high enough herblore level to clean ${herb.grimyName}s")
            return
        }

        player.inventory[player.getInteractingItemSlot()] = Item(herb.cleanHerb)
        player.message("You clean the ${herb.grimyName}", ChatMessageType.SPAM)
        player.playSound(3923)
        player.addXp(Skills.HERBLORE, herb.xpGained)

        val amount = player.inventory.getItemCount(herb.grimyHerb)

        repeat(amount) { index ->
            if (index == 0) {
                // since it cleans the clicked herb first, waits 2 to not do the same action twice in 1 tick
                it.wait(3)
            }

            val itemIndex = player.inventory.getItemIndex(herb.grimyHerb, true)

            if (itemIndex == -1) {
                return
            }

            player.inventory[itemIndex] = Item(herb.cleanHerb)
            player.message("You clean the ${herb.grimyName}", ChatMessageType.SPAM)
            player.playSound(3923)
            player.addXp(Skills.HERBLORE, herb.xpGained)
            it.wait(2)
        }

    }
}