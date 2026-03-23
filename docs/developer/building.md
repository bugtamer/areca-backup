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
- Some builds have not yet been tested to see if they run properly.
- See [`ivy.xml`](../../ivy.xml).

### Windows platform

- `ant windows-x86-64`
- `ant windows-x86-32`

### Linux platform

- `ant linux-x86-64`
- `ant linux-x86-32`
- `ant linux-ppc64`  (untested)
- `ant linux-ppc`    (untested)
- `ant linux-s390x`  (untested)
- `ant linux-s390`   (untested)

### macOS platform

- `ant macos-x86-64` (untested)
- `ant macos-x86-32` (untested)

### Solaris platform

- `ant solaris-x86-32` (untested)
- `ant solaris-sparc`  (untested)

### AIX platform

- `ant solaris-aix-ppc64` (untested)
- `ant solaris-aix-ppc`   (untested)

### HP-UX platform

- `ant hpux-x86-64` (untested)


## Build Areca to support debug mode

[Debug mode](debugging.md) requires compiling Areca specifically to enable this development feature.

Add the `-Ddebug=on` argument to the above compilation commands (e.g. `ant linux-x86-64 -Ddebug=on`).
`debug` can be set to either `on` or `off` (default value) but you do not need to add `-Ddebug=off` to the `off` setting;

**Note:**
You can only use breakpoints if you have already compiled Areca with `-Ddebug=on`;
otherwise, you may only be able to catch exceptions.


## Troubleshooting

### `jni.h` was not found

It caused by missing or wrong setting of `jni.h.dir` and `jni_md.h.dir`
which are files that are provided by the JDK.

Check: `locate-jni-headers.sh`
