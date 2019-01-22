import gg.rsmod.game.message.impl.SendLogoutMessage
import gg.rsmod.game.model.ACTIVE_COMBAT_TIMER
import gg.rsmod.plugins.osrs.api.helper.player

/**
 * Logout button.
 */
r.bindButton(parent = 182, child = 8) {
    val p = it.player()
    if (!p.timers.has(ACTIVE_COMBAT_TIMER)) {
        p.requestLogout()
        p.write(SendLogoutMessage())
        p.channelClose()
    } else {
        p.message("You can't log out until 10 seconds after the end of combat.")
    }
}