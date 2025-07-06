# Areca Backup - Textual User Interface (tui)



- TUI [Launcher.java](../../../src/com/application/areca/launcher/tui/Launcher.java),
  AKA Command-Line Interface (CLI),
  handles the Terminal User Interface (TUI).


## TUI Help

Data used for the following examples :

- Areca installation directory : `E:\areca` 
- Workspace : `C:\Users\jdoe\.areca\workspace` 
- Target id : `1234567890`
- XML configuration file : `C:\Users\jdoe\.areca\workspace\1234567890.bcfg`
- Backup repository : `E:\backups`


### Show informations about Areca

`infos`

Examples

- `E:\areca\areca_cl.bat infos` (Specifies the full path if Areca is not defined in the `PATH` system variable)
- `areca_cl.bat infos`
- `areca_cl.exe infos`
- `./bin/areca_cl.sh infos` (for GNU/Linux)


### Describe targets

`describe` `-config` **(xml configuration file or directory)**

Examples

- `E:\areca\areca_cl.bat describe -config C:\Users\jdoe\.areca\workspace\1234567890.bcfg`
- `areca_cl.bat describe -config C:\Users\jdoe\.areca\workspace\1234567890.bcfg`


### Launch a backup


`backup` `-config` **(xml configuration file or directory)** [`-f`] [`-d`] [`-c`] [`-wdir` **(working directory)**] [`-s`] [`-title` **(archive title)**]

- `-f` to force full backup (instead of incremental backup)
- `-d` to force differential backup (instead of incremental backup)
- `-c` to check the archive consistency after backup
- `-wdir` to use a specific working directory during archive check
- `-resume` to resume a pending backup if found
- `-cresume` **(nb days)** to resume a pending backup if younger than 'nb days'
- `-s` to disable asynchronous processing when handling a target group
- `-title` to set a title to the archive


### Merge archives

`merge` `-config` **(xml configuration file)** [`-title` **(archive title)**] [`-k`] `-date` **(merged date : YYYY-MM-DD)** / `-from` **(nr of days - 0='-infinity')** `-to` **(nr of days - 0='today')**

- `-k` to keep deleted files in the merged archive
- `-title` to set a title to the archive
- `-c` to check the archive consistency after merge
- `-wdir` to use a specific working directory
- `-date` to specify the reference date used for merging <br>
  OR `-from`/`-to` **(nr of days)** to specify the archive range used for merging


### Delete archives

`delete` `-config` **(xml configuration file)** [`-date` **(deletion date : YYYY-MM-DD)** / `-delay` **(nr of days)**]


### Recover archives

`recover` `-config` **(xml configuration file)** `-destination` **(destination folder)** [`-date` **(recovered date : YYYY-MM-DD)**] [`-c`]

- `-c` to check consistency of recovered files
- `-o` to overwrite existing files
- `-nosubdir` to prevent Areca to perform the recovery in a subdirectory
- `-date` to specify the recovery date


### Check archives

`check` `-config` **(xml configuration file)** [`-wdir` **(working directory)**] [`-date` **(checked date : YYYY-MM-DD)**] [`-a`]

- `-wdir` to use a specific working directory
- `-a` to check all files (not only those contained in the archive denoted by the date argument)
- `-date` to specify the archive which will be checked


### No argument output

See `printHelp()` of TUI [Launcher.java](../../../src/com/application/areca/launcher/tui/Launcher.java).

**`E:\areca\areca_cl.bat`**

```output
INFO -  - ------------------------------------------------------------------
INFO -  - Areca Backup
INFO -  - Copyright 2005-2025, Olivier PETRUCCI
INFO -  - List of valid arguments :
INFO - 
INFO -  -       infos
INFO - 
INFO -  - Show informations about Areca :
INFO -  - Describe targets :
INFO - 
INFO -  - Launch a backup :
INFO -  -       describe -config (xml configuration file or directory)
INFO -  -       backup -config (xml configuration file or directory) [-f] [-d] [-c] [-wdir (working directory)] [-s] [-title (archive title)]
INFO -  -          -d to force differential backup (instead of incremental backup)
INFO -  -          -c to check the archive consistency after backup
INFO -  -          -wdir to use a specific working directory during archive check
INFO -  -          -resume to resume a pending backup if found
INFO -  -          -cresume (nb days) to resume a pending backup if younger than 'nb days'
INFO -  -          -s to disable asynchronous processing when handling a target group
INFO -  -          -title to set a title to the archive
INFO - 
INFO -  - Merge archives :
INFO -  -       merge -config (xml configuration file) [-title (archive title)] [-k] -date (merged date : YYYY-MM-DD) / -from (nr of days - 0='-infinity') -to (nr of days - 0='today')
INFO -  -          -k to keep deleted files in the merged archive
INFO -  -          -title to set a title to the archive
INFO -  -          -c to check the archive consistency after merge
INFO -  -          -wdir to use a specific working directory
INFO -  -          -date to specify the reference date used for merging
INFO -  -          OR -from/-to (nr of days) to specify the archive range used for merging
INFO - 
INFO -  - Delete archives :
INFO -  -       delete -config (xml configuration file) [-date (deletion date : YYYY-MM-DD) / -delay (nr of days)]
INFO - 
INFO -  - Recover archives :
INFO -  -       recover -config (xml configuration file) -destination (destination folder) [-date (recovered date : YYYY-MM-DD)] [-c]
INFO -  -          -f to force full backup (instead of incremental backup)
INFO -  -          -c to check consistency of recovered files
INFO -  -          -o to overwrite existing files
INFO -  -          -nosubdir to prevent Areca to perform the recovery in a subdirectory
INFO - 
INFO -  - Check archives :
INFO -  -       check -config (xml configuration file) [-wdir (working directory)] [-date (checked date : YYYY-MM-DD)] [-a]
INFO -  -          -date to specify the recovery date
INFO -  -          -wdir to use a specific working directory
INFO -  -          -date to specify the archive which will be checked
INFO - 
INFO -  - ------------------------------------------------------------------
INFO - 
INFO -  - Error : invalid arguments (a command must be provided)
INFO -  -          -a to check all files (not only those contained in the archive denoted by the date argument)
```
