package gg.rsmod.plugins.content.skills.herblore.action

import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.model.entity.AreaSound
import gg.rsmod.game.model.item.Item
import gg.rsmod.game.model.queue.QueueTask
import gg.rsmod.plugins.api.Skills
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.ext.*
import gg.rsmod.plugins.content.skills.herblore.data.UnfPot
import java.awt.geom.Area
import kotlin.math.min


object UnfPotAction {

    const val COMBINING_ANIMATION = 363

    suspend fun makeUnfPot(it: QueueTask, unfPot: UnfPot) {
        val player = it.player
        val inventory = player.inventory

        val herbName = player.world.definitions.get(ItemDef::class.java, unfPot.cleanHerb).name.capitalizeSentence()

        if (player.getSkills().getCurrentLevel(Skills.HERBLORE) < unfPot.requiredLevel) {
            it.messageBox("You need ${unfPot.requiredLevel} Herblore to combine those.")
        }

        var potAmount = 0

        val herbAmount = inventory.getItemCount(unfPot.cleanHerb)
        val vialAmount = inventory.getItemCount(Items.VIAL_OF_WATER)

        if (min(herbAmount, vialAmount) == 1) {
            player.world.spawn(AreaSound(player.tile, 2266, 1, 1))
            player.animate(COMBINING_ANIMATION)
            player.world.spawn(AreaSound(player.tile, 2608, 1, 1))
            player.message("You put the $herbName into the vial of water.")

            inventory[inventory.getItemIndex(unfPot.cleanHerb, true)] = null
            inventory[inventory.getItemIndex(Items.VIAL_OF_WATER, true)] = Item(unfPot.unfPot)
            return
        }

        it.produceItemBox(unfPot.unfPot) { _, amount ->
            potAmount = amount
        }

        repeat(potAmount) { _ ->
            player.world.spawn(AreaSound(player.tile, 2266, 1, 1))
            player.animate(COMBINING_ANIMATION)
            player.world.spawn(AreaSound(player.tile, 2608, 1, 1))
            player.message("You put the $herbName into the vial of water.")

            val herbIndex = inventory.getItemIndex(unfPot.cleanHerb, true)
            val vialIndex = inventory.getItemIndex(Items.VIAL_OF_WATER, true)

            if (herbIndex == -1 || vialIndex == -1) {
                return@repeat
            }

            inventory[herbIndex] = null
            inventory[vialIndex] = Item(unfPot.unfPot)
            it.wait(2)
        }

    }

}