pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "luminoso-api"

include("tcs")
include("gateway")
include("users-management")
include("candidate-management")
include("communications")
include("messages")

