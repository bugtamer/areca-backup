# Areca Backup - UI Links

Links of the UI worked fine in Windows but freeze in some Linux systems.

- [ArecaURLs.java](src/com/application/areca/ArecaURLs.java)
  URLs for some links.
- [Anchor.java](../../src/com/application/areca/launcher/gui/composites/Anchor.java)
  New implementation to attempt to fix broken links in some Linux systems.

## AfAffected links

- [AboutWindow.java](../../src/com/application/areca/launcher/gui/AboutWindow.java)
  - `Get more plugins` link
  - `Oficial Site` link
  - `Support Areca Backup` (unmodified) link
- [DonationLink.java](../../src/com/application/areca/launcher/gui/composites/DonationLink.java)
  - `Support Areca Backup ...` link in `Help` menu
- [LogComposite.java](../../src/com/application/areca/launcher/gui/composites/LogComposite.java)
  - `<USER>/.areca/workspace/.log/areca.dd-mm-yy.log` link
- [NewVersionWindow.java](../../src/com/application/areca/launcher/gui/NewVersionWindow.java)
  - `Oficial Site` link
- [PreferencesWindow.java](../../src/com/application/areca/launcher/gui/PreferencesWindow.java)
  - `<USER>/.areca/preferences.properties` link
- [PropertiesComposite.java](../../src/com/application/areca/launcher/gui/composites/PropertiesComposite.java)
  - `*.bcfg` link in the target detail panel
- [RegexFilterComposite.java](../../src/com/application/areca/launcher/gui/filters/RegexFilterComposite.java)
  - `Help` link (`Target edition` >> `Filter edition` window)

See *fix broken links and links that frezee Areca on Linux* commit (2024-11-30).
