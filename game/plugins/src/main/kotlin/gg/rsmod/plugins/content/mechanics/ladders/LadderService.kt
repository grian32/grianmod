package gg.rsmod.plugins.content.mechanics.ladders

import com.google.gson.Gson
import gg.rsmod.game.Server
import gg.rsmod.game.model.World
import gg.rsmod.game.service.Service
import gg.rsmod.plugins.api.ext.appendToString
import gg.rsmod.util.ServerProperties
import mu.KLogging
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author Grian <>
 */
class LadderService : Service {

    lateinit var gates: LaddersJSON

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        val file = Paths.get("./data/cfg/ladders/ladders.json")

        Files.newBufferedReader(file).use { reader ->
            this.gates = Gson().fromJson(reader, LaddersJSON::class.java)
        }

        logger.info { "Loaded ${(gates.climbDown.size + gates.climbUp.size).appendToString("ladders")}." }
    }

    override fun postLoad(server: Server, world: World) {
    }

    override fun bindNet(server: Server, world: World) {
    }

    override fun terminate(server: Server, world: World) {
    }

    companion object : KLogging()
}