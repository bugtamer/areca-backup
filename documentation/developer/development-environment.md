# Areca Backup - Development Environment

| Dependency                            | Recommended version | Purpose                                   | How to check if it is ready |
| ------------------------------------- | ------------------- | ----------------------------------------- | --------------------------- |
| JDK ([vendors](./java-vendors.md))    | `8` LTS or higher   | Runtime environment for Ant and Areca     | $ `javac -version`          |
| [Apache Ant](https://ant.apache.org/) | `1.10.x` or higher  | Building tool (compiles native launchers) | $ `ant -version`            |
| [Git](https://git-scm.com/)           | `2.x.x` or higher   | Manage areca and launcher repos           | $ `git --version`           |
| [Rust](https://www.rust-lang.org/)    | `1.x.x` or higher   | Rust language to build native launchers   | $ `rustc --version`         |

- See [Areca Backup native launchers](https://github.com/bugtamer/areca-backup-native) repo.


## How to make Git ignores executable bit of files among filesystems

See [git core.fileMode](https://git-scm.com/docs/git-config#Documentation/git-config.txt-corefileMode)

`git config core.fileMode false`


## Required charset encoding

`ISO-8859-1`


### Visual Studio Code

Menu `File` > submenu `Preferences` > `Settings` option > `Workspace` >
`Text Editor` > `Files` > `Encoding` > `ISO-8859-1`

Or edit `areca-backup/.vscode/settings.json`:

```JSON
{
  "files.encoding": "iso88591"
}
```

### IntelliJ IDEA

[File Encodings](https://www.jetbrains.com/help/idea/settings-file-encodings.html)

### Eclipse

Menu `Window` > `Preferences` option > `General` > `Workspace`,
set `Text file encoding` to `Other:` and choose `ISO-8859-1`.
