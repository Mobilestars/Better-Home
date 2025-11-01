# 🏡 BetterHomes

A lightweight, modern **home plugin** for Paper/Spigot servers — simple, safe, and fully customizable.  
Players can set multiple homes, teleport safely, and admins can control home limits per rank!

---

## ✨ Features

- 🧭 **Set, teleport, and delete homes** easily  
  - `/sethome [name]` – Set a home (named or default)  
  - `/home [name]` – Teleport to a home  
  - `/delhome [name]` – Delete a home  

- 🏠 **Multiple homes per player**  
  - Each player can have multiple named homes  
  - `/home` without a name uses the “default” home  

- ⚙️ **Configurable home limits**
  - Set a **global max-home limit** in `config.yml`
  - Assign **rank-based limits** using LuckPerms permissions  

- 🔐 **LuckPerms integration**
  - Fully compatible — grant players more homes with:
    ```bash
    /lp group vip permission set betterhome.group.vip true
    ```
  - Configure rank limits directly in your `config.yml`:
    ```yaml
    rank-limits:
      vip: 3
      premium: 5
      admin: 10
    ```

- ⚠️ **Safety checks**
  - Prevent teleporting into blocks  
  - Shows an actionbar message `Area is unsafe!` if teleport would be dangerous  

- 💾 **Simple data storage**
  - Homes are stored in `homes.yml`  
  - Clean, human-readable format  

- ✅ **No OP required**
  - Everyone can use `/home`, `/sethome`, and `/delhome` by default  

---

## 🛠️ Configuration Example

```yaml
default-home-limit: 1

rank-limits:
  vip: 3
  premium: 5
  admin: 10
```

---

## 📜 Permissions

| Permission                | Description                        | Default |
|----------------------------|------------------------------------|----------|
| `betterhome.sethome`       | Allows players to set homes        | ✅       |
| `betterhome.home`          | Allows teleporting to homes        | ✅       |
| `betterhome.delhome`       | Allows deleting homes              | ✅       |
| `betterhome.group.<rank>`  | Used for rank-based home limits    | ❌       |

---

## 🧩 Compatibility

| Server Type | Supported |
|--------------|------------|
| **PaperMC**  | ✅ |
| **Spigot**   | ✅ |
| **Purpur**   | ✅ |

**Minecraft Versions:** `1.18 → 1.21+`  
**Java:** `17 or higher recommended`

---

## 🧑‍💻 Developer Info

- **Author:** Mobilestars  
- **Version:** 1.0.0  
- **Prefix:** `Home`

---

## ❤️ Support the Project

If you enjoy **BetterHomes**, consider giving it a ❤️ on [Modrinth](https://modrinth.com/plugin/better-homes) 
or sharing it with your friends!
