# Areca Backup - Debugging

## Attach debugging mode

1. Add `-Ddebug=on` argument to the [build command](building.md#build-areca-to-support-debug-mode).

2. Run any of these debug [launchers](launchers.md):

   * `debug_areca.*` (GUI)
   * `debug_areca_cl.*` (TUI)
   * `debug_areca_check_version.*` (check for new versions)
   * `debug_areca_run.*` (startup logic)

3. and attach an IDE's debugger to this Areca instance.


## Launch debugging mode

### Visual Studio Code debug configuration

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
        },
        {
            "type": "java",
            "name": "Launch Areca GUI",
            "request": "launch",
            "mainClass": "com.application.areca.launcher.gui.Launcher",
            "args": "${env:HOME}/.areca",
            "cwd": "${workspaceFolder}",
            "console": "integratedTerminal",
            "env": {
                "GDK_NATIVE_WINDOWS": true,
                "JAVA_PATH": "/usr/share/java",
            },
            "classPaths": [
                "${workspaceFolder}",
                "${workspaceFolder}/translations",
                "${workspaceFolder}/config",
                "${workspaceFolder}/lib",
                "${workspaceFolder}/lib/areca.jar",
                "${workspaceFolder}/lib/mail.jar",
                "${workspaceFolder}/lib/activation.jar",
                "${workspaceFolder}/lib/commons-net-1.4.1.jar",
                "${workspaceFolder}/lib/jakarta-oro-2.0.8.jar",
                "${workspaceFolder}/lib/jsch.jar",
                "${workspaceFolder}/lib/org.eclipse.core.commands_3.2.0.I20060605-1400.jar",
                "${workspaceFolder}/lib/org.eclipse.equinox.common_3.2.0.v20060603.jar",
                "${workspaceFolder}/lib/org.eclipse.jface_3.2.0.I20060605-1400.jar",
                "${workspaceFolder}/lib/swt.jar",
                "${workspaceFolder}/lib/commons-codec-1.4.jar",
            ],
            "vmArgs": [
                "-Xms64m",
                "-Xmx1024m",
                "-Duser.dir=${workspaceFolder}",
                "-Djava.library.path=${workspaceFolder}/lib:${env:JAVA_PATH}/lib:${env:JAVA_PATH}/jni",
                "-Djava.system.class.loader=com.application.areca.impl.tools.ArecaClassLoader",
            ],
        },
    ]
}
```

Check `JAVA_PATH` is right for you system.


## Additional Resources

- [Launch versus attach configurations](https://code.visualstudio.com/docs/debugtest/debugging-configuration#_launch-versus-attach-configurations)
- [Running and debugging Java](https://code.visualstudio.com/docs/java/java-debugging)
- [Java Application Remote Debugging](https://www.baeldung.com/java-application-remote-debugging)
