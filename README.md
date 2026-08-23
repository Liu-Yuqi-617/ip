# Victoria project template

This is a project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Victoria.java` file, right-click it, and choose `Run Victoria.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Building and running the fat JAR

This project uses the [Shadow Gradle plugin](https://github.com/GradleUp/shadow) to package the application classes and their dependencies into one executable JAR.

From the project root, use JDK 25 and run:

```text
gradlew.bat clean shadowJar
```

On macOS or Linux, use `./gradlew clean shadowJar` instead. The generated fat JAR is placed at `build/libs/duke.jar`. The `build/` directory is ignored by Git, so the generated binary is not committed.

Run the JAR with:

```text
java -jar build/libs/duke.jar
```

On Windows, the equivalent path uses backslashes:

```text
java -jar build\libs\duke.jar
```

To distribute the JAR, create a release in your fork on GitHub, use a version such as `v0.1`, and attach `build/libs/duke.jar` under **Attach binaries by dropping them here**. Do not add the JAR to a commit.
