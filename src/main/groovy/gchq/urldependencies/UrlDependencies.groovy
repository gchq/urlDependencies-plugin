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
        project.urlDependencies.projectRootDir = project.getRootDir()

        project.task('downloadUrlDependencies') {
            doLast {
                // Create the libs folder if it doesn't already exist
                def libsDir = new File(project.urlDependencies.libs)
                libsDir.mkdir()

                for(dependency in project.urlDependencies.dependencies){
                    download(dependency.value, "${project.urlDependencies.libs}/${dependency.key}.jar")
                }
            }
        }
    }

    def download(String sourceUrl, String destinationPath) {
        // TODO This works for getting the content length - using this would make the selective download more
        // sophisticated - the user wouldn't have to delete the files to re-download. But if the deps
        // are named with version this probably won't be a big issue.
//        URLConnection connection = new URL(remoteUrl).openConnection()
//        def contentLength = connection.contentLength

        // Download the file, if it doesn't already exist.
        def file = new File(destinationPath)
        if(!file.exists()) {
            new File("$destinationPath").withOutputStream { out ->
                new URL(sourceUrl).withInputStream { from -> out << from }
            }
        }
    }
}


