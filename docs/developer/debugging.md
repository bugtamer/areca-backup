# Areca Backup - Debugging

Run any of these debug [launchers](launchers.md):

* `debug_areca.*` (GUI)
* `debug_areca_cl.*` (TUI)
* `debug_areca_check_version.*` (check for new versions)
* `debug_areca_run.*` (startup logic)

and attach an IDE's debugger to this Areca instance.


## Visual Studio Code debug configuration

`areca-backup/.vscode/launch.json` should have something like this

```json
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Attach to Remote Program",
            "request": "attach",
            "hostName": "127.0.0.1",
            "port": "8000"
        }
    ]
}
```

- [Launch versus attach configurations](https://code.visualstudio.com/docs/debugtest/debugging-configuration#_launch-versus-attach-configurations)
- [Running and debugging Java](https://code.visualstudio.com/docs/java/java-debugging)


## Additional Resources

- [Java Application Remote Debugging](https://www.baeldung.com/java-application-remote-debugging)
