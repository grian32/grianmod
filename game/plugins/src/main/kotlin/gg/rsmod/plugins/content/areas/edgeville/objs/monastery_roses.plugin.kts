package gg.rsmod.plugins.content.areas.edgeville.objs

for (roses in 9260..9262) {
    onObjOption(obj = roses, option = "take-seed") {
        player.message("There doesn't seem to be any seeds on this rosebush.")
    }
}