# Areca Backup - Release version checklist

*Check out previous commits to get an overview of what a new release entails.*

The following steps describe how to release a new version (e.g. `8.0.0`),
for instance, from the `develop` branch.


## Sematic versioning

What `<major>.<minor>.<patch>` means in a specific Areca version (`8.0.0`):
- `major` (a major change or) might contain potencial or expects breaking changes.
- `minor` adds new features with expected backguard compatibility.
- `patch` fixes issues with expected backguard compatibility.


## Steps to build a new release bundle

- Modify the source code.
- [Test](testing.md) the changes.

1. Add/Update credit to the **author** and **copyright** in each modified file
   if the file already had that info and was originally created by Olivier PETRUCCI.

2. Update [**AUTHORS**](../../AUTHORS) and [**COPYING**](../../COPYING) files
   if any file was modified by a new developer.

3. Add a new entry to [history.md](../../documentation/developer/history.md):
   > `history.md` shows the change log (online).
   ```markdown
   ## Version 8.0.0 (released on 2024-09-21)

   - Fixed Areca launchers.
   - Support for Java 8 LTS.
   - Fixed broken links to online documentation.
   - Fixed `Check for new version ...` feature.
   - Fixed building pipeline.
   - Add dependency manager.
   - Add some documentation for users and developers.
   ```
   Annotate any known or potential break change.

4. Update [VersionInfos.java](../../src/com/application/areca/version/VersionInfos.java):
   - Change the `BUILD_ID`
     > `BUILD_ID` is shown in the Areca's log to identify its build version.

     These commands can help to generate a new random value:
     - `javac building/GenerateBuildId.java`
     - `java building.GenerateBuildId`
       ```output
       Next Areca's BUILD ID: 5682047565851761744L for src/com/application/areca/version/VersionInfos.java
       ```
       Replace (v7.5) `BUILD_ID = 5872222636083894532L;` with (v8.0.0) `BUILD_ID = 5682047565851761744L;` in
       [VersionInfos.java](../../src/com/application/areca/version/VersionInfos.java).
   - Add a new entry to `VERSIONS`:
     > `VERSIONS` shows the change log in the feature `History` in `Areca Backup - About`
       (`Help` menu > `About ...` or `Plugins ...`).
     ```Java
     VERSIONS.add(new VersionData("8.0.0", new GregorianCalendar(2024, 8, 21), "Fixed Areca launchers. Support for Java 8 LTS. Fixed broken links to online documentation. Fixed `Check for new version ...` feature. Fixed building pipeline. Add dependency manager. Add some documentation for users and developers."));
     ```
     `month` is a zero-based `int`:
     ```Java
     new GregorianCalendar(intYear, intZeroBasedMonth, intDay)
     ```

5. Update [`version.xml`](../../version.xml).
   > `version.xml` allows Areca to discover the new released version with the feature `Check for new version ...`
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <versiondata
       id="8.0.0"
       date="2024-8-21"
       url="https://github.com/bugtamer/areca-backup/releases"
       description="Fixed Areca launchers. Support for Java 8 LTS. Fixed broken links to online documentation. Fixed `Check for new version ...` feature. Fixed building pipeline. Add dependency manager. Add some documentation for users and developers."
   />
   ```
   - `id` is the release version.
   - `date` is the release date (month is zero-based).
   - `url` is where the Areca's user can download the new release version.
   - `description` is the list of changes that includes this new version.

6. Update the value of `VERSION_URL` in [`ArecaURLs.java`](../../src/com/application/areca/ArecaURLs.java)
   if the location where `version.xml` is hosted has changed its location (e.g. due to a repo fork).

7. Commit changes to `develop` branch:<br>
   `git commit --message "Version 8.0.0"`

8. [Build release bundles](building.md).

9. Upload release bundles to the `url` pointed in [`version.xml`](../../version.xml).

10. Release the source code changes
    - Switch to `main` branch:<br>
      `git checkout main`
    - Update local `main` branch:<br>
      `git pull`
    - Merge `develop` into the `main` branch with a **merge squash**:<br>
      `git merge --squash develop`
    - Commit the merge squash:<br>
      ```shell
      git commit --message "Version 8.0.0
      
      - Fixed Areca launchers.
      - Support for Java 8 LTS.
      - Fixed broken links to online documentation.
      - Fixed `Check for new version ...` feature.
      - Fixed building pipeline.
      - Add dependency manager.
      - Add some documentation for users and developers."
      ```
    - Annotated tag:<br>
      ```shell
      git tag -a v8.0.0 --message "Version 8.0.0
      
      - Fixed Areca launchers.
      - Support for Java 8 LTS.
      - Fixed broken links to online documentation.
      - Fixed `Check for new version ...` feature.
      - Fixed building pipeline.
      - Add dependency manager.
      - Add some documentation for users and developers."
      ```
    - Upload local commits to the remote repo:<br>
      `git push`
