# AutoSwapLowDurabilityElytra - 自动鞘翅替换模组


[English](README_EN.md) | [中文](README.md)


![Minecraft](https://img.shields.io/badge/Minecraft-1.21+-green?style=flat-square)
[![Mod Loader](https://img.shields.io/badge/Fabric-Loader-blue?style=flat-square)](https://fabricmc.net/)

自动检测并替换低耐久鞘翅的实用模组，支持自定义耐久阈值和热插拔配置。



## 功能特性

- 🪂 **自动更换鞘翅**  
  当装备的鞘翅耐久低于阈值时，自动从背包寻找备用鞘翅进行更换
- ⚙️ **可配置参数**
  - 设置更换触发耐久值
  - 随机化操作延迟（防止被反作弊检测）
  - 自动登出功能（耐久过低时自动断开连接）
- 🖥️ **Mod Menu集成**  
  通过游戏内图形界面轻松配置参数

## 安装要求

- Minecraft 1.20.4+
- Fabric Loader
- Fabric API
- Cloth Config API

## 安装步骤

1. 下载最新版本的模组jar文件
2. 放入Minecraft游戏目录的`mods`文件夹
3. 启动游戏

## 配置说明

通过Mod Menu界面配置或直接修改配置文件：

```yaml
# 基础设置
lowestDurabilityWhenSwap: 23  # 触发更换的耐久阈值
isAutoSwapOn: true           # 启用自动更换

# 防检测设置
swapRandomMinMillisecond: 300  # 最小操作延迟(ms)
swapRandomMaxMillisecond: 600  # 最大操作延迟(ms)
swapRandomDurability: 3        # 耐久随机偏移值

# 自动登出设置 
lowestDurabilityWhenLogOut: 10 # 触发登出的耐久阈值
isAutoLogOutOn: false          # 启用自动登出
```


## 注意事项


- 在服务器里小心使用，被 Ban 了我可不管

- 建议保持随机延迟的合理范围

- 自动登出功能会直接断开服务器连接


## 许可证

MIT License - 自由使用和修改，使用风险自负

---

> _💡 本文档完全由人工智能生成_  
> _Powered by [DeepSeek-R1](https://www.deepseek.com)_

---
