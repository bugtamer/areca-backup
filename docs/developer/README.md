# Areca Backup - Developer documentation

It is not intended to be detailed and precise documentation,
but rather to provide a general understanding of how it works,
which helps reduce the time needed to learn how to maintain Areca.

Only the linked topics have any level of documentation;
the missing links are topics that could be documented.


## Settings

- [Development environment](development-environment.md)
- [Dependencies](../../ivy.xml)
- [Building](building.md)
- [Debugging](debugging.md)
- [Testing](testing.md)
- Security


## Expand Areca

- [Roadmap](https://sourceforge.net/projects/areca-backup/files/)
- [Authors](../../AUTHORS)
- [Plugins](areca_plugins_documentation.pdf)
- [Translations](../../translations/README)
- [Platforms](building.md)
- Contribute
- [Authors](../../AUTHORS)
- First changes after forking Areca Backup


## How what you see works

- Workspace
  - $USER/.areca/[preferences.properties](preferences.properties.md)
  - Target configuration (*.bcfg XML files)
- areca-backup/config/fwk.properties
- Targets
  - Sources (File System)
  - Destinations (File System | FTP | SFTP)
  - Storage modes (Standard | Delta | Image)
  - Compression (None | Zip | Zip 64)
  - File management
  - Encryption
  - Filters
  - Pre/post processing
  - Transactions
- Groups
- Backups (Incremental | Differential | Full | Simulate)
- Archives (e.g. 1234567890)
  - Directory structure
    - 1234567890/yymmdd.zip_data/.committed
    - 1234567890/yymmdd.zip_data/content
    - 1234567890/yymmdd.zip_data/hash
    - 1234567890/yymmdd.zip_data/manifest
    - 1234567890/yymmdd.zip_data/trace
    - 1234567890/history
- Recovers
- [Launchers](launchers.md) (GUI | TUI)
- [TUI](tui/textual-user-interface.md)
- GUI
  - Menus
    - Workspace
      - Open workspace ... (Ctrl + W)
      - Save copy as ... (Ctrl + P)
      - Import ...
      - Preferences ...
      - Quit (Ctrl + Q)
    - Edit
      - New group ... (Ctrl + G)
      - Delete group ...
      - New target ... (Ctrl + T)
      - Edit target ... (Ctrl + E)
      - Delete target ...
      - Duplicate target
      - Wizards
        - Generate backup shortcut ...
        - Generate backup strategy commands ...
    - Run
      - Simulate backup (Ctrl + S)
      - Backup ... (Ctrl + Enter)
      - Merge archives ...
      - Delete archives ... (Ctrl + Delete)
      - Recover ...
      - Check ... (Ctrl + C)
      - Backup all targets
    - Help
      - Help ... (F1)
      - Tutorial ...
      - [Check for new version ...](gui/menu/help/check-for-new-version.md)
      - Plugins ...
      - Support Areca Backup
      - About ...
  - Button bar
    - Modify your preferences
    - Create a new target
    - Edit the target
    - Launch a backup process
    - Merge the archives
    - Delete the archives
    - Recover the archive
    - Displays Areca's help
  - Current workspace / Open a workspace (Workspace selector)
  - Target/Group panel
  - Target/Group detail
  - Tabs
    - Archives / Physical view
    - Logical view
    - Historic
    - Indicators
    - Search
    - Log
    - Progression
  - [UI links](ui-links.md)
  - Icons
  - Shortcut list
  - Window Modals
    - Areca Backup - Backup
    - Areca Backup - Preferences
    - Areca Backup - Target edition
    - Areca Backup - Target deletion
    - Areca Backup - FTP Parameters
    - Areca Backup - Source edition
    - Areca Backup - Filter edition
    - Areca Backup - Action edition
    - Areca Backup - Generate backup shortcut ...
    - Areca Backup - Generate backup strategy commands ...
    - Areca Backup - Create a copy of the current workspace
    - Areca Backup - Import existing configuration
    - Areca Backup - Group Edition
    - Areca Backup - Backup simulation for target_name
    - Areca Backup - Archive check (target_name)
    - Areca Backup - Archive detail
    - Areca Backup - Data recovery
    - Areca Backup - New version found
    - Areca Backup - No new version found
    - Areca Backup - About
    - Areca Backup - Process report
    - Areca Backup - Version check module
  - Context menus
    - Target
      - Simulate backup (Ctrl + S)
      - Backup ... (Ctrl + Enter)
      - New group ... (Ctrl + G)
      - New target ... (Ctrl + T)
      - Edit target (Ctrl + E)
      - Edit XML configurarion ...
      - Delete target ...
      - Duplicate target
      - Wizards
        - Generate backup shortcut ...
        - Generate backup strategy commands ...
    - Group
      - Backup ... (Ctrl + Enter)
      - New group ... (Ctrl + G)
      - Delete group ...
      - New target ... (Ctrl + T)
      - Generate backup shortcut ...
    - Archive
      - Merge archives ...
      - Delete archive ...
      - Recover ...
      - Check ...
      - Archive detail ...
    - Logical view
      - Recover ...
      - Copy file names
      - View ...
      - View as test ...
    - Search
      - Archives
      - Logical view
    - Log
      - Clear
    - Areca Backup - Archive detail (modal)
      - Recover ...
      - Copy file names
