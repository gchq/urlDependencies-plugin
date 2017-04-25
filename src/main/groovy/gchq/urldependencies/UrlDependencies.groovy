/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gchq.urldependencies

import org.gradle.api.Plugin
import org.gradle.api.Project

class UrlDependencies implements Plugin<Project> {
    void apply(Project project) {

        project.extensions.create("urlDependencies", UrlDependenciesExtension)

        project.task('downloadUrlDependencies') {
            doLast {
                for(dependency in project.urlDependencies.dependencies){
                    download(dependency.value, "libs/${dependency.key}.jar")
                }
            }
        }
    }

    def download(String remoteUrl, String localUrl) {
        // TODO This works for getting the content length - using this would make the selective download more
        // sophisticated - the user wouldn't have to delete the files to re-download. But if the deps
        // are named with version this probably won't be a big issue.
//        URLConnection connection = new URL(remoteUrl).openConnection()
//        def contentLength = connection.contentLength

        // Create the libs folder if it doesn't already exist
        def libsDir = new File("libs")
        libsDir.mkdir()

        // Download the file, if it doesn't already exist.
        def file = new File(localUrl)
        if(!file.exists()) {
            new File("$localUrl").withOutputStream { out ->
                new URL(remoteUrl).withInputStream { from -> out << from }
            }
        }
    }
}


