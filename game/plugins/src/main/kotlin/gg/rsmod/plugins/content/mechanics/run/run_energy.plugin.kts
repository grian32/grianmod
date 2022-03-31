package gg.rsmod.plugins.content.mechanics.run

onLogin {
    player.timers[RunEnergy.RUN_DRAIN] = 1
}

onTimer(RunEnergy.RUN_DRAIN) {
    player.timers[RunEnergy.RUN_DRAIN] = 1
    RunEnergy.drain(player)
}

/**
 * Button by minimap.
 */
onButton(interfaceId = 160, component = 22) {
    RunEnergy.toggle(player)
}

/**
 * Settings button.
 */
onButton(interfaceId = 261, component = 95) {
    RunEnergy.toggle(player)
}