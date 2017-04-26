# urlDependencies-plugin

A Gradle plugin for retrieving remote dependencies by URL. E.g. from GitHub releases.

## Installation
This is not yet published as a [Gradle Plugin](https://plugins.gradle.org/) so you need to install it locally before you can use it.

Clone and build the plugin. This also installs it into your local Maven repository so it can be found by your projects:

```bash
git clone https://github.com/gchq/urlDependencies-plugin.git
cd urlDependencies-plugin
./gradlew clean build publishToMavenLocal
```

## Usage

### Add dependencies
Add a `urlDependencies` block to your `build.gradle` file. Use `libs` to say where you want the downloaded files to be stored - 'libs' is the default. Use `compile` to add libraries to download. The first parameter is a name you choose and the second is the URL of the file.
```
urlDependencies {
    libs "libs"
    compile('stroom-query-api-6.0-beta.1-SNAPSHOT',
        'https://github.com/gchq/stroom-query/releases/download/6.0-beta.1-SNAPSHOT/stroom-query-api-6.0-beta.1-SNAPSHOT.jar')
    compile('stroom-query-api-6.0-beta.1-SNAPSHOT-sources',
        'https://github.com/gchq/stroom-query/releases/download/6.0-beta.1-SNAPSHOT/stroom-query-api-6.0-beta.1-SNAPSHOT-sources.jar')
    compile('event-logging-v3.1.0',
        'https://github.com/gchq/event-logging/releases/download/v3.1.0/event-logging-v3.1.0.jar')
}
```

Next you need to add your dependencies to the `dependencies` block, using the name you gave them in `urlDependencies`.
```
dependencies {
  // ... Your existing dependencies
  compile urlDependencies.get('stroom-query-api-6.0-beta.1-SNAPSHOT')
}
```
### Running a build
To actually download the dependencies you'll need to run the task `downloadUrlDependencies`, like this:
```bash
./gradlew downloadUrlDependencies clean build
```
You won't need to run this task again unless your dependencies change. This task won't download a dependency if there's already a file with that name. If you want to re-download a dependency, or if a dependency has changed but it's file name hasn't, then you'll need to delete that dependency from your `lib` directory.
