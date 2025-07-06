# Areca Backup - Building

The building pipeline is initially defined by [`build.xml`](../../build.xml) and is executed by Apache Ant.

- See [Development environment](development-environment.md).
- See [Testing](testing.md).
- See how to [release a new version](./release-version-checklist.md) before building.
- See [Change log](history.md).


## Supported platforms

- Execute the following commands from the project root folder (`areca-backup/`).
- `ant` builds a release bundle for the current platform if it is supported.
- Run the `releases.sh` script to build all bundles.
- The release bundle output folder is `areca-backup/releases/`.

### Windows platform

- `ant windows-x86-64`
- `ant windows-x86-32`

### Linux platform

- `ant linux-x86-64`
- `ant linux-x86-32`


## Build Areca to support debug mode

[Debug mode](debugging.md) requires compiling Areca specifically to enable this development feature.

Add the `-Ddebug=on` argument to the above compilation commands.
`debug` can be set to either `on` or `off` (default value) but you do not need to add `-Ddebug=off` to the `off` setting;

**Note:**
You can only use breakpoints if you have already compiled Areca with `-Ddebug=on`;
otherwise, you may only be able to catch exceptions.

### Windows platform

- `ant windows-x86-64 -Ddebug=on`
- `ant windows-x86-32 -Ddebug=on`

### Linux platform

- `ant linux-x86-64 -Ddebug=on`
- `ant linux-x86-32 -Ddebug=on`


## Troubleshooting

### `jni.h` was not found

It caused by missing or wrong setting of `jni.h.dir` and `jni_md.h.dir`
which are files that are provided by the JDK.

Check: `locate-jni-headers.sh`
