#### 3.7.0
- port to 1.21.6
- There are known issues:
  - Tooltip clips behind next Config Entry Label when the tooltip is too tall.

#### 3.6.3
- don't rely on latest neo/forge loader version

#### 3.6.2
- fixing check for RangedEntries

#### 3.6.1
  - fix crash when local language was Turkish

### 3.6.0
  - update to 1.21.5

#### 3.5.2
- fix network issue for all loader

#### 3.5.1
- fix client crash due to wrongly set mixin file

#### 3.5.0
- adding the option for config saving at runtime
  - useful when values get edited with commands
- complete Network overhaul
- fix incorrect Config Screen display when player was set to op
  - previously it was needed to rejoin the server
- general internal cleanup

#### 3.4.2
- fixing an issue where undo button was falsely active
- fixing logging for missing translation keys
  - outputs the usable translation keys for your config

#### v3.4.1
- adding Indicator for Config Option if a world/game restart is necessary
- adding more translations

#### v3.4.0
- complete overhaul of the config api
- using json files instead of toml files
  - this will reset all configurations you made with the old versions
- adding Config Menu to edit the configs without the need of editing files
  - in Singleplayer worlds you can edit Client and Common Configs
  - in Multiplayer, Common and Server Configs are editable, but only if you are an Operator
    when not only the Client Config is editable
- Configs get synced when you enter a World/Server and when a Server Operator
  edits the config it also gets synced with all clients again

#### v2.1.3
- fixing an issue with the dependencies for neoforge

#### v2.1.2
- fixing the accepted Minecraft versions for Fabric version

#### v2.1.1
- Config Values can be tagged to be Synced between Server and Client
  - Lists aren't currently supported

#### v2.0.0
- update release for Minecraft 1.21

#### v1.2.0
- updating NightConfig to 3.7.2
  - this only applies to Fabric, Neo-/Forge ship Night Config on its own

#### v1.1.0
- backport update
- Fabric 1.20 up to 1.20.4
- Forge 1.20 up to 1.20.4
- NeoForge 1.20.4
- All versions are bundled under 1.20 since it isn't using any Loader specific code

#### v1.0.1
- fixing neoforge mods.toml

#### v1.0.0 - excluding Forge for now
 - initial release
 - basically Simple-Config-API but rebranded to fit my other Mods
 - some more Datatypes like Lists