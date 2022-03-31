package gg.rsmod.plugins.content.inter.attack

import gg.rsmod.game.model.attr.NEW_ACCOUNT_ATTR

/**
 * First log-in logic (when accounts have just been made).
 */
onLogin {
    if (player.attr.getOrDefault(NEW_ACCOUNT_ATTR, false)) {
        AttackTab.setEnergy(player, 100)
    }
}

/**
 * Attack style buttons
 */
onButton(interfaceId = 593, component = 3) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 0)
}

onButton(interfaceId = 593, component = 7) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 1)
}

onButton(interfaceId = 593, component = 11) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 2)
}

onButton(interfaceId = 593, component = 15) {
    player.setVarp(AttackTab.ATTACK_STYLE_VARP, 3)
}

/**
 * Toggle auto-retaliate button.
 */
onButton(interfaceId = 593, component = 29) {
    player.toggleVarp(AttackTab.DISABLE_AUTO_RETALIATE_VARP)
}

/**
 * Toggle special attack.
 */
onButton(interfaceId = 593, component = 35) {
    player.toggleVarp(AttackTab.SPECIAL_ATTACK_VARP)
}

/**
 * Disable special attack when switching weapons.
 */
onEquipToSlot(EquipmentType.WEAPON.id) {
    player.setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}

/**
 * Disable special attack on log-out.
 */
onLogout {
    player.setVarp(AttackTab.SPECIAL_ATTACK_VARP, 0)
}