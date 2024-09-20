# Areca Backup - Building

The building pipeline is initially defined by [`build.xml`](../../build.xml) and is executed by Apache Ant.

- See [Development environment](development-environment.md).
- See [Testing](testing.md).
- See [Change log](documentation/developer/history.md).


## Supported platforms

- Execute the following commands from the project root folder (`areca-backup/`).
- `ant` builds a release bundle for the current platform if it is supported.
- The release bundle output folder is `areca-backup/releases/`.

### Windows platform

- `ant windows-x86-64`
- `ant windows-x86-32`

### Linux platform

- `ant linux-x86-64`
- `ant linux-x86-32`
