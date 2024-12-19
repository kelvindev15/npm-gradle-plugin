package io.github.kelvindev15.utils

/**
 * Represents the platform the plugin is running on.
 */
object Platform {
    /**
     * Represents the operating system the plugin is running on.
     */
    enum class OS {
        /**
         * Represents the Windows operating system.
         */
        WINDOWS,

        /**
         * Represents the Mac operating system.
         */
        MAC,

        /**
         * Represents the Linux operating system.
         */
        LINUX,
    }

    /**
     * Detects the operating system the plugin is running on.
     */
    fun detectOS(): OS =
        when {
            checkOS("Windows") -> OS.WINDOWS
            checkOS("Mac") -> OS.MAC
            checkOS("Linux") -> OS.LINUX
            else -> error("Unsupported OS")
        }

    private fun checkOS(os: String): Boolean = System.getProperty("os.name").startsWith(os)
}
