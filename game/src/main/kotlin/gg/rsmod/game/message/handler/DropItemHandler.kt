package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.DropItemMessage
import gg.rsmod.game.model.entity.Client
import gg.rsmod.game.model.entity.GroundItem

/**
 * @author Tom <rspsmods@gmail.com>
 */
class DropItemHandler : MessageHandler<DropItemMessage> {

    override fun handle(client: Client, message: DropItemMessage) {
        if (!client.lock.canDropItems()) {
            return
        }

        val hash = message.hash
        val slot = message.slot

        val item = client.inventory[slot] ?: return

        val remove = client.inventory.remove(item, assureFullRemoval = false, beginSlot = slot)
        if (remove.completed > 0) {
            val floor = GroundItem(item.id, remove.completed, client.tile, client.uid)
            client.world.spawn(floor)

            log(client, "Drop item: item=[%d, %d], slot=%d, parent=%d, child=%d", item.id, remove.completed, slot, hash shr 16, hash and 0xFFFF)
        }
    }
}