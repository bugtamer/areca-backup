# Areca Backup - Launchers

## Context

Areca is a desktop application developed with Java technology.
Java requires specific information to know how to execute a program.
Launchers simplify the process of starting Areca for the user.

Areca can run in graphical mode or text mode.


## Requirements to Launch Areca

Either the `JRE` (Java Runtime Environment) or the `JDK` (Java Development Kit) is required.
[See Java providers](java-vendors.md).

You must call either `javaw` or `java` and provide the following:

1. Initial RAM allocation (64 MB by default).
2. Maximum assignable RAM (1024 MB by default).
3. Classpath, which includes paths to:
   * license file,
   * libraries (including `areca.jar` and the platform-specific [`SWT`](https://eclipse.dev/eclipse/swt/)),
   * translations, and
   * `fwk.properties`
4. Absolute path to Areca
5. Path to dependencies
6. Java class that initializes Areca (e.g., GUI or TUI)
7. Command-line arguments, if any

While the `areca_run.*` scripts  contain this logic,
the `areca.*` and `areca_cl.*` scripts pass any command-line arguments to `areca_run.*`,
and specify the Java class to initialize Areca. Note: `*` means `bat` or `sh`.

There is additional logic primarily aimed at configuring this data,
such as locating a `JRE` or `JDK`.

The binary launchers (`.exe`) do not include `areca_run.exe`,
as both `areca.exe` and `areca_cl.exe` already contain all the aforementioned logic,
and can also display an icon to represent them.
Scripts (`bat` and `sh`) do not display icons; to show an icon, create a shortcut to them and associate an icon with it.


## Binaries for Windows

Areca 7.5 binaries may not work with current versions of the JRE (or JDK).
New binaries (`areca.exe` and `areca_cl.exe`) can be generated from
[areca-backup-native](https://github.com/bugtamer/areca-backup-native) repo.


## Scripts for Windows

* [`areca.bat`](../../areca.bat) (GUI)
* [`areca_cl.bat`](../../areca_cl.bat) (TUI)
* [`areca_check_version.bat`](../../areca_check_version.bat) (check for new versions)
* [`areca_run.bat`](../../bin/areca_run.bat) (startup logic)


## Scripts for Linux

* [`areca.sh`](../../areca.sh) (GUI)
* [`areca_cl.sh`](../../bin/areca_cl.sh) (TUI)
* [`areca_check_version.sh`](../../areca_check_version.sh) (check for new versions)
* [`areca_run.sh`](../../bin/areca_run.sh) (startup logic)
