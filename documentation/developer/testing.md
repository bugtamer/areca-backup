# Areca Backup - Testing

[Build Areca](building.md) before running the following command lines to try out to detect major bugs. 


## Build-in tests

These tests come from Areca Backup v7.5.


### CompareFiles

[`CompareFiles.java`](../../src/com/application/areca/tests/CompareFiles.java)

- $ `java -cp lib/areca.jar com.application.areca.tests.CompareFiles <filename1> <filename2> <outfile>`

Parameters:

- `filename1` (required) First file to compare..
- `filename2` (required) Second file to compare.
- `outfile`   (required) Comparison results, where the values of the columns correspond to the order of the files as arguments.
  The output ends with both columns with EOF.

Example:

- $ `java -cp lib/areca.jar com.application.areca.tests.CompareFiles README AUTHORS outfile.txt`


### ComputeHash

[`ComputeHash.java`](../../src/com/application/areca/tests/ComputeHash.java)

- $ `java -cp lib/areca.jar com.application.areca.tests.ComputeHash <filename1> [filename2] ... [filename#]`

Parameters:

- A list of `filename`s (at least 1 filename).

Example:

- $ `java -cp lib/areca.jar com.application.areca.tests.ComputeHash README AUTHORS`


### CreateData

[`CreateData.java`](../../src/com/application/areca/tests/CreateData.java)

- Utility class.


### DecodeBase64

[`DecodeBase64.java`](../../src/com/application/areca/tests/DecodeBase64.java)

- $ `java -cp lib/areca.jar:lib/commons-codec-1.4.jar com.application.areca.tests.DecodeBase64 <base64>`

Parameters:

- `base64` (required) . String to encode.

Example:

- $ `java -cp lib/areca.jar:lib/commons-codec-1.4.jar com.application.areca.tests.DecodeBase64 base64`


### TargetHandler

[`TargetHandler.java`](../../src/com/application/areca/tests/TargetHandler.java)

- It is an interface, so nothing to test.


### Test

E2E

[`Test.java`](../../src/com/application/areca/tests/Test.java)

- $ `java -cp lib/areca.jar com.application.areca.tests.Test <workspace_path>`

Parameters:

- `workspace_path` (required) An Areca workspace.

Example:

- $  `java -cp lib/areca.jar com.application.areca.tests.Test ~/$USER/.areca/workspace`    (Linux)
- \> `java -cp lib/areca.jar com.application.areca.tests.Test %HOMEPATH%/.areca/workspace` (Windows)


### TestCopy

[`TestCopy.java`](../../src/com/application/areca/tests/TestCopy.java)

- $ `java -cp lib/areca.jar com.application.areca.tests.TestCopy <source_file> <destiny_file>`

Parameters:

- `sourceParentDirectory` Source folder.
- `targetParentDirectory` Destiny folder.

Example:

- $ `java -cp lib/areca.jar com.application.areca.tests.TestCopy release building/tests`


### TestPerfs

[`TestPerfs.java`](../../src/com/application/areca/tests/TestPerfs.java)

- $ `java -cp lib/areca.jar com.application.areca.tests.TestPerfs <workspace_path>`

Parameters:

- `workspace_path` (required) An Areca workspace.

Example:

- $  `java -cp lib/areca.jar com.application.areca.tests.TestPerfs ~/$USER/.areca/workspace`    (Linux)
- \> `java -cp lib/areca.jar com.application.areca.tests.TestPerfs %HOMEPATH%/.areca/workspace` (Windows)


### WorkspaceProcessor

[`WorkspaceProcessor.java`](../../src/com/application/areca/tests/WorkspaceProcessor.java)

- Utility class.
