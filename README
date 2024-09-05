# Areca Backup

It basically allows you to select a set of files / directories to backup,
choose where and how (as a simple file copy or as a zip archive) they will be stored,
and configure post-backup actions (like sending backup reports by email or launching custom shell scripts).

It has been designed to ?

- ***Be as simple as possible to set up*** :<br>
  No complex configuration files to edit<br>
  Your backup configuration (stored as an XML file)
  can be edited with Areca's graphical user interface.

- ***Be as versatile as possible*** :<br>
  Areca can use advanced backup modes (like *delta backup*)
  or simply produce a basic copy of your source files as a standard directory
  or zip archive (readable by File Explorer in Windows, WinRAR, WinZip, 7-Zip, PeaZip or other archivers).

- ***Allow you to interact with your archives*** :<br>
  Browse your archives, track and recover a specific version of a file,
  merge a set of archives into a single one, etc.

[![](documentation/user/images/main_sc.jpg)](documentation/user/screenshots.md "See more screenshots")

[Screenshots](documentation/user/screenshots.md)

> **:warning: `areca-backup.org` is no longer the official site at least for this Areca fork.**


## Table Of Content

- [Main features](#main-features)
- [Documentation](#documentation)
  - [End user documentation](#end-user-documentation)
  - [Technical informations](#technical-informations)
  - [Documentation for developers](#documentation-for-developers)


## Main features

- [Multiplatform](documentation/user/documentation.md#how-to-install-areca-)
- [Compression](documentation/user/documentation.md#compression) (Zip & Zip64).
- [Encryption](documentation/user/documentation.md#encryption) (AES128 & AES256).
- [Storage](documentation/user/documentation.md#target-settings)
  on local hard drive, SSD, NVMe, network drive, USB drive, FTP, FTPs or SFTP server.
- [File filters](documentation/user/documentation.md#file-filters)
  (by extension, subdirectory, regular expression, size, date, status, with AND/OR/NOT logical operators).
- [Incremental, differential and full backup](documentation/user/documentation.md#backup-types) support.
- Support for [delta backup](documentation/user/documentation.md#storage-modes).
- [ACL](documentation/user/faq.md#acl-and-extended-attributes-support-)
  and Extended attributes support (Linux only)
- Support several [languages](documentation/user/documentation.md#translations)
  (br, cn, cs, da, nl, sv, tw, de, en, es, fr, hu, it, ja, pt, ru).
- Archives [merges](documentation/user/documentation.md#merge).
- As of date [recovery](documentation/user/documentation.md#how-to-recover):
  Areca allows you to recover your archives (or single files) as of a specific date.
- [Transaction mechanism](documentation/user/features.md#backup-engine-features-).
- Backup [reports](documentation/user/documentation.md#prepost-processing)
  (disk or [email](documentation/user/images/linux/original/email.jpg)).
- Pre and Post backup [scripts](documentation/user/documentation.md#prepost-processing).
- Files [permissions](documentation/user/documentation.md#files-management),
  [symbolic links](documentation/user/documentation.md#file-filters)
  and named pipes can be stored and recovered (Linux only).
- Backup [Wizards](documentation/user/documentation.md#using-the-wizards)
- [Plugins](documentation/user/documentation.md#extending-areca).
- [CLI](documentation/user/documentation.md#command-line-interface)
  (Command-Line Interface)
- [GUI](documentation/user/documentation.md#gui)
  ([Graphical User Interface](documentation/user/screenshots.md "Screenshots"))
    - [Archives content explorer](documentation/user/images/linux/original/physical_view.jpg).
    - [File version tracking](documentation/user/images/linux/original/logical_view.jpg)
      (creation / modifications / deletion).
    - [Archive description](documentation/user/images/linux/original/detail_properties.jpg).
    - [Backup simulation](documentation/user/images/linux/original/simulation.jpg).
    - [User's actions history](documentation/user/images/linux/original/history.jpg)
      (archives deletion, merges, backups, recoveries).
    - Areca takes the look and feel of the underlying operative system.


## Documentation

### End user documentation

- [Tutorial](documentation/user/tutorial.md)
  1. [Configuring your first target](documentation/user/tutorial1.md)
  2. [Running your first backup](documentation/user/tutorial2.md)
  3. [Recovering files from your archives](documentation/user/tutorial3.md)
  4. [Merging your archives](documentation/user/tutorial4.md)
  5. [Recovering files when your backup configuration has been lost](documentation/user/tutorial5.md)
- [User's manual](documentation/user/documentation.md)
  - [What is Areca?](documentation/user/documentation.md#what-is-areca-)
  - [What is NOT Areca?](documentation/user/documentation.md#what-is-not-areca-)
  - [Why use Areca?](documentation/user/documentation.md#why-use-areca-)
  - [User interfaces](documentation/user/documentation.md#user-interfaces)
  - [How to install Areca?](documentation/user/documentation.md#how-to-install-areca-)
  - [Basic concepts](documentation/user/documentation.md#basic-concepts)
  - [Target settings](documentation/user/documentation.md#target-settings)
  - [Archive management](documentation/user/documentation.md#archive-management)
  - [Using the graphical user interface](documentation/user/documentation.md#using-the-graphical-user-interface)
  - [Using the command-line interface](documentation/user/documentation.md#using-the-command-line-interface)
  - [How to recover](documentation/user/documentation.md#how-to-recover)
  - [Scheduling](documentation/user/documentation.md#scheduling)
- [FAQ](documentation/user/faq.md)

### Technical informations

- [Regular expressions](documentation/user/regex.md)
- [Translations](documentation/user/documentation.md#translations)
- [Extending Areca](documentation/user/documentation.md#extending-areca)
- [Config backup](documentation/user/config_backup.md)

### Documentation for developers

- [Development environment](documentation/developer/development-environment.md)
- [Build Areca Backup](documentation/developer/building.md)

---

Mirrors of no longer available [**documentation**](https://bugtamer.github.io/areca-backup-legacy-documentation/) from:
- [areca-backup.org](https://bugtamer.github.io/areca-backup-legacy-documentation/areca-backup.org/) (Olivier PETRUCCI's Areca official site)
- [arecavss.com](https://bugtamer.github.io/areca-backup-legacy-documentation/arecavss.com/)
  (Volume Shadow Copy plugin)
