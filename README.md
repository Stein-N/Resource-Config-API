![](https://cf.way2muchnoise.eu/975801.svg)![](https://cf.way2muchnoise.eu/versions/975801.svg)

![Modrinth Downloads](https://img.shields.io/modrinth/dt/69TY9iyJ?logo=modrinth&label=Downloads&color=%2300AF5C)![Modrinth Version](https://img.shields.io/modrinth/v/69TY9iyJ?logo=modrinth&label=Available%20For&color=%2300AF5C)

![Title Header](https://cdn.modrinth.com/data/69TY9iyJ/images/9766e95e84d5abf6c2ff5ca19a5f317bc6280d3a.png)

This Config API is based on the [NightConfig Library](https://github.com/TheElectronWill/night-config) and is very basic.
It doesn't have fancy Features, simply create Config Files and save your values in it.

It is downloadable on [Curseforge](https://www.curseforge.com/minecraft/mc-mods/resource-config-api) and [Modrinth](https://modrinth.com/mod/resource-config-api).

This API is basically [Simple Config API](https://www.curseforge.com/minecraft/mc-mods/simple-config-api) but renamed to fit my other Mods and with more Features and Datatypes, 
since Update 1.20.5.

### Features:

- Save different Datatypes
  - Primitive: Integer, Double, String, Boolean
  - Reference: List, ArrayList, LinkedList
- Values and Categories can be commented
- Values can be defined in Range
- Custom Config Path
- Designed for Multi-Loader Projects
- Autocorrection of invalid or corrupt Config Values

### Planned:

- Support more Datatypes
- Translatable Comments
  - will be defined through the lang file

### For Developer:

#### Latest Version: 1.0.0
#### Available for:
- Fabric 1.20.5. -> latest
- Forge 1.20.5. -> latest
- NeoForge 1.20.5. -> latest

### Note:
- Float, Long, Short, Byte and Char can't be supported due to parsing incompatibilities.
- Quilt will not get a dedicated version, use the Fabric Version instead.

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

When you want to use this API in a Quilt Project u have to use the Fabric version.
````groovy
    dependencies {
        implementation "xstopho.resource-config-api:resource-config-api-common:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-fabric:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-forge:{major_minecraft_version}+{api_version}"
        implementation "xstopho.resource-config-api:resource-config-api-neoforge:{major_minecraft_version}+{api_version}"
    }
````
</details>