# Areca Backup - Launchers

## Context

Areca is a desktop application developed with Java technology.
Java requires specific information to know how to execute a program.
Launchers simplify the process of starting Areca for the user.

Areca can run in graphical mode or text mode.


## Binaries for Windows

Areca 7.5 binaries may not work with current versions of the `JRE` (or `JDK`).
New binaries (`areca.exe` and `areca_cl.exe`) can be generated from
[areca-backup-native](https://github.com/bugtamer/areca-backup-native) repo.


## Scripts for Windows

It might be a better option to use scripts instead of binaries.
For example, they are inspectable and customizable.

* [`areca.bat`](../../areca.bat) (GUI)
* [`areca_cl.bat`](../../areca_cl.bat) (TUI)
* [`areca_check_version.bat`](../../areca_check_version.bat) (check for new versions)
* [`areca_run.bat`](../../bin/areca_run.bat) (startup logic)
* The scripts that begin with `debug_` allow you to [debug Areca](debugging.md).


## Scripts for Linux

* [`areca.sh`](../../areca.sh) (GUI)
* [`areca_cl.sh`](../../bin/areca_cl.sh) (TUI)
* [`areca_check_version.sh`](../../areca_check_version.sh) (check for new versions)
* [`areca_run.sh`](../../bin/areca_run.sh) (startup logic)
* The scripts that begin with `debug_` allow you to [debug Areca](debugging.md).


## Requirements to Launch Areca

Either the `JRE` (Java Runtime Environment) or the `JDK` (Java Development Kit) is required.
[See Java providers](java-vendors.md).

You must call either `javaw` (GUI) or `java` (GUI or TUI) and provide the following details:

1. Initial RAM allocation (64 MB by default: `-Xms64m`).
2. Maximum assignable RAM (1024 MB by default: `-Xmx1024m`).
3. Classpath (`-cp "<CLASSPATH>"`), which includes paths to:
   * license file,
   * libraries (including `areca.jar` and the platform-specific [`SWT`](https://eclipse.dev/eclipse/swt/)),
   * translations, and
   * `fwk.properties`
4. Absolute path to Areca (`-Duser.dir="<ARECA_DIRECTORY>"`)
5. Path to dependencies (`-Djava.library.path="<LIBRARY_PATH>"`)
6. Java class that initializes Areca (e.g., GUI or TUI)
   `-Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader <FIRST_COMMAND_LINE_ARGUMENT>`
7. Command-line arguments, if any

   There is additional logic primarily aimed at configuring this data,
   such as locating a `JRE` or `JDK`.

   ```shell
   java
      -Xmx1024m
      -Xms64m
      -cp "<CLASSPATH>"
      -Duser.dir="<ARECA_DIRECTORY>"
      -Djava.library.path="<LIBRARY_PATH>"
      -Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader <INITIAL_JAVA_CLASS_ARGUMENT>
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
   ```

   to allow to attach an IDE's debugger to an already running instance of Areca you need to add this
   `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=<DEBUG_ADDRESS>:<DEBUG_PORT>`
   to the former code snippet so it would look like this

   ```shell
   java
      -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=<DEBUG_ADDRESS>:<DEBUG_PORT>
      -Xmx1024m
      -Xms64m
      -cp "<CLASSPATH>"
      -Duser.dir="<ARECA_DIRECTORY>"
      -Djava.library.path="<LIBRARY_PATH>"
      -Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader <INITIAL_JAVA_CLASS_ARGUMENT>
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
      "<OPTIONAL_COMMAND_LINE_ARGUMENT>"
   ```

   Note: Type the snippets on a single line,
   or add `^` (for Windows) or `\` (for Linux) to the end of each line except the last
   to split the command into multiple lines.

Keep the same changes and fixes in the following pair of files:
- `areca_run.*` and `debug_areca_run.*`
- `areca.*` and `debug_areca.*`
- `areca_cl.*` and  `debug_areca_cl.*`
`debug_` scripts only include additional logic for [debugging](debugging.md).

While the `areca_run.*` scripts contain this logic,
the `areca.*` and `areca_cl.*` scripts
pass any command-line arguments to `areca_run.*`
and specify the Java class to initialize Areca.

The binary launchers (`.exe`) do not include `areca_run.exe`,
as both `areca.exe` and `areca_cl.exe` already contain all the aforementioned logic,
and can also display an icon to represent them.
Scripts (`bat` and `sh`) do not display icons; to show an icon,
create a shortcut to them and associate an icon with it.


## Elevated privileges

Backup and recovery operations require sufficient permissions to complete.
These permissions do not have to be root or admin permissions, but they must be sufficient.

### Areca Log

Since version 8.2.5 Areca shows if it has detected that it is running with elevated privileges.
Example: `YY-MM-DD HH:MM - INFO - Elevated privileges : yes`.

### Windows

`areca.bat` Run as administrator

### Linux

`sudo ./areca.sh`

~~~terminal
ls: cannot access '/usr/java': No such file or directory
No valid JRE found in /usr/java.
~~~

If after that message Areca does not run, you can try:
- `sudo --preserve-env=PATH ./areca.sh`
  Consider, where appropriate, which environment variables of the user, who executes sudo, needs to pass to a "clean" root environment. 
  Consider whether it may pose a security problem in your case. 
  You can add more than one environment variable by separating them with commas (`,`).
- `sudo -E ./areca.sh`
  `-E` attempts to preserve the user's environment


## Relevant classes

- [ArecaClassLoader.java](../../src/com/application/areca/impl/tools/ArecaClassLoader.java)
- [Launcher.java](../../src/com/application/areca/launcher/gui/Launcher.java) for GUI
- [Launcher.java](../../src/com/application/areca/launcher/tui/Launcher.java) for TUI
- [VersionCheckLauncher.java](../../src/com/application/areca/version/VersionCheckLauncher.java)


## Related topics

- [Areca Backup native launchers](https://github.com/bugtamer/areca-backup-native) repo
- [Java providers](java-vendors.md) (`JRE` or `JDK`)
- [`SWT`](https://eclipse.dev/eclipse/swt/) platform-specific widgets for the Graphical User Interface
- [Building Areca](../developer/building.md)
- [Debugging](debugging.md)
