# Areca Backup - FAQ

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


> Please read Areca's FAQ before submitting issues or feature requests !


## Features :

### How to automate backups ?

You can use Areca's command-line interface to create backup scripts (*.bat or *.sh scripts), and schedule their execution with Linux's crontab or Windows' scheduler.

Windows scheduling : http://support.microsoft.com/kb/308569

Linux scheduling : http://en.wikipedia.org/wiki/Cron


### Does Areca support Volume Shadow Copy Service (VSS) on Windows ?

No, Areca doesn't support VSS, but you can use the ArecaVSS plugin ([www.arecavss.com](../arecavss.com/README.md)), which will add VSS support to Areca.

This will allow Areca to read and store files that are locked by the system or programs (such as outlook *.pst files)


### How does Areca detect modified or new files when incremental backups are chosen ?

Areca uses the file's size and last modification time to detect modified files. If one of these attributes is modified (whatever its value is), the file is flagged as modified.

Since v7.2.17, Areca can also inspect the file's content to detect modifications of its content (which is much slower than detection based on attributes)


### Can Areca store backups on FTP servers ?

Yes - since v4.5, Areca can store your files on FTP servers.

If FTP storage is used, it is highly advisable to compress your archives. (You can use zip64 or standard zip compression)

It is also advisable to use the "FTP Test" feature (which can be found on the FTP parameters window) to check that your FTP server is compatible with Areca.


### Can Areca store backups on FTPs (File Transfer Protocol over SSL/TLS) servers ?

Yes - since v4.5.1, Areca can store files on FTPs servers.

Areca supports SSL and TLS, as well as implicit and explicit modes.


### Can Areca store backups on SFTP servers ?

Yes - since v7.2, Areca can store files on SFTP servers.


### Can Areca burn CDs/DVDs ?

No, Areca can only store your backups on your file system (ie local or remote directories)


### When can I use compressed targets ?

You can use standard zip compression if your archives will be smaller than 4GB. For bigger archives, use zip64 compression.

Of course, it is useless to use compression when your source files are already compressed (for instance JPG, MP3, AVI files) : No space will be gained and it will result in unnecessary time consumption during backup.


### Why does Areca not use zip encryption for encrypted compressed archives ?

Areca supports encryption, but it is implemented at the file system access level (ie it is not implemented as zip encryption). This allows to benefit from the same encryption layer, whether the target is compressed or not.

In other words, if you choose target encryption and compression, Areca will encrypt the archives exactly the same way as if you choose target encryption without compression.

---

## ACL and Extended attributes support :

### Does Areca support ACL and extended attributes ?

Yes : since version 7.0, Areca supports ACL and extended attributes on Linux.

This feature uses native code (unlike Areca's backup engine, which has been written in Java) and is currently only compiled for 32 bits Linux systems with glibc2.6.

<u>Note that this native code needs the "acl" library.</u>


### How can I compile Areca's library that handles ACL and extended attributes ?

That's quite simple : download Areca's sources on SourceForge (zip file available on the download page), uncompress it, open the "jni" directory and run the "compile.sh" script.

It will create a "libarecafs.so" file. Copy this file in Areca's "lib" directory.


### How can I activate ACL and extended attributes support ?

ACL and extended attributes support are not enabled by default. You have to modify your configuration to activate it.

Open the "fwk.properties" file which is located in Areca's configuration subdirectory and set the "filesystem.accessor.impl" to "com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor" (the default value is "com.myJava.file.metadata.posix.basic.DefaultMetaDataAccessor")

On startup, Areca displays some information about the "filesystem accessor" which is used (you should see something like "Loading configured file metadata accessor : [com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor]" in your log file or log tab)

---

### Problems :

### 'java.io.IOException: The system cannot find the file specified' on remote directories

This error can occur when you store your backups on a remote directory (over a network) which is not available (network error).


### I get errors when I try to store archives on a FTP server - Why ?

Areca needs extended accesses to the FTP storage directory in order to use it.

More precisely, the FTP user must be granted to following rights :
- Read / Write / Delete / Create / Rename on files
- List / Create / Delete / Rename on subdirectories

Areca has been tested with the following FTP servers :
- Linux : ProFTPD and VSFTPD
- Windows : FileZilla FTP Server, TypSoft FTP Server, Cerberus FTP Server and zFTP Server

Note that - depending on the FTP server configuration - file sizes can be restricted (for instance, files over 1GB won't be accepted)


### I get errors when trying to use FTP over SSL or SFTP - Why ?

Solution proposal (special thanks to "Mirkosoft") :

_"I could solve my issue with ftp timeouts: the problem was the firewall built into windows._

_(The following is valid for windows 7 ultimate on a 64 bit system. Not approved on other systems)_


_As mentioned, I'm using ftp over ssl._

_The windows firewall tries to perform stateful inspection on ftp traffic. Using ssl, this traffic becomes unreadable to the firewall and thus the connection is interrupted after about 10 minutes or so._


_There are two possible solutions to this:_

_1. Disable the windows firewall completely.
Not recommend for most users._

_2. Disable stateful inspection on ftp traffic using the command line.
This may be achieved by calling netsh advfirewall set global StatefulFtp disable_


_The state of ftp inspection may be checked by calling netsh advfirewall show global Statefulftp_

_This solves all of the mentioned timeout problems for me."_


---

[Top] | [Copyright (c) 2005-2015 Olivier PETRUCCI] | [archive.org]

[Top]: #areca-backup---faq "Go to top of the document"
[Copyright (c) 2005-2015 Olivier PETRUCCI]: https://areca-backup.org/faq.php "Visit the original resource"
[archive.org]: http://web.archive.org/web/20150912034048/http://www.areca-backup.org/faq.php "Visit the original resource at archive.org"