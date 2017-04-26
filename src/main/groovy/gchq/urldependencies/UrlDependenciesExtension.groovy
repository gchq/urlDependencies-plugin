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

import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.collections.SimpleFileCollection

class UrlDependenciesExtension {
    def dependencies = [:]
    def projectRootDir
    def libs = "libs"

    void compile(String name, String url){
        dependencies.put(name, url)
    }

    String getAsPath(String name){
        return "$projectRootDir/${libs}/${name}.jar"
    }

    FileCollection getAsFile(String path){
        return new SimpleFileCollection(new File(path))
    }

    FileCollection get(String name) {
        return new SimpleFileCollection(new File(getAsPath(name)))
    }
}