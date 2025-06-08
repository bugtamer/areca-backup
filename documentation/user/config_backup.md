# Areca Backup - Config backup

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


> Areca stores backup copies of your configuration files on your backup location.


Areca stores backup copies of your configuration files on your backup location.
These copies are stored in a subdirectory named "areca_config_backup", as ".bcfg" files.

They can be opened directly in Areca (by using the "open workspace" feature).
Nevertheless, Areca needs to make sure that these files won't be inadvertently modified or corrupted.
That's why target edition are not permitted in backup workspaces.

If you want to modify these backup copies, you must first open a new workspace,
and then import these files into this new workspace.
Areca will create copies of these backup files, which will be editable. 


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI]

[Top]: #areca-backup---config-backup "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: http://www.areca-backup.org/config_backup.php "Visit the original resource"