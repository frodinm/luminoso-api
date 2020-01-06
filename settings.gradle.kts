pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "luminoso-api"

include("authorization")
include("tcs")
include("gateway")
include("users-management")
include("candidate-management")
include("website-management")
include("communications")
include("messages")

