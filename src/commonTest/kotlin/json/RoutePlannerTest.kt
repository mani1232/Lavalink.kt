package json

import dev.kord.x.lavalink.rest.NanoIpRoutePlanner
import dev.kord.x.lavalink.rest.RotatingIpRoutePlanner
import dev.kord.x.lavalink.rest.RotatingNanoIpRoutePlanner
import dev.kord.x.lavalink.rest.RoutePlannerStatus
import json.src.NANO_IP_ROUTE_PLANNER
import json.src.ROTATING_IP_ROUTE_PLANNER
import json.src.ROTATING_NANO_IP_ROUTE_PLANNER
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertTrue

class RoutePlannerTest {

    private fun RoutePlannerStatus.Data.IpBlock.validate() {
        type shouldBe "Inet6Address"
        size shouldBe 1213123321
    }

    private fun List<RoutePlannerStatus.Data.FailingAddress>.validate() {
        assertTrue(size == 1)
        first().run {
            address shouldBe "/1.0.0.0"
            failingTimestamp shouldBe 1573520707545
            failingTime shouldBe "Mon Nov 11 20:05:07 EST 2019"
        }
    }

    @JsName("testRotatingNanoIpRoutePlanner")
    @Test
    fun `test rotating nano ip route planner`() {
        test<RotatingNanoIpRoutePlanner>(ROTATING_NANO_IP_ROUTE_PLANNER) {
            `class` shouldBe RoutePlannerStatus.Class.RotatingNanoIpRoutePlanner
            details.run {
                ipBlock.validate()
                failingAddresses.validate()
                blockIndex shouldBe 0
                currentAddressIndex shouldBe 36792023813
            }
        }
    }


    @JsName("testRotatingIpRoutePlanner")
    @Test
    fun `test rotating ip route planner`() {
        test<RotatingIpRoutePlanner>(ROTATING_IP_ROUTE_PLANNER) {
            `class` shouldBe RoutePlannerStatus.Class.RotatingIpRoutePlanner
            details.run {
                ipBlock.validate()
                failingAddresses.validate()
                rotateIndex shouldBe "1"
                ipIndex shouldBe "1"
                currentAddress shouldBe "1"
            }
        }
    }

    @JsName("testNanoIpRoutePlanner")
    @Test
    fun `test nano ip route planner`() {
        test<NanoIpRoutePlanner>(NANO_IP_ROUTE_PLANNER) {
            `class` shouldBe RoutePlannerStatus.Class.NanoIpRoutePlanner
            details.run {
                ipBlock.validate()
                failingAddresses.validate()
                currentAddressIndex shouldBe 1
            }
        }
    }
}