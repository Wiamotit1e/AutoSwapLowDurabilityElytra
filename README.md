# AutoSwapLowDurabilityElytra - 自动鞘翅替换模组

![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-green?style=flat-square)
[![Mod Loader](https://img.shields.io/badge/Fabric-Loader-blue?style=flat-square)](https://fabricmc.net/)

自动检测并替换低耐久鞘翅的实用模组，支持自定义耐久阈值和热插拔配置。


## ✨ 功能特性

- **自动耐久监控**  
  实时检测装备中鞘翅的耐久值
- **智能物品交换**  
  当耐久低于设定值时自动替换背包中的新鞘翅
- **可视化配置**  
  通过游戏内Mod菜单轻松调整参数：
  - 📏 设置最低触发耐久值（默认23）
  - 🔄 一键开启/关闭自动替换功能

## 📥 安装指南

### 前置需求
- [Fabric Loader](https://fabricmc.net/use/) 
- [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
- [Cloth Config](https://www.curseforge.com/minecraft/mc-mods/cloth-config)

### 安装步骤
1. 下载最新版模组jar文件
2. 放入Minecraft实例的`mods`文件夹
3. 启动游戏即可生效

## ⚙️ 配置说明

通过游戏菜单访问配置：
1. 主菜单 → Mod列表
2. 找到"WIamMod"
3. 点击配置按钮

可调整参数：
- **最低触发耐久**：设置自动替换的耐久阈值（0-最大耐久值）
- **功能开关**：全局启用/禁用自动替换

## 🛠️ 开发者信息

### 技术栈
- Mixin注入
- Cloth Config API
- Auto Config集成

### 核心逻辑
```java
// 耐久检测与交换逻辑示例
if(elytraDurability <= (maxDurability - configThreshold)) {
    findNewElytra();
    swapInventorySlots();
}
```



---

> _💡 本文档完全由人工智能生成_  
> _Powered by [DeepSeek-R1](https://www.deepseek.com)_

---
