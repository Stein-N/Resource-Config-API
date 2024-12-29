#### v3.3.0
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