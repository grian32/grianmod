package gg.rsmod.game.plugin

import com.google.gson.GsonBuilder
import gg.rsmod.game.Server
import gg.rsmod.game.event.Event
import gg.rsmod.game.fs.def.ItemDef
import gg.rsmod.game.fs.def.NpcDef
import gg.rsmod.game.fs.def.ObjectDef
import gg.rsmod.game.model.Direction
import gg.rsmod.game.model.Tile
import gg.rsmod.game.model.World
import gg.rsmod.game.model.combat.NpcCombatDef
import gg.rsmod.game.model.container.key.ContainerKey
import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.GroundItem
import gg.rsmod.game.model.entity.Npc
import gg.rsmod.game.model.shop.PurchasePolicy
import gg.rsmod.game.model.shop.Shop
import gg.rsmod.game.model.shop.ShopCurrency
import gg.rsmod.game.model.shop.StockType
import gg.rsmod.game.model.timer.TimerKey
import gg.rsmod.game.service.Service
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.script.experimental.annotations.KotlinScript

/**
 * Represents a KotlinScript plugin.
 *
 * @author Tom <rspsmods@gmail.com>
 */
@KotlinScript(
        displayName = "Kotlin Plugin",
        fileExtension = "plugin.kts",
        compilationConfiguration = KotlinPluginConfiguration::class
)
abstract class KotlinPlugin(private val r: PluginRepository, val world: World, val server: Server) {

    /**
     * A map of properties that will be copied from the [PluginMetadata] and
     * exposed to the plugin.
     */
    private lateinit var properties: MutableMap<String, Any>

    /**
     * Get property associated with [key] casted as [T].
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getProperty(key: String): T? = properties[key] as? T?

    /**
     * Set the [PluginMetadata] for this plugin.
     */
    fun _loadMetadata(metadata: PluginMetadata) {
        checkNotNull(metadata.propertyFileName) { "Property file name must be set in order to load metadata." }

        val file = METADATA_PATH.resolve("${metadata.propertyFileName}.json")
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create()

        if (!Files.exists(file)) {
            Files.createDirectories(METADATA_PATH)
            Files.newBufferedWriter(file).use { writer ->
                gson.toJson(metadata, PluginMetadata::class.java, writer)
            }
        }

        Files.newBufferedReader(file).use { reader ->
            val data = gson.fromJson(reader, PluginMetadata::class.java)
            if (data.properties.isNotEmpty()) {
                properties = mutableMapOf()
                data.properties.forEach { key, value ->
                    properties[key] = if (value is Double) value.toInt() else value
                }
            }
        }
    }

    /**
     * Load [service] on plugin start-up.
     */
    fun loadService(service: Service) {
        r.services.add(service)
    }

    /**
     * Set the [gg.rsmod.game.model.region.ChunkCoords] with [chunk] as its
     * [gg.rsmod.game.model.region.ChunkCoords.hashCode], as a multi-combat area.
     */
    fun setMultiCombatChunk(chunk: Int) {
        r.multiCombatChunks.add(chunk)
    }

    /**
     * Set the 8x8 [gg.rsmod.game.model.region.ChunkCoords]s that belong to [region]
     * as multi-combat areas.
     */
    fun setMultiCombatRegion(region: Int) {
        r.multiCombatRegions.add(region)
    }

    /**
     * Set the [NpcCombatDef] for npcs with [Npc.id] of [npc].
     */
    fun setCombatDef(npc: Int, def: NpcCombatDef) {
        check(!r.npcCombatDefs.containsKey(npc)) { "Npc combat definition has been previously set: $npc" }
        r.npcCombatDefs[npc] = def
    }

    /**
     * Set the [NpcCombatDef] for npcs with [Npc.id] of [npc] and [others].
     */
    fun setCombatDef(npc: Int, vararg others: Int, def: NpcCombatDef) {
        setCombatDef(npc, def)
        others.forEach { other -> setCombatDef(other, def) }
    }

    /**
     * Create a [Shop] in our world.
     */
    fun createShop(name: String, currency: ShopCurrency, stockType: StockType = StockType.NORMAL,
                   stockSize: Int = Shop.DEFAULT_STOCK_SIZE, purchasePolicy: PurchasePolicy = PurchasePolicy.BUY_TRADEABLES,
                   init: Shop.() -> Unit) {
        val shop = Shop(name, stockType, purchasePolicy, currency, arrayOfNulls(stockSize))
        r.shops[name] = shop
        init(shop)
    }

    /**
     * Create a [ContainerKey] to register to the [World] for serialization
     * later on.
     */
    fun registerContainerKey(key: ContainerKey) {
        r.containerKeys.add(key)
    }

    /**
     * Spawn an [Npc] on the given coordinates.
     */
    fun spawnNpc(npc: Int, x: Int, z: Int, height: Int = 0, walkRadius: Int = 0, direction: Direction = Direction.SOUTH) {
        val n = Npc(npc, Tile(x, z, height), world)
        n.respawns = true
        n.walkRadius = walkRadius
        n.lastFacingDirection = direction
        r.npcSpawns.add(n)
    }

    /**
     * Spawn a [DynamicObject] on the given coordinates.
     */
    fun spawnObj(obj: Int, x: Int, z: Int, height: Int = 0, type: Int = 10, rot: Int = 0) {
        val o = DynamicObject(obj, type, rot, Tile(x, z, height))
        r.objSpawns.add(o)
    }

    /**
     * Spawn a [GroundItem] on the given coordinates.
     */
    fun spawnItem(item: Int, amount: Int, x: Int, z: Int, height: Int = 0, respawnCycles: Int = GroundItem.DEFAULT_RESPAWN_CYCLES) {
        val ground = GroundItem(item, amount, Tile(x, z, height))
        ground.respawnCycles = respawnCycles
        r.itemSpawns.add(ground)
    }

    /**
     * Invoke [logic] when the [option] option is clicked on an inventory
     * [gg.rsmod.game.model.item.Item].
     *
     * This method should be used over the option-int variant whenever possible.
     */
    fun onItemOption(item: Int, option: String, logic: (Plugin).() -> Unit) {
        val opt = option.toLowerCase()
        val def = world.definitions.get(ItemDef::class.java, item)
        val slot = def.inventoryMenu.indexOfFirst { it?.toLowerCase() == opt }

        check(slot != -1) { "Option \"$option\" not found for item $item [options=${def.inventoryMenu.filterNotNull().filter { it.isNotBlank() }}]" }

        r.bindItem(item, slot + 1, logic)
    }

    /**
     * Invoke [logic] when the [option] option is clicked on an equipment
     * [gg.rsmod.game.model.item.Item].
     */
    fun onEquipmentOption(item: Int, option: String, logic: (Plugin).() -> Unit) {
        val opt = option.toLowerCase()
        val def = world.definitions.get(ItemDef::class.java, item)
        val slot = def.equipmentMenu.indexOfFirst { it?.toLowerCase() == opt }

        check(slot != -1) { "Option \"$option\" not found for item equipment $item [options=${def.equipmentMenu.filterNotNull().filter { it.isNotBlank() }}]" }

        r.bindEquipmentOption(item, slot + 1, logic)
    }

    /**
     * Invoke [logic] when the [option] option is clicked on a
     * [gg.rsmod.game.model.entity.GameObject].
     *
     * This method should be used over the option-int variant whenever possible.
     */
    fun onObjOption(obj: Int, option: String, lineOfSightDistance: Int = -1, logic: (Plugin).() -> Unit) {
        val opt = option.toLowerCase()
        val def = world.definitions.get(ObjectDef::class.java, obj)
        val slot = def.options.indexOfFirst { it?.toLowerCase() == opt }

        check(slot != -1) { "Option \"$option\" not found for object $obj [options=${def.options.filterNotNull().filter { it.isNotBlank() }}]" }

        r.bindObject(obj, slot + 1, lineOfSightDistance, logic)
    }

    /**
     * Invoke [logic] when the [option] option is clicked on an [Npc].
     *
     * This method should be used over the option-int variant whenever possible.
     *
     * @param lineOfSightDistance
     * If the npc is behind an object such as a prison cell or bank booth, this
     * distance should be set. If the npc can be reached normally, you shouldn't
     * specify this value.
     */
    fun onNpcOption(npc: Int, option: String, lineOfSightDistance: Int = -1, logic: (Plugin).() -> Unit) {
        val opt = option.toLowerCase()
        val def = world.definitions.get(NpcDef::class.java, npc)
        val slot = def.options.indexOfFirst { it?.toLowerCase() == opt }

        check(slot != -1) { "Option \"$option\" not found for npc $npc [options=${def.options.filterNotNull().filter { it.isNotBlank() }}]" }

        r.bindNpc(npc, slot + 1, lineOfSightDistance, logic)
    }

    /**
     * Invoke [logic] when [option] option is clicked on a [GroundItem].
     *
     * This method should be used over the option-int variant whenever possible.
     */
    fun onGroundItemOption(item: Int, option: String, logic: (Plugin).() -> Unit) {
        val opt = option.toLowerCase()
        val def = world.definitions.get(ItemDef::class.java, item)
        val slot = def.groundMenu.indexOfFirst { it?.toLowerCase() == opt }

        check(slot != -1) { "Option \"$option\" not found for ground item $item [options=${def.groundMenu.filterNotNull().filter { it.isNotBlank() }}]" }

        r.bindGroundItem(item, slot + 1, logic)
    }

    /**
     * Invoke [logic] when an [item] is used on a [gg.rsmod.game.model.entity.GameObject]
     *
     * @param obj the game object id
     * @param item the item id
     */
    fun onItemOnObj(obj: Int, item: Int, lineOfSightDistance: Int = -1, logic: (Plugin).() -> Unit) {
        r.bindItemOnObject(obj, item, lineOfSightDistance, logic)
    }

    /**
     * Invoke [plugin] when [item1] is used on [item2] or vise-versa.
     */
    fun onItemOnItem(item1: Int, item2: Int, plugin: Plugin.() -> Unit) = r.bindItemOnItem(item1, item2, plugin)

    /**
     * Invoke [plugin] when [item] in inventory is used on [groundItem] on ground.
     */
    fun onItemOnGroundItem(item: Int, groundItem: Int, plugin: Plugin.() -> Unit) = r.bindItemOnGroundItem(item, groundItem, plugin)

    /**
     * Set the logic to execute when [gg.rsmod.game.message.impl.WindowStatusMessage]
     * is handled.
     */
    fun setWindowStatusLogic(logic: (Plugin).() -> Unit) = r.bindWindowStatus(logic)

    /**
     * Set the logic to execute when [gg.rsmod.game.message.impl.CloseModalMessage]
     * is handled.
     */
    fun setModalCloseLogic(logic: (Plugin).() -> Unit) = r.bindModalClose(logic)

    /**
     * Set the logic to check if a player has a menu opened and any [gg.rsmod.game.model.queue.QueueTask]
     * with a [gg.rsmod.game.model.queue.TaskPriority.STANDARD] priority should wait before executing.
     *
     * @see PluginRepository.isMenuOpenedPlugin
     *
     * @return
     * True if the player has a menu opened and any standard task should wait
     * before executing.
     */
    fun setMenuOpenCheck(logic: Plugin.() -> Boolean) = r.setMenuOpenedCheck(logic)

    /**
     * Set the logic to execute by default when [gg.rsmod.game.model.entity.Pawn.attack]
     * is handled.
     */
    fun setCombatLogic(logic: (Plugin).() -> Unit) = r.bindCombat(logic)

    /**
     * Set the logic to execute when a player levels a skill.
     */
    fun setLevelUpLogic(logic: (Plugin).() -> Unit) = r.bindSkillLevelUp(logic)

    /**
     * Invoke [logic] when [World.postLoad] is handled.
     */
    fun onWorldInit(logic: (Plugin).() -> Unit) = r.bindWorldInit(logic)

    /**
     * Invoke [logic] when an [Event] is triggered.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Event> onEvent(event: Class<out T>, logic: Plugin.(T) -> Unit) = r.bindEvent(event, logic as Plugin.(Event) -> Unit)

    /**
     * Invoke [logic] on player log in.
     */
    fun onLogin(logic: (Plugin).() -> Unit) = r.bindLogin(logic)

    /**
     * Invoke [logic] on player log out.
     */
    fun onLogout(logic: (Plugin).() -> Unit) = r.bindLogout(logic)

    /**
     * Invoked when an item is swapped on the same component.
     */
    fun onComponentItemSwap(interfaceId: Int, component: Int, plugin: Plugin.() -> Unit) = r.bindComponentItemSwap(interfaceId, component, plugin)

    /**
     * Invoked when an item is swapped between two components.
     */
    fun onComponentToComponentItemSwap(srcInterfaceId: Int, srcComponent: Int, dstInterfaceId: Int, dstComponent: Int, plugin: Plugin.() -> Unit) = r.bindComponentToComponentItemSwap(srcInterfaceId, srcComponent, dstInterfaceId, dstComponent, plugin)

    /**
     * Invokes when a player interaction option is executed
     */
    fun onPlayerOption(option: String, plugin: Plugin.() -> Unit) = r.bindPlayerOption(option, plugin)

    /**
     * Invoked when a player hits 0 hp and is starting their death task.
     */
    fun onPlayerPreDeath(plugin: Plugin.() -> Unit) = r.bindPlayerPreDeath(plugin)

    /**
     * Invoked when a player is sent back to their respawn location on
     * death.
     */
    fun onPlayerDeath(plugin: Plugin.() -> Unit) = r.bindPlayerDeath(plugin)

    /**
     * Invoked when npc with [Npc.id] of [npc] invokes their death task.
     */
    fun onNpcPreDeath(npc: Int, plugin: Plugin.() -> Unit) = r.bindNpcPreDeath(npc, plugin)

    /**
     * Invoked when npc with [Npc.id] of [npc] finishes their death task and
     * is de-registered from the world.
     */
    fun onNpcDeath(npc: Int, plugin: Plugin.() -> Unit) = r.bindNpcDeath(npc, plugin)

    /**
     * Set the combat logic for [npc] and [others], which will override the [setCombatLogic]
     * logic.
     */
    fun onNpcCombat(npc: Int, vararg others: Int, logic: (Plugin).() -> Unit) {
        r.bindNpcCombat(npc, logic)
        others.forEach { other -> r.bindNpcCombat(other, logic) }
    }

    /**
     * Invoke [logic] when [gg.rsmod.game.message.impl.OpNpcTMessage] is handled.
     */
    fun onSpellOnNpc(parent: Int, child: Int, logic: (Plugin).() -> Unit) = r.bindSpellOnNpc(parent, child, logic)

    /**
     * Invoke [logic] when [gg.rsmod.game.message.impl.IfOpenSubMessage] is handled.
     */
    fun onInterfaceOpen(interfaceId: Int, logic: (Plugin).() -> Unit) = r.bindInterfaceOpen(interfaceId, logic)

    /**
     * Invoke [logic] when [gg.rsmod.game.model.interf.InterfaceSet.closeByHash]
     * is handled.
     */
    fun onInterfaceClose(interfaceId: Int, logic: (Plugin).() -> Unit) = r.bindInterfaceClose(interfaceId, logic)

    /**
     * Invoke [logic] when [gg.rsmod.game.message.impl.IfButtonMessage] is handled.
     */
    fun onButton(interfaceId: Int, component: Int, logic: (Plugin).() -> Unit) = r.bindButton(interfaceId, component, logic)

    /**
     * Invoke [logic] when [gg.rsmod.game.message.impl.OpModelMessage] is handled.
     */
    fun onOpModel(interfaceId: Int, component: Int, logic: Plugin.() -> Unit) = r.bindOpModel(interfaceId, component, logic)

    /**
     * Invoke [logic] when [key] reaches a time value of 0.
     */
    fun onTimer(key: TimerKey, logic: (Plugin).() -> Unit) = r.bindTimer(key, logic)

    /**
     * Invoke [logic] when any npc is spawned into the game with [World.spawn].
     */
    fun onGlobalNpcSpawn(logic: (Plugin).() -> Unit) = r.bindGlobalNpcSpawn(logic)

    /**
     * Invoke [logic] when a ground item is picked up by a [gg.rsmod.game.model.entity.Player].
     */
    fun onGlobalItemPickup(logic: Plugin.() -> Unit) = r.bindGlobalGroundItemPickUp(logic)

    /**
     * Invoke [logic] when an npc with [Npc.id] matching [npc] is spawned into
     * the game with [World.spawn].
     */
    fun onNpcSpawn(npc: Int, logic: (Plugin).() -> Unit) = r.bindNpcSpawn(npc, logic)

    /**
     * Invoke [logic] when [gg.rsmod.game.message.impl.ClientCheatMessage] is handled.
     */
    fun onCommand(command: String, powerRequired: String? = null, logic: (Plugin).() -> Unit) = r.bindCommand(command, powerRequired, logic)

    /**
     * Invoke [logic] when an item is equipped onto equipment slot [equipSlot].
     */
    fun onEquipToSlot(equipSlot: Int, logic: (Plugin).() -> Unit) = r.bindEquipSlot(equipSlot, logic)

    /**
     * Invoke [logic] when an item is un-equipped from equipment slot [equipSlot].
     */
    fun onUnequipFromSlot(equipSlot: Int, logic: (Plugin).() -> Unit) = r.bindUnequipSlot(equipSlot, logic)

    /**
     * Return true if [item] can be equipped, false if it can't.
     */
    fun canEquipItem(item: Int, logic: (Plugin).() -> Boolean) = r.bindEquipItemRequirement(item, logic)

    /**
     * Invoke [logic] when [item] is equipped.
     */
    fun onItemEquip(item: Int, logic: (Plugin).() -> Unit) = r.bindEquipItem(item, logic)

    /**
     * Invoke [logic] when [item] is removed from equipment.
     */
    fun onItemUnequip(item: Int, logic: (Plugin).() -> Unit) = r.bindUnequipItem(item, logic)

    /**
     * Invoke [logic] when a player enters a region (8x8 Chunks).
     */
    fun onEnterRegion(regionId: Int, logic: (Plugin).() -> Unit) = r.bindRegionEnter(regionId, logic)

    /**
     * Invoke [logic] when a player exits a region (8x8 Chunks).
     */
    fun onExitRegion(regionId: Int, logic: (Plugin).() -> Unit) = r.bindRegionExit(regionId, logic)

    /**
     * Invoke [logic] when a player enters a chunk (8x8 Tiles).
     */
    fun onEnterChunk(chunkHash: Int, logic: (Plugin).() -> Unit) = r.bindChunkEnter(chunkHash, logic)

    /**
     * Invoke [logic] when a player exits a chunk (8x8 Tiles).
     */
    fun onExitChunk(chunkHash: Int, logic: (Plugin).() -> Unit) = r.bindChunkExit(chunkHash, logic)

    /**
     * Invoke [logic] when the the option in index [option] is clicked on an inventory item.
     *
     * String option method should be used over this method whenever possible.
     */
    fun onItemOption(item: Int, option: Int, logic: (Plugin).() -> Unit) = r.bindItem(item, option, logic)

    /**
     * Invoke [logic] when the the option in index [option] is clicked on a
     * [gg.rsmod.game.model.entity.GameObject].
     *
     * String option method should be used over this method whenever possible.
     *
     * @param lineOfSightDistance
     * If the npc is behind an object such as a prison cell or bank booth, this
     * distance should be set. If the npc can be reached normally, you shouldn't
     * specify this value.
     */
    fun onObjOption(obj: Int, option: Int, lineOfSightDistance: Int = -1, logic: (Plugin).() -> Unit) = r.bindObject(obj, option, lineOfSightDistance, logic)

    /**
     * Invoke [logic] when the the option in index [option] is clicked on an [Npc].
     *
     * String option method should be used over this method whenever possible.
     */
    fun onNpcOption(npc: Int, option: Int, lineOfSightDistance: Int = -1, logic: (Plugin).() -> Unit) = r.bindNpc(npc, option, lineOfSightDistance, logic)

    /**
     * Invoke [logic] when the the option in index [option] is clicked on a [GroundItem].
     *
     * String option method should be used over this method whenever possible.
     */
    fun onGroundItemOption(item: Int, option: Int, logic: (Plugin).() -> Unit) = r.bindGroundItem(item, option, logic)

    /**
     * Set the condition of whether [item] can be picked up as a ground item.
     *
     * @return false if the item can not be picked up.
     */
    fun setGroundItemCondition(item: Int, plugin: Plugin.() -> Boolean) = r.setGroundItemPickupCondition(item, plugin)

    /**
     * Invoke [plugin] when a spell is used on an item.
     */
    fun onSpellOnItem(fromInterface: Int, fromComponent: Int, toInterface: Int, toComponent: Int, plugin: Plugin.() -> Unit) = r.bindSpellOnItem((fromInterface shl 16) or fromComponent, (toInterface shl 16) or toComponent, plugin)

    /**
     * Returns true if the item can be dropped on the floor via the 'drop' menu
     * option - return false otherwise.
     */
    fun canDropItem(item: Int, plugin: (Plugin).() -> Boolean) = r.bindCanItemDrop(item, plugin)

    /**
     * Invoke [plugin] when [item] is used on [npc].
     */
    fun onItemOnNpc(item: Int, npc: Int, plugin: Plugin.() -> Unit) = r.bindItemOnNpc(npc = npc, item = item, plugin = plugin)

    companion object {
        private val METADATA_PATH = Paths.get("./plugins", "configs")
    }
}