# Areca Backup - Regex

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


> You can use regular expressions to include / exclude source files in your backups.


## Regular expressions quick reference :

Here is a listing of the most popular regular expression patterns.

| Rule                                                     | Syntax                                                         |
|:---------------------------------------------------------|:---------------------------------------------------------------|
| Exactly one occurence of x                               | x                                                              |
| At least one occurence of x                              | x+                                                             |
| 0 or many occurences of x                                | x*                                                             |
| 0 or 1 occurence of x                                    | x?                                                             |
| Escape character                                         | \                                                              |
| Any single character                                     | .                                                              |
| One character in a predefined set                        | [_list-of-characters_] ... examples : [ABCD], [012345789]      |
| One character in a character range                       | [_character1_-_character2_] ... examples : [0-9], [a-zA-Z] ... |
| Any character except those contained in a predefined set | [^_list-of-characters_] ... examples : [^ABCD], [^0-9]         |
| Tab                                                      | \t                                                             |
| New line                                                 | \n                                                             |
| Carriage return                                          | \r                                                             |
| Beginning of the line                                    | ^                                                              |
| End of the line                                          | $                                                              |
  

## Examples :

| Example                           | Syntax         |
|:----------------------------------|:---------------|
| All filenames ending with ".html" | ^.*\.html$   |
| All filenames containing "client" | ^.*client.*$ |

Additional help can be found on [Areca's regular expressions wiki](../sourceforge.net/regex.md)


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI]

[Top]: #areca-backup---regex "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: https://areca-backup.org/regex.php "Visit the original resource"