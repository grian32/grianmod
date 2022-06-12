package gg.rsmod.plugins.content.skills.agility.courses.gnomeagilitycourse

val logAnims = intArrayOf(-1, 762, 762, 762, 763, 762, 762)
val tightropeAnims = intArrayOf(-1, 762, 762, 762, 762, 763, 762)
var courseStage: GnomeCourseStage = GnomeCourseStage.LOG_BALANCE

onObjOption(Objs.LOG_BALANCE_23145, "walk-across") {
    player.walkLogBalance()
}

onObjOption(Objs.OBSTACLE_NET_23134, "climb-over") {
    player.climbNet()
    // FIXME: you can click this from the side and itll move up a plane oob lol
}

onObjOption(Objs.TREE_BRANCH_23559, "climb") {
    player.climbTreeBranch()
}

onObjOption(Objs.BALANCING_ROPE_23557, "walk-on") {
    player.walkTightrope()
}

listOf(Objs.TREE_BRANCH_23560, Objs.TREE_BRANCH_23561).forEach {
    onObjOption(it, "climb-down") {
        player.climbDownTreeBranch()
    }
}

onObjOption(Objs.OBSTACLE_NET_23135, "climb-over") {
    player.climbSecondNet()
    // FIXME: you can click this from behind and itll move u forward 2 tiles lol
}

// 23138; right one is 23139
listOf(Objs.OBSTACLE_PIPE_23138, Objs.OBSTACLE_PIPE_23139).forEach {
    onObjOption(it, "squeeze-through") {
        player.squeezeThroughPipe()
    }
}

fun Player.walkLogBalance() = queue {
    spam("You walk carefully across the slippery log...")

    withAnimationSet(logAnims) {
        lock()
        walkToBlocking(Tile(2474, 3429), MovementQueue.StepType.FORCED_WALK, false)
        unlock()
    }

    spam("...You make it safely to the other side.")

    addXp(Skills.AGILITY, 7.5)

    courseStage = GnomeCourseStage.LOG_BALANCE
}

fun Player.climbNet() = queue {
    // TODO: figure out how to make this nicer looking lol
    val endTile: Tile
    if (
        player.tile == Tile(2474, 3426, 0) ||
        player.tile == Tile(2472, 3426, 0) ||
        player.tile == Tile(2476, 3426, 0)
    ) {
        endTile = player.tile.transform(1, -3, 1)
    } else {
        endTile = player.tile.transform(0, -3, 1)
    }
    // middle 2474, 3426, 0 right & 2473. 3426, 0
    // right 2472, 3426, 0 right & 2471, 3426, 0
    // left 2476, 3426, 0 right & 2475, 3426, 0
    spam("You climb the netting...")
    animateBlocking(828)
    player.moveTo(endTile)

    addXp(Skills.AGILITY, 7.5)

    if (courseStage == GnomeCourseStage.LOG_BALANCE) {
        courseStage = GnomeCourseStage.NET
    }
}

// net - id 23134 - climb over
// end tile is always on the left side of the obstacle, up once and one over https://imgur.com/yWAtDVB via tp
// spam msg "You climb the netting..."
// anim 828
// add xp 7.5

fun Player.climbTreeBranch() = queue {
    spam("You climb the tree...")
    animateBlocking(828)
    spam("...To the platform above.")
    moveTo(Tile(2473, 3420, 2))

    addXp(Skills.AGILITY, 5.0)

    if (courseStage == GnomeCourseStage.NET) {
        courseStage = GnomeCourseStage.TREE_BRANCH
    }
}

// tree branch - id 23559 - option climb
// spam msg "You climb the tree..."
// anim id 828
// 1 tick l8r
// spam msg "...To the platform above."
// tp to 2473, 3420, 2
// add xp 5

fun Player.walkTightrope() = queue {
    spam("You carefully cross the tightrope.")

    withAnimationSet(tightropeAnims) {
        lock()
        walkToBlocking(Tile(2483, 3420, 2), MovementQueue.StepType.FORCED_WALK, false)
        unlock()
    }

    addXp(Skills.AGILITY, 7.5)

    if (courseStage == GnomeCourseStage.TREE_BRANCH) {
        courseStage = GnomeCourseStage.TIGHTROPE
    }
}

// balancing rope - id 23557 - option walk-on
// spam msg "You carefully cross the tightrope."
// Appearance(runAnim = -1, walkForwardAnim = 762, walkBackwardsAnim = 762, walkLeftAnim = 762, walkRightAnim = 762, standAnim = 763, turnOnSpotAnim = 762)
// sound effect name tightrope rep 5
// wait 5-6 ticks
// end tile 2483, 3420, 2
// agil xp 7.5

fun Player.climbDownTreeBranch() = queue {
    spam("You climb down the tree...")
    animateBlocking(828)
    spam("You land on the ground.")
    moveTo(Tile(2487, 3420, 0))

    addXp(Skills.AGILITY, 5.0)

    if (courseStage == GnomeCourseStage.TIGHTROPE) {
        courseStage = GnomeCourseStage.SECOND_TREE_BRANCH
    }
}

// 2nd tree branch - id 23560/23561(middle of the tree lol) - option climb-down
// spam msg "You climb down the tree..."
// anim id 828
// 1 tick l8r
// spam msg "You land on the ground."...
// 5 agil xp
// tp to 2487 3420 0

fun Player.climbSecondNet() = queue {
    // TODO: figure out how to combine this, atleast somewhat, with the first net function
    val endTile: Tile
    if (
        player.tile == Tile(2483, 3425, 0) ||
        player.tile == Tile(2485, 3425, 0) ||
        player.tile == Tile(2487, 3425, 0)
    ) {
        endTile = player.tile.transform(1, 2, 0)
    } else {
        endTile = player.tile.transform(0, 2, 0)
    }

    // middle 2483, 3425, 0 right
    // right 2485, 3425, 0 right
    // left 2487, 3425, 0 right
    spam("You climb the netting...")
    animateBlocking(828)
    player.moveTo(endTile, true)

    addXp(Skills.AGILITY, 7.5)

    if (courseStage == GnomeCourseStage.SECOND_TREE_BRANCH) {
        courseStage = GnomeCourseStage.SECOND_NET
    }
}

// 2nd obstacle net - id 23135 - option climb-over
// end tile is always on the right side of the obstacle, one tile over from the obstacle itself
// spam msg "You climb the netting..."
// anims 828
// 1 tick l8r
// add xp 7.5

fun Player.squeezeThroughPipe() = queue {
    // doesnt work properly, force moves are weird
    forceMove(ForcedMovement.of(tile.transform(0, 3, 0), tile, tile.transform(0, 3, 0), 30, 96, 1024))
    animateBlocking(749, 30)
    playSound(Sounds.SQUEEZE_IN)
    moveTo(tile.transform(0, 3, 0))

    forceMove(ForcedMovement.of(tile.transform(0, 4, 0), tile, tile, 30, 68, 1024))
    animateBlocking(749, 30)
    moveTo(tile.transform(0, 4, 0))
    playSound(Sounds.SQUEEZE_IN)

    addXp(Skills.AGILITY, 7.5)

    if (courseStage == GnomeCourseStage.SECOND_NET) {
        addXp(Skills.AGILITY, 39.0)
    }
}


// obstacle pipe - left one is 23138; right one is 23139 - option squeeze-through
// https://imgur.com/GOc6ZBA