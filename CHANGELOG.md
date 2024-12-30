#### v3.4.1
- fixing an issue where undo button was falsely active
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