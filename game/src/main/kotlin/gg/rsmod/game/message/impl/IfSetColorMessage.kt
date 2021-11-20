package gg.rsmod.game.message.impl

import gg.rsmod.game.message.Message

/**
 * @author grian
 */
data class IfSetColorMessage(val parent: Int, val child: Int, val red: Int, val green: Int, val blue: Int): Message
