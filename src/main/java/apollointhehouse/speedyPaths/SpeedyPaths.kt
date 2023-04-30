package apollointhehouse.speedyPaths

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SpeedyPaths : ModInitializer {
    override fun onInitialize() {
        LOGGER.info("SpeedyPaths initialized.")
    }

    companion object {
        private const val MOD_ID = "speedypaths"
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }
}

