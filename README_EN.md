# AutoSwapLowDurabilityElytra - Automatic Elytra Swapping Mod


[English](README_EN.md) | [中文](README.md)


![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-green?style=flat-square)
[![Mod Loader](https://img.shields.io/badge/Fabric-Loader-blue?style=flat-square)](https://fabricmc.net/)

A utility mod that automatically detects and replaces low-durability elytra, featuring customizable durability thresholds and hot-reload configurations.

## Features

- 🪂 **Automatic Elytra Swapping**  
  Automatically searches inventory for replacement elytra when equipped one falls below durability threshold
- ⚙️ **Customizable Parameters**
    - Set durability threshold for swap trigger
    - Randomized operation delays (prevents anti-cheat detection)
    - Auto-disconnect feature (when durability critically low)
- 🖥️ **Mod Menu Integration**  
  Configure settings through in-game GUI

## Requirements

- Minecraft 1.21
- Fabric Loader
- Fabric API
- Cloth Config API

## Installation

1. Download latest mod jar file
2. Place into Minecraft `mods` folder
3. Launch game

## Configuration

Configure via Mod Menu interface or edit config file directly:

```yaml
# Basic Settings
lowestDurabilityWhenSwap: 23  # Durability threshold to trigger swap
isAutoSwapOn: true           # Enable automatic swapping

# Anti-Detection Settings
swapRandomMinMillisecond: 300  # Minimum operation delay(ms)
swapRandomMaxMillisecond: 600  # Maximum operation delay(ms)
swapRandomDurability: 3        # Durability randomization offset

# Auto-Disconnect Settings
lowestDurabilityWhenLogOut: 10 # Durability threshold to trigger disconnect
isAutoLogOutOn: false          # Enable automatic disconnect
```

## Important Notes

- Use cautiously on servers - I take no responsibility for bans
- Maintain reasonable random delay ranges
- Auto-disconnect will immediately terminate server connection

## License

MIT License - Free to use and modify at your own risk

---

> _💡 This documentation is fully AI-generated_  
> _Powered by [DeepSeek-R1](https://www.deepseek.com)_

---
