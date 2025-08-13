# AutoSwapLowDurabilityElytra - Automatic Elytra Replacement Mod

[English](README_EN.md) | [中文](README.md)

![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-green?style=flat-square)  
[![Mod Loader](https://img.shields.io/badge/Fabric-Loader-blue?style=flat-square)](https://fabricmc.net/)

A practical mod that automatically detects and replaces low-durability elytra, featuring customizable thresholds and hot-reloadable configurations.

## Features

- 🪂 **Automatic Elytra Swap**  
  Automatically replaces equipped elytra when durability falls below threshold, using backups from inventory
- 🛑 **Fall Protection Mechanism**  
  Real-time Y-coordinate monitoring with automatic disconnect when exceeding safe altitude
- ⚙️ **Customizable Parameters**
  - Set durability threshold for swapping
  - Randomized operation delays
  - Dual auto-logout system (triggers on critical durability/abnormal altitude)
- 🖥️ **Mod Menu Integration**  
  Easy in-game configuration via GUI

## Installation Requirements

- **Mod Version 1.0.0**  
  ▸ Supported Minecraft Versions: `1.21` and `1.21.1`  
  ▸ Required Dependencies:  
  &nbsp;&nbsp;• Fabric Loader  
  &nbsp;&nbsp;• Fabric API  
  &nbsp;&nbsp;• Cloth Config API

- **Mod Version 1.0.1**  
  ▸ Supported Minecraft Versions: `1.21.2`, `1.21.3` and `1.21.4`  
  ▸ Required Dependencies:  
  &nbsp;&nbsp;• Fabric Loader  
  &nbsp;&nbsp;• Fabric API  
  &nbsp;&nbsp;• Cloth Config API
  
- **Mod Version 1.0.2**  
  ▸ Supported Minecraft Versions: `1.21.5`  and `1.21.6`
  ▸ Required Dependencies:  
  &nbsp;&nbsp;• Fabric Loader  
  &nbsp;&nbsp;• Fabric API  
  &nbsp;&nbsp;• Cloth Config API

  - **Mod Version 1.0.3**  
  ▸ Supported Minecraft Versions: `1.21.7`  and `1.21.8`
  ▸ Required Dependencies:  
  &nbsp;&nbsp;• Fabric Loader  
  &nbsp;&nbsp;• Fabric API  
  &nbsp;&nbsp;• Cloth Config API

## Installation

1. Download the latest mod jar file
2. Place it in your Minecraft `mods` folder
3. Launch the game

## Configuration

Configure via Mod Menu interface or edit config file directly:

```yaml
# Basic Settings
lowestDurabilityWhenSwap: 23  # Durability threshold for swap
isAutoSwapOn: true           # Enable auto-swap

# Anti-Detection Settings
swapRandomMinMillisecond: 300  # Minimum operation delay(ms)
swapRandomMaxMillisecond: 600  # Maximum operation delay(ms)
swapRandomDurability: 3        # Durability randomization offset

# Auto-Logout Settings
lowestDurabilityWhenLogOut: 10 # Logout durability threshold
lowestYCoordinateWhenLogOut: 1000 # Fall protection altitude
isAutoLogOutOn: false          # Enable auto-logout
```

## Important Notes

- Use with caution on servers - I take no responsibility for ban
- Recommended to keep random delays within 200-800ms reasonable range
- Auto-logout will immediately disconnect from server and return to main menu

## License

MIT License - Use freely at your own risk

---

> _💡 This documentation is fully AI-generated_  
> _Powered by [DeepSeek-R1](https://www.deepseek.com)_
