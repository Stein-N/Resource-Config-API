![](https://cf.way2muchnoise.eu/title/975801.svg)![](https://cf.way2muchnoise.eu/975801.svg)![](https://cf.way2muchnoise.eu/versions/975801.svg)

![Title Header](https://cdn.modrinth.com/data/69TY9iyJ/images/9766e95e84d5abf6c2ff5ca19a5f317bc6280d3a.png)

A Config API for Fabric, (Quilt) and Neo-/Forge which is based on [NightConfig Library](https://github.com/TheElectronWill/night-config).
It is downloadable on [Curseforge](https://www.curseforge.com/minecraft/mc-mods/resource-config-api) and[Modrinth](https://modrinth.com/mod/resource-config-api).

This API is basically [Simple Config API](https://www.curseforge.com/minecraft/mc-mods/simple-config-api) but renamed to fit my other Mods, since the Major Update 1.21.

### Features:

- Save different Datatypes
  - Integer, Doubles, Strings and Booleans
  - Values and Categories can be commented
  - Values can be defined in Range
- Designed for Multi-Loader Projects
- Autocorrection of invalid or corrupt Config Values

### Planned:

- Support more Datatypes
- implement a Config Screen
  - eventually for all .toml files or only for files created with this API
- Synchronize Config between Server and Client
- apply changed values dynamically while Minecraft is running

### For Developer:

#### Latest Version: 0.1.0
#### Available for:
- Fabric 1.21 -> latest
- Forge 1.21 -> latest
- NeoForge 1.21 -> latest


<details>
<summary>Adding Resource Config API to your Project</summary>

````groovy
    repositories {
        maven {
          name = "xStopho Mods"
          url = "https://raw.githubusercontent.com/Stein-N/resources/main/maven"
        }
    }
````

When you want to use this API in a Quilt Project u have to use the Fabric version for now, i am working on a dedicated version.

````groovy
    dependencies {
        implementation "xstopho.resource-config-api:resource-config-api-common:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-fabric:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-forge:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-neoforge:{major_minecraft_version}+{api_version}"
    }
````

</details>