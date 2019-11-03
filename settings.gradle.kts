pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "luminoso-api"

include("luminoso-gateway")
include("luminoso-security")
include("luminoso-real-estate")
include("luminoso-communications")

