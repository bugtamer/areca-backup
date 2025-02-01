# Areca Backup - ACL Troubleshooting

Launch time issues:

```
openjdk version "x.y.z" yyyy-mm-dd LTS
OpenJDK Runtime Environment Vendor-x.y.z (build x.y.z-LTS)
OpenJDK 64-Bit Server VM Vendor-x.y.z (build x.y.z-LTS, mixed mode, sharing)
/path-to/java/bin/java: symbol lookup error: /path-to/areca-backup/lib/libarecafs.so: undefined symbol: acl_get_file
```


Runtime issues (Log tab):

```log
[...]
yy-mm-dd hh:mm - WARNING - com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor cannot be used on this system. Got the following error : "java.lang.UnsatisfiedLinkError : no acl in java.library.path: /path-to/areca-backup/lib/:/lib64:/lib:/usr/lib64:/usr/lib:/usr/lib64/java:/usr/lib/java:/usr/lib64/jni:/usr/lib/jni:/usr/share/java"
yy-mm-dd hh:mm - WARNING - You should check that the 'acl' package is properly deployed on your computer.
In most cases, there should be a 'libacl.so' file or symbolic link somewhere on your filesystem.
If it is not the case, you should check for a 'libacl.<some version number>.so' file and create a symbolic link named 'libacl.so' on it.
If no such file can be found, just install the 'acl' package using your standard package manager.
yy-mm-dd hh:mm - WARNING - [com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor] not validated. The default metadata accessor will be used instead. See FAQ for more informations about file metadata management. (ACL & extended attributes)
[...]
yy-mm-dd hh:mm - INFO - ACL support : no
yy-mm-dd hh:mm - INFO - Extended attributes support : no
[...]
```


ACL support is a platform specific feature.
The libarecafs.so gives this support to Areca.
You may need to compile the libarecafs.so with the libraries of your system.
In order to do so, you need:
  - JDK (Java Development Kit) that can be embedded in "areca-Backup/jdk" to simplify paths.
  - gcc (compiler)



## Check you have already enabled the ACL support

- Edit "areca-Backup/config/fwk.properties"
- The following line must be present (without any '#' character before this line):
  filesystem.accessor.impl = com.myJava.file.metadata.posix.jni.JNIMetaDataAccessor



## How to (re)compile libarecafs.so

### Create C header from JDK 9 or lower versions

areca-Backup/jni $ javah -jni -d . -classpath ../lib/areca.jar com.myJava.file.metadata.posix.jni.wrapper.FileAccessWrapper

### Create C header from JDK 10 or higher versions

areca-Backup/jni $ javac -h . -classpath ../lib/areca.jar -sourcepath ../src/com/myJava/file/metadata/posix/jni/wrapper -d ../src/com/myJava/file/metadata/posix/jni/wrapper ../src/com/myJava/file/metadata/posix/jni/wrapper/FileAccessWrapper.java

### Compile the libarecafs.so

Locate 'jdk/include' folder (that contains 'jni.h' and 'linux/jni_md.h') in your system:

H=/path-to/jdk/include

Replace the '${H}' with the value of 'H' into the end of the following command line (2 replacements):

areca-Backup/jni $ gcc -c -fPIC -lacl com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.c -o com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o -I${H} -I${H}/linux
areca-Backup/jni $ gcc -shared -Wl,-soname,libarecafs.so -o libarecafs.so com_myJava_file_metadata_posix_jni_wrapper_FileAccessWrapper.o -lacl

### Move the libarecafs.so to its destination folder

areca-Backup/jni $ mv --interactive libarecafs.so ../lib/libarecafs.so



## How to create a symbolic link to allow Areca to find the libacl.so

areca-Backup $ ln -s </path-to/libacl.so> ./lib/libacl.so

Example:

```
$ ldconfig -p | grep libacl
        libacl.so.1 (libc6,x86-64) => /lib/x86_64-linux-gnu/libacl.so.1
        libacl.so (libc6,x86-64) => /lib/x86_64-linux-gnu/libacl.so
```

So the command line in this example will be:

areca-Backup $ ln -s /lib/x86_64-linux-gnu/libacl.so ./lib/libacl.so
