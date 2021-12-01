package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpModelMessage
import gg.rsmod.game.model.World
import gg.rsmod.game.model.entity.Client

class OpModelHandler : MessageHandler<OpModelMessage> {

    override fun handle(client: Client, world: World, message: OpModelMessage) {
        val interfaceId = message.hash shr 16
        val component = message.hash and 0xFFFF

        if (!client.interfaces.isVisible(interfaceId)) {
            return
        }

        log(client, "OpModel: component=[%d:%d]", interfaceId, component)

        if (world.plugins.executeOpModel(client, interfaceId, component)) {
            return
        }

        if (world.devContext.debugButtons) {
            client.writeMessage("Unhandled OpModel: component=[$interfaceId:$component]")
        }
    }
}