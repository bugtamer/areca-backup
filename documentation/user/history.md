# Areca Backup - Versions history

| About Areca                   | End user documentation            | Technical informations                        |
|-------------------------------|-----------------------------------|-----------------------------------------------|
| [Home](README.md)             | [Plugins](plugin_list.md)         | [Regular expressions](regex.md)               |
| [Features](features.md)       | [Versions history](history.md)    | [Translations](documentation.md#translations) |
| [Plugins](plugin_list.md)     | [Tutorial](tutorial.md)           | [Config backup](config_backup.md)             |
| [Screenshots](screenshots.md) | [User's manual](documentation.md) |                                               |
| [Download]                    | [FAQ](faq.md)                     |                                               |
| [Bug & feature requests]      | [Support & Contact](support.md)   |                                               |
| [Forums]                      |                                   |                                               |

[Download]: https://sourceforge.net/projects/areca/files/areca-stable/
[Bug & feature requests]: https://sourceforge.net/p/areca/_list/tickets?source=navbar
[Forums]: https://sourceforge.net/projects/areca/forums


> Areca's current stable version is 7.5 (2015-8-26)

## Version 7.5 (released on 2015-8-26)

- Fixed the issue that prevented archives from being created when there were only deleted files (no new files);Fixed a minor issue when reconnecting to a FTP server.

## Version 7.4.9 (released on 2014-11-26)

- Fixed compression issue that could occur with filenames containing line breaks.

## Version 7.4.8 (released on 2014-11-2)

- Fixed encryption issued which could occur with recent versions of JREs

## Version 7.4.7 (released on 2014-6-16)

- Fixed a performance regression introduced in v7.4

## Version 7.4.6 (released on 2014-5-15)

- Fixes a concurrency issue that could occur when creating temporary files
- Minor improvements.

## Version 7.4.5 (released on 2014-5-1)

- Fixes the SFTP kerberos dialog box issue with Java7
- Fixes SWT issues on 64 bit JREs.

## Version 7.4.4 (released on 2014-4-6)

- Fixes a compatibility issue with Java-8
- Chineese translation update.

## Version 7.4.3 (released on 2014-3-19)

- Fixes a bug that could occur on Posix systems when the file's owner group was empty
- New '%BACKUP_TYPE%' dynamic tag for post-processors (full/incremental/differential)
- Ability to limit Areca's throughput when writing files
- If 32 and 64 bits Java environments are installed on the system, Areca will use the 64 bit by default (windows only)
- Areca can now use up to 1GB RAM.

## Version 7.4.2 (released on 2014-1-19)

- Fixes a bug in Delta storage that could generate 'out of memory' errors when reading large files.

## Version 7.4.1 (released on 2014-1-17)

- Minor memory optimizations

## Version 7.4 (released on 2013-12-5)

- Fixed issues that could occur on temporary files in multi-users environment
- Areca now attempts to locate your archives when importing backup configurations
- Minor memory optimizations when handling large directories
- Max memory that can be used by Areca has been increased from 256MB to 512MB.

## Version 7.3.9 (released on 2013-11-29)

- Better error management when reading metadata
- Cleaner configuration import window
- Fixed a bug that occurred when using the '-date' switch in the command line tool

## Version 7.3.8 (released on 2013-11-3)

- Additional integrity checks after archive merges
- Fixed memory management issue when recovering
- Prevent from modifying backup scheme (full, differential, incremental) when resuming an interrupted backup.

## Version 7.3.7 (released on 2013-8-31)

- CS, ES and HU translation updates.

## Version 7.3.6 (released on 2013-8-21)

- Added certificate authentication for SFTP connectivity
- Passwords are now encrypted in configuration files.

## Version 7.3.5 (released on 2013-7-14)

- Fixed a but that prevented from creating backup shortcuts in some configurations.

## Version 7.3.4 (released on 2013-6-24)

- Improved error messages when dangling symbolic links are encountered.

## Version 7.3.3 (released on 2013-5-12)

- Improved error messages in case of invalid encryption configuration
- Delta storage bugfix
- Fixed memory issues that could occur when too many errors were encountered while checking archives.

## Version 7.3.2 (released on 2013-5-10)

- Fixed a bug that could prevent backups to be resumed in case of error.

## Version 7.3.1 (released on 2013-4-14)

- Fixed a bug that could prevent the workspace from loading when drives are disconnected.

## Version 7.3 (released on 2013-3-30)

- Performance improvement on delta storage mode.

## Version 7.2.18 (released on 2013-3-22)

- Fixed freeze issues when trying to connect to unresponsive FTP/SFTP servers
- Up-to-date Chinese and Japanese translations.

## Version 7.2.17 (released on 2012-11-17)

- Minor GUI bugfixes.

## Version 7.2.16 (released on 2012-11-13)

- Fixed SMTPs compatibility issue : Areca can now send the 'STARTTLS' command when establishing the connection
- Fixed a bug on transaction points
- Fixed a bug that could occur when handling files starting with a whitespace
- Improved file modification detection : Areca can now inspect the files' content instead of relying on their attributes
- Minor wizard improvements.

## Version 7.2.15 (released on 2012-10-30)

- Minor technical bugfixes.

## Version 7.2.14 (released on 2012-10-23)

- Fixed a compatibility issue with IBM JVMs (Base64 encoding)
- Better error management when handling compressed archives
- Areca now uses by default the local temporary directory as working directory when checking archives
- Better plugins integration

## Version 7.2.13 (released on 2012-10-14)

- Fixed a bug that could occur when handling target sources spread across multiple drives
- better integration of plugins.

## Version 7.2.12 (released on 2012-8-26)

- Fixed a bug introduced in v7.2.11 that could occur with image backups when no file needs to be stored.

Pre processors are now run whether a backup is required or not (ie even if no file has to be stored).

## Version 7.2.11 (released on 2012-8-25)

- Backup is now launched without simulation
- Improved texteditor options
- Minor GUI bugfixes.

Pre processors are now run whether a backup is required or not (ie even if no file has to be stored).

## Version 7.2.10 (released on 2012-5-24)

- Pre/Postprocessors can now be run when archives are merged or checked
- Minor bugfixes and improvements.

## Version 7.2.9 (released on 2012-5-17)

- Added filename encoding verifications when recovering or merging archives
- Minor GUI improvements.

## Version 7.2.8 (released on 2012-5-5)

- Added a 'check archive' option after merge
- CBC mode added to encryption parameters
- Stored file list can be included in backup reports.

## Version 7.2.7 (released on 2012-4-29)

- Minor backup shortcut codepage bug fix
- Classpath bug fix.

## Version 7.2.6 (released on 2012-4-9)

- New email preprocessor
- Translation fix on German language pack
- File filtering bugfix
- Errorcode management bugfix (Windows)
- Minor GUI enhancements

This version changes the way memory settings can be overriden. Please refer to the online help for more informations.

## Version 7.2.5 (released on 2012-1-26)

- Minor improvements & bugfixes.

## Version 7.2.4 (released on 2012-1-18)

- Recovery optimization when handling large volume of data
- Improved recovery options
- Two merge bug fixes

CAUTION : This new version of Areca fixes two bugs in the 'merge' function. It is recommended to check archives created by merging with previous (7.2.x) versions of Areca.

## Version 7.2.3 (released on 2011-8-20)

- Minor bugfixes

## Version 7.2.2 (released on 2011-8-28)

- Statistics can now be added to backup reports
- Recovery performance improvements when handling large number of files
- Minor GUI enhancements and bugfixes.

## Version 7.2.1 (released on 2011-2-19)

- Transaction parameters have been added to the target configuration window.

## Version 7.2 (released on 2011-1-23)

- Added support for intermediate transaction points
- Added SFTP storage
- Added control on encrypted filenames wrapping
- Enhanced control on FTP passive mode.

## Version 7.1.10 (released on 2010-11-1)

- Fixed a bug on search window.

## Version 7.1.9 (released on 2010-10-17)

- Fixed a bug that could occur when checking recovered files
- Default zip compression level set to 4.

## Version 7.1.8 (released on 2010-9-29)

- Added control encoding option to FTP window
- Fixed a problem that prevented last modification time to be recovered on read-only files (windows specific)
- Faster and storage efficient 'archive check' feature
- Faster delta storage mode
- Added control on zip compression level
- Minor other bugfixes & enhancements.

## Version 7.1.7 (released on 2010-5-19)

- Minor GUI enhancements & bugfixes.

## Version 7.1.6 (released on 2009-12-7)

- Big files (over 2 GB) metadata are now properly handled
- More control has been added to file path naming conventions on Windows
- A bug that could occur when recovering single files in 'delta' mode has been fixed
- Configuration storage has been refactored and simplified
- 'Check' and 'Merge' features have been improved
- Better 'Configuration import' window
- 'Search' feature enhancements
- Minor user interface enhancements.

CAUTION : This version includes major modifications of Areca's backup configuration format.

## Version 7.1.5 (released on 2009-8-24)

- More secured configuration backup (Areca makes sure that the XML backup file is correctly written on the backup location)
- Command-line output in console has been reactivated
- Bugfix when forcing 'full backup' mode on delta targets
- Areca can now handle <null> extended attribute values
- Progress bars enhancements
- Post-processors enhancements.

## Version 7.1.4 (released on 2009-8-2)

- Drag and Drop in sources configuration window
- Bugfix when recovering a specific version of a file (the latest version was always recovered)
- Minor enhancements.

## Version 7.1.3 (released on 2009-7-12)

- Regular expression filter bug fix
- Minor XML configuration bug fix
- Recovery bug fix.

## Version 7.1.2 (released on 2009-6-12)

- New regular expression filters options
- Backup report enhancements
- Post processors enhancements
- Minor bugfixes.

## Version 7.1.1 (released on 2009-5-26)

- Ability to view files contained in your archives using the default applications configured on your system
- Post-backup actions enhancements
- Minor other enhancements.

## Version 7.1 (released on 2009-4-21)

- Minor error messages enhancements
- Named pipes are now properly handled by Areca
- Better 'Check Archive' feature.

## Version 7.0.9 (released on 2009-4-3)

- Synchronous backup mode available from command-line interface
- The 256 characters limit on file path is disabled if the Java version is over 1.6
- Enhanced FTP connexions management.

## Version 7.0.8 (released on 2009-3-21)

- Logical view bug fix
- Image backups bug fix.

## Version 7.0.7 (released on 2009-3-17)

- Regex file filter bug fix
- Added 'encrypt file names' to the 'missing encryption data' window
- File handle cleanup.

## Version 7.0.6 (released on 2009-3-7)

- Delta backup bug fix.

## Version 7.0.5 (released on 2009-3-5)

- ACL serialization bug fix in Java 1.5
- Minor recovery bug fix.

## Version 7.0 (released on 2009-2-15)

- ACL and extended attributes support for Linux
- New 'archive check' feature
- Heavy memory management refactoring.

CAUTION : this version is NOT backward compatible with previous versions of Areca.

## Version 6.1 (released on 2008-12-2)

- Backup pause implementation
- Plugin API enhancement
- Configurable compression level
- Encryption refactoring

CAUTION : This refactoring is NOT backward-compatible. This means that this new version will NOT be able to read archives encrypted with previous versions of Areca.

## Version 6.0.7 (released on 2008-4-28)

- log bug fix.

## Version 6.0.6 (released on 2008-4-27)

- Various bug fixes.

## Version 6.0.5 (released on 2008-4-10)

- Log bug fix.

## Version 6.0.4 (released on 2008-4-9)

- Archive trace backward-compatibility bug fix.

## Version 6.0.3 (released on 2008-4-8)

- Zip Bugfix.

## Version 6.0.2 (released on 2008-4-7)

- Bugfixes
- Archive trace storage refactoring
- translation updates.

## Version 6.0.1 (released on 2008-3-30)

- Minor bugfixes
- Minor encryption and log enhancements.

## Version 6.0 (released on 2008-3-26)

- Delta storage implementation
- Chinese (traditional), Swedish and Danish translations
- Minor bugfixes and enhancements.

## Version 5.5.7 (released on 2008-1-25)

- Minor bugfixes
- Japanese, Chinese and Spanish translations.

## Version 5.5.6 (released on 2008-1-15)

- Filename size checks were added
- Minor enhancements.

## Version 5.5.5 (released on 2008-1-13)

- Encryption bug fix
- Zip64 bug fix
- Minor enhancements.

## Version 5.5.4 (released on 2007-12-20)

- Recovery bug fix.

## Version 5.5.3 (released on 2007-12-2)

- Full and differential backups support.

## Version 5.5.2 (released on 2007-11-9)

- Compression enhancements
- Hungarian translation
- Minor bugfixes.

## Version 5.5.1 (released on 2007-10-27)

- Recovery optimization
- Backup strategy wizard.

## Version 5.5 (released on 2007-10-14)

- Merge processor enhancements
- New delete processor
- Encryption enhancements
- Bug Fixes.

## Version 5.4 (released on 2007-10-8)

- Pre-processors
- Filter enhancements
- Minor bug fixes.

## Version 5.3.5 (released on 2007-9-21)

- Mail send processor enhancement
- Minor bug fixes.

## Version 5.3.4 (released on 2007-9-14)

- External decompression tool
- GUI enhancements
- Java properties override feature.

## Version 5.3.3 (released on 2007-9-1)

- FTP data cache
- Some bug fixes.

## Version 5.3.2 (released on 2007-8-24)

- External decryption tool
- New archive merge option.

## Version 5.3.1 (released on 2007-8-19)

- Faster file copy
- Zip options enhancements
- Filters enhancements
- Multiple source directories support.

## Version 5.3 (released on 2007-8-11)

- Dutch and Italian translation
- Zip split support
- Symbolic links support.

## Version 5.2.1 (released on 2007-7-22)

- Permission management bug fix
- Post-processors enhancements
- Locked file filter enhancements
- FTP enhancements.

## Version 5.2 (released on 2007-6-27)

- Zip64 bug fix
- Russian translation
- Mail reports enhancements
- Shell script dynamic parameters.

## Version 5.1 (released on 2007-6-17)

- Better multithreading management
- User interface enhancements (Archive's files edition feature & improved target deletion).

## Version 5.0.2 (released on 2007-6-5)

- Bug fix : directories starting with '#' were not processed properly.

## Version 5.0.1 (released on 2007-5-30)

- UTF-8 is used for all metadata files
- Backup shortcut implementation
- Search-Window enhancements.

## Version 5.0 (released on 2007-5-12)

- Graphical user interface refactoring (SWT is used instead of Swing).

## Version 4.5.2 (released on 2007-5-1)

- Some bug fixes
- Target duplication feature.

## Version 4.5.1 (released on 2007-4-29)

- FTPs (implicit/explicit SSL/TLS) support.

## Version 4.5 (released on 2007-4-11)

- Zip64 compression
- FTP support
- Improved archives controls
- XML configuration backup
- Log window.

## Version 4.2.3 (released on 2007-3-7)

- Some more bug fixes.

## Version 4.2.2 (released on 2007-3-6)

- Various bug fixes.

## Version 4.2.1 (released on 2007-3-3)

- Memory optimizations and improved controls on zip32 archive size.

## Version 4.2 (released on 2007-2-7)

- File attributes recovery (date & permissions)
- Authenticated SMTP support
- Encrypted targets bug fix.

## Version 4.1.7 (released on 2007-2-4)

- Merge bug fix.

## Version 4.1.6 (released on 2007-1-30)

- Memory optimizations
- Recovery enhancement (File last modification date is now preserved).

## Version 4.1.5 (released on 2006-12-1)

- Stronger encryption
- Some bug fixes (shell post-processors).

## Version 4.1 (released on 2006-11-22)

- Search feature
- Toolbar
- Minor enhancements & Bug fixes.

## Version 4.0.5 (released on 2006-11-10)

- Mail encoding bug fix
- Workspace backup feature.

## Version 4.0 (released on 2006-11-4)

- Post-processors and user preferences
- Various optimizations.

## Version 3.5.1 (released on 2006-10-21)

- Minor ZIP bug fix (This bug occurred on empty archives).

## Version 3.5 (released on 2006-10-10)

- New file filters
- Major archive medium refactoring.

## Version 3.4 (released on 2006-9-12)

- Empty directory tracking
- Archive mediums refactoring
- File size & date filters.

## Version 3.3.1 (released on 2006-9-9)

- Internationalization bug fix.

## Version 3.3 (released on 2006-9-7)

- Internationalization (by Stephane Brunel), UTF8 support and various improvements.

## Version 3.2.7 (released on 2006-8-30)

- Backup simulation bug fix.

## Version 3.2.6 (released on 2006-8-28)

- Command line interface improvements - Backup pre-check improvements.

## Version 3.2.5 (released on 2006-8-24)

- Command line interface bug fix.

## Version 3.2.4 (released on 2006-8-4)

- Added AES encryption algorithm
- Minor GUI enhancements.

## Version 3.2.3 (released on 2006-8-2)

- Minor bug fix.

## Version 3.2.2 (released on 2006-7-27)

- Added a check module for new releases.

## Version 3.2.1 (released on 2006-7-20)

- Target history enhancement.

## Version 3.2 (released on 2006-7-18)

- Implemented process cancellation
- added target Indicators.

## Version 3.1.5 (released on 2006-7-12)

- Added Java version check on startup and minor GUI enhancements.

## Version 3.1.4 (released on 2006-7-8)

- Support for deep directories encryption on Windows (Windows limits paths length to 256 characters).

## Version 3.1.3 (released on 2006-7-6)

- Added references to the project's home page.

## Version 3.1.2 (released on 2006-7-2)

- Minor GUI correction (the 'Help' window didn't open correctly under Windows 2000.)

## Version 3.1.1 (released on 2006-6-12)

- Incremental storage mediums now check that the data have really changed before backup.

## Version 3.1 (released on 2006-5-21)

- Archive encryption implementation.

## Version 3.0 (released on 2006-5-7)

- Backup simulation and single file recovery implementation.

## Version 2.7 (released on 2006-4-15)

- Archive merge enhancement
- Archive deletion implementation.

## Version 2.6 (released on 2006-3-22)

- Archive detail window.

## Version 2.5 (released on 2006-3-9)

- Commit / Rollback implementation for backups and merges.

## Version 2.4 (released on 2006-2-6)

- Regex filters implementation.

## Version 2.3.1 (released on 2006-1-2)

- Backup monitoring enhancement.

## Version 2.3 (released on 2005-12-18)

- Backup / Merge / Recover pre-check improvements.

## Version 2.2.1 (released on 2005-12-8)

- Log history management.

## Version 2.2 (released on 2005-10-11)

- Manifest implementation.

## Version 2.1.2 (released on 2005-7-23)

- Context menus implementation.

## Version 2.1.1 (released on 2005-7-13)

- History improvement.

## Version 2.1 (released on 2005-7-11)

- Minor GUI enhancements. ('about' and 'help' dialogs)

## Version 2.0 (released on 2005-7-3)

- Group / Target edition windows.

## Version 1.2.2 (released on 2005-6-21)

- Minor GUI enhancements.

## Version 1.2.1 (released on 2005-6-6)

- History implementation.

## Version 1.2 (released on 2005-6-1)

- New storage mediums
- XML processing improvement.

## Version 1.1 (released on 2005-5-15)

- GUI implementation

## Version 1.0 (released on 2005-5-1)

- Backup engine implementation.


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI] | [archive.org]

[Top]: #areca-backup---versions-history "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: https://areca-backup.org/history.php "Visit the original resource"
[archive.org]: http://web.archive.org/web/20150901140137/http://www.areca-backup.org/history.php "Visit the original resource at archive.org"