# Areca Backup - Plugins

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


> Use plugins to add extra features to Areca Backup, like SFTP or VSS support.


## Available plugins :

| Plugin                                | Download                                      | Description                                                                                                                                                                                            |
|---------------------------------------|-----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [ArecaVSS](../arecavss.com/README.md) | [www.arecavss.com](../arecavss.com/README.md) | Volume Shadow Copy Services (VSS) Support <br> By invoking VSS services, Areca will be able to backup files that are opened or locked by the system or other programs (for instance Outlook PST files) |
| FTP/FTPs                              | Included in Areca-Backup                      | FTP/FTPs Server Support <br> This plugins adds the ability to store archives on remote servers, by using the FTP or FTPs protocol.                                                                     |
| SFTP                                  | Included in Areca-Backup                      | SFTP Server Support <br> This plugins adds the ability to store archives on remote servers, by using the SFTP protocol.                                                                                |


Note : If you want to implement your ow plugin, Areca's plugin API documentation can be found [here](./documents/areca_plugins_documentation.pdf)


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI] | [archive.org]

[Top]: #areca-backup---plugins "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: https://areca-backup.org/plugin_list.php "Visit the original resource"
[archive.org]: http://web.archive.org/web/20150912034048/http://www.areca-backup.org/plugin_list.php "Visit the original resource at archive.org"