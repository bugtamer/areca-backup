# Areca Backup - Dependencies

See [Apache Ivy](https://ant.apache.org/ivy/) project configuration files:
[`ivy.xml`](../../ivy.xml) and [`ivysettings.xml`](../../ivysettings.xml).


## Dependencies for Areca Backup 7.5

A copy of the original dependencies (`*.jar`) are located in `building/legacy/lib`.

The "Match" column means whether the hash of the retrieved version matches that of Areca "Backup 7.5".

| org                                       | name                                 | rev                    | Match  | MD5                                | SHA1                                       |
| ----------------------------------------- | ------------------------------------ | ---------------------- | ------ | ---------------------------------- | ------------------------------------------ |
| `oro`                                     | `oro`                                | `2.0.8`                | Yes    | `42e940d5d2d822f4dc04c65053e630ab` | `5592374f834645c4ae250f4c9fbb314c9369d698` |
| `commons-net`                             | `commons-net`                        | `1.4.1`                | Yes    | `365c9a26e81b212de0553fbed10452cc` | `abb932adb2c10790c1eaa4365d3ac2a1ac7cb700` |
| `commons-codec`                           | `commons-codec`                      | `1.4`                  | Yes    | `82b899580da472be37055da949b731fa` | `4216af16d38465bbab0f3dff8efa14204f7a399a` |
| `javax.mail`                              | `mail`                               | `1.4`                  | **NO** | `0bacd591fbd2ce1a5e0d0062be15ff8e` | `c451d58874f7030f84b2db22bd5ea12a1c35517c` |
| `org.eclipse.jface`                       | `org.eclipse.jface`                  | `3.2.0-I20060605-1400` | Yes    | `c3fdba492479a8f10408bc538ab16e4f` | `abdd6681301e014582ff20644cfeece78b8d348a` |
| `org.eclipse.core`                        | `commands`                           | `3.2.0-I20060605-1400` | **NO** | `11efeb1534fd7d786f1a7cc7e206be6b` | `fe2f0edb5cc23ddc53e41abf062aa3fdb5a66dec` |
| `org.eclipse.equinox`                     | `common`                             | `3.2.0-v20060603`      | **NO** | `8ab087f48815d80fe81a1e0cc8ad3345` | `7f6049c3983bc8b07fd37f5aa3796d8d49786b9b` |
| `java.security.policies.crypto.unlimited` | `local_policy`                       | `1.6`                  | **NO** | `bc84dcde732d9392679f65fd57aded63` | `06f24175eddc114755281f96095e06481c5be30e` |
| `javax.activation`                        | `activation`                         | `1.1`                  | **NO** | `d5dd655f6056a72116f6a0666745a386` | `cbcabcb06fee4a1dc158a42f49c66fe213d0ca72` |
| `jsch` ?                                  | `jsch`                               | `0.1.44` ?             | **NO** | `1583a80bde1f8920de02cfa4080a636d` | `d7b43337141ec800f5cbb60c602e5125348fc9f8` |
| `org.eclipse.swt`                         | `org.eclipse.swt.win32.win32.x86_64` | `4.332`                | **NO** | `1699da5bf72ed716e4c3892a912229d3` | `94fc38c538f9bf264d6a9078c711f122f79acd63` |
| `org.eclipse.swt`                         | `org.eclipse.swt.win32.win32.x86`    | `4.332`                | **NO** | `02c558b5e1efccb88f88609e9e7e9208` | `a5d40ef3582cb71631d493cf71d5dd559390f640` |
| `org.eclipse.swt`                         | `org.eclipse.swt.gtk.linux.x86_64`   | `4.332`                | **NO** | `983561cf5c4bbfb75b9627f9d5329619` | `2e9dff738c66efa2a31b048ac608e454715cf3eb` |
| `org.eclipse.swt`                         | `org.eclipse.swt.gtk.linux.x86`      | `4.332`                | **NO** | `2ee89780b8d9b68990aa6e53552242c2` | `507716ff7e80eb4a93b5a336f07c75c813008b79` |

Dependency search engine:

- **https://mvnrepository.com/artifact/`[org]`/`[name]`/`[rev]`**
- **https://mvnrepository.com/artifact/`[org]`/`[name]`**
- **https://mvnrepository.com/artifact/`[org]`**

Repositories that:

- https://mvnrepository.com/artifact/oro/oro/2.0.8
- https://mvnrepository.com/artifact/commons-net/commons-net/1.4.1
- https://mvnrepository.com/artifact/commons-codec/commons-codec/1.4
- https://mvnrepository.com/artifact/javax.mail/mail/1.4
- https://mvnrepository.com/artifact/org.eclipse.jface/org.eclipse.jface/3.2.0-I20060605-1400
- https://mvnrepository.com/artifact/org.eclipse.core/commands/3.2.0-I20060605-1400
- https://mvnrepository.com/artifact/org.eclipse.equinox/common/3.2.0-v20060603
- https://mvnrepository.com/artifact/java.security.policies.crypto.unlimited/local_policy/1.6
- https://mvnrepository.com/artifact/javax.activation/activation
- https://mvnrepository.com/artifact/jsch/jsch/0.1.44
- https://mvnrepository.com/artifact/org.eclipse.swt/org.eclipse.swt.win32.win32.x86_64/4.3
- https://mvnrepository.com/artifact/org.eclipse.swt/org.eclipse.swt.win32.win32.x86/4.3
- https://mvnrepository.com/artifact/org.eclipse.swt/org.eclipse.swt.gtk.linux.x86_64/4.3
- https://mvnrepository.com/artifact/org.eclipse.swt/org.eclipse.swt.gtk.linux.x86/4.3

### Unknown dependency versions

- **`activation`**

- None of the versions of **`JSch`**,
  from `0.1.14` (2004-03-24) to `0.1.55` (2018-11-26),
  match the version of Areca Backup 7.5 in
  https://sourceforge.net/projects/jsch/files/jsch.jar/.
  - 2011-01-23 [Areca Backup 7.2](https://sourceforge.net/projects/areca/files/areca-stable/areca-7.2/): first time Areca uses JSch.
  - 2015-08-26 [Areca Backup 7.5](https://sourceforge.net/projects/areca/files/areca-stable/areca-7.5/): latest version of Areca.
  - Areca 7.2 and 7.5 both share the same JSch version
  - So `JSch` `0.1.44` (2010-11-02) is the closest version to Areca 7.2 or 7.5
