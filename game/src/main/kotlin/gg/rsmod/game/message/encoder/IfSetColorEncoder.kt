package gg.rsmod.game.message.encoder

import gg.rsmod.game.message.MessageEncoder
import gg.rsmod.game.message.impl.IfSetColorMessage

class IfSetColorEncoder: MessageEncoder<IfSetColorMessage>() {
    override fun extract(message: IfSetColorMessage, key: String): Number {
        return when (key) {
            "hash" -> (message.parent shl 16) or message.child
            "color" -> (message.red shl 10) or (message.green shl 5) or message.blue
            else -> throw Exception("Unhandled value key.")
        }
    }

    override fun extractBytes(message: IfSetColorMessage, key: String): ByteArray = throw Exception("Unhandled value key.")
}