package gg.rsmod.game.message.decoder

import gg.rsmod.game.message.MessageDecoder
import gg.rsmod.game.message.impl.OpModelMessage

class OpModelDecoder : MessageDecoder<OpModelMessage>() {
    override fun decode(opcode: Int, opcodeIndex: Int, values: HashMap<String, Number>, stringValues: HashMap<String, String>): OpModelMessage {
        val hash = values["hash"]!!.toInt()
        return OpModelMessage(hash)
    }
}