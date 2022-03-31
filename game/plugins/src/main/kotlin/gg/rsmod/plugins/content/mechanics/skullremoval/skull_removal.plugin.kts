package gg.rsmod.plugins.content.mechanics.skullremoval

import gg.rsmod.game.model.timer.SKULL_ICON_DURATION_TIMER

onTimer(SKULL_ICON_DURATION_TIMER) {
    if (!player.hasSkullIcon(SkullIcon.NONE)) {
        player.setSkullIcon(SkullIcon.NONE)
    }
}