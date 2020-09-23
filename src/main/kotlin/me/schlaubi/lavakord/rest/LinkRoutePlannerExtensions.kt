package me.schlaubi.lavakord.rest

import lavalink.client.io.Link
import me.schlaubi.lavakord.asKordLink
import me.schlaubi.lavakord.audio.KordLink

/**
 * Retrieves the current address status of the route planner api.
 *
 * @see RoutePlannerStatus
 */
suspend fun Link.addressStatus(): RoutePlannerStatus = asKordLink().addressStatus()

/**
 * Unmarks all route planner addresses.
 */
suspend fun Link.unmarkAllAdresses(): Unit = asKordLink().unmarkAllAddresses()

/**
 * Unmarks the route planner [address].
 */
suspend fun Link.unmarAdresse(address: String): Unit = asKordLink().unmarAdresse(address)

private suspend fun KordLink.addressStatus(): RoutePlannerStatus {
    val node = this.getNode(true) ?: error("No node available")
    val url = node.buildUrl {
        path("/routeplanner/status")
    }

    return node.get(url)
}

private suspend fun KordLink.unmarkAllAddresses() {
    val node = this.getNode(true) ?: error("No node available")
    val url = node.buildUrl {
        path("/routeplanner/free/all")
    }

    return node.get(url)
}

private suspend fun KordLink.unmarkAddress(address: String) {
    val node = this.getNode(true) ?: error("No node available")
    val url = node.buildUrl {
        path("/routeplanner/free/address")
    }

    return node.post(url) {
        body = mapOf("address" to address)
    }
}