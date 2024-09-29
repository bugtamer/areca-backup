# Areca Backup - Translations

| About Areca                   | End user documentation            | Technical informations                        |
|-------------------------------|-----------------------------------|-----------------------------------------------|
| [Home](README.md)             | [Plugins](plugin_list.md)         | [Regular expressions](regex.md)               |
| [Features](features.md)       | [Versions history](history.md)    | [Translations](documentation.md#translations) |
| [Plugins](plugin_list.md)     | [Tutorial](tutorial.md)           | [Translations web page](translations.md)      |
| [Screenshots](screenshots.md) | [User's manual](documentation.md) | [Config backup](config_backup.md)             |
| [Download]                    | [FAQ](faq.md)                     |                                               |
| [Bug & feature requests]      | [Support & Contact](support.md)   |                                               |
| [Forums]                      |                                   |                                               |

[Download]: https://sourceforge.net/projects/areca/files/areca-stable/
[Bug & feature requests]: https://sourceforge.net/p/areca/_list/tickets?source=navbar
[Forums]: https://sourceforge.net/projects/areca/forums


## Welcome to Areca's translation web page.

This page allows you to update Areca's translation files. Click on one of the links below to update the translation.

You will find the missing labels grouped by language on the [Missing Labels](https://web.archive.org/web/20151012065719/http://www.areca-backup.org/arcwk/index.php?title=Missing_Labels) page.
You may also find some useful informations on how translations are managed [here](https://web.archive.org/web/20151012065719/http://www.areca-backup.org/arcwk/index.php?title=Translation_howto).

Don't forget to update the "Last translated version" column in the table below once you've saved your changes.
If you want to be notified of translation requests when new versions of Areca are released, please add your email in the "Translators" column bellow or send me an email (aventin at users dot sourceforge dot net)


<u>**Important :**</u><br>
<u>**Don't forget to keep the `<pre>` (at the beginning of the translation page) and `</pre>` (at the end of the translation page) tags (they prevent MediaWiki from parsing the translation's content)**</u>


| Language | Last translated version | Translators                                     |
| -------- | ----------------------- | ----------------------------------------------- |
| cs       | 7.4.3                   | miloslav dot thon at gmail dot com              |
| de       | 7.4.4                   | weakmum at gmx dot de                           |
| en       | 7.4.4                   | aventin at users dot sourceforge dot net        |
| es       | 7.3.6                   | lucasvieites at gmail dot com                   |
| fr       | 7.4.4                   | aventin at users dot sourceforge dot net        |
| hu       | 7.4.3                   | SanskritFritz at gmail dot com                  |
| cn       | 7.4.3                   | slreycn at gmail dot com                        |
| ru       | 7.4                     | pbtsrc at ya dot ru                             |
| it       | 7.4.4                   | danielecortesi at users dot sourceforge dot net |
| pt       | 7.4.2                   | palbuquerque at gmail dot com                   |
| br       | 7.4.4                   | joao dot cormick at gmail dot com               |
| da       | 6.0.2                   | *! TRANSLATOR WANTED !*                         |
| ja       | 6.0                     | *! TRANSLATOR WANTED !*                         |
| nl       | 7.1.7                   | *! TRANSLATOR WANTED !*                         |
| sv       | 7.1.8                   | *! TRANSLATOR WANTED !*                         |
| tw       | 6.0.2                   | *! TRANSLATOR WANTED !*                         |


## How to translate Areca

If you want to add a new language :

- First, send me (aventin at users dot sourceforge dot net) an email with your SourceForge account so I can add you to Areca's wiki editors list.
- Once you have been granted "editor" access, make a copy of the english translation ("translations/resources_en.properties" file) and name it after the language you want to add ("de" for german, "it" for italian, ...)
- Once your translation is completed, add it to this wiki (don't forget to add your language and email to the table above) 



VERY IMPORTANT : The content of the translations file MUST be encoded using the ISO-8859-1 encoding. Non-compatible characters (for instance chinese characters) must be unicode-encoded (using a \u escape character).

You can ensure this by using one of the following translation tools :

- "Zaval Java Resource Editor", available at http://www.zaval.org/products/jrc-editor/download/index.html
- "Eclipse ResourceBundle Editor Plugin", available at [http://www.resourcebundleeditor.com/wiki/Download](https://web.archive.org/web/20151012065719/http://www.resourcebundleeditor.com/wiki/Download)
- "PropEdit", available at http://sourceforge.jp/projects/propedit/
- "Open Language Tools", available at [https://open-language-tools.dev.java.net/](https://web.archive.org/web/20151012065719/https://open-language-tools.dev.java.net/)

NOTE: If you use a plain text editor (Notepad or Gedit) you can use the "native2ascii" command provided with Sun's JDK to convert UTF-8 files to ASCII.


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI] | [archive.org]

[Top]: #areca-backup---translations "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: https://areca-backup.org "Visit the original resource"
[archive.org]: https://web.archive.org/web/20151012065719/http://www.areca-backup.org/arcwk/index.php?title=Translations "Visit the original resource at archive.org"