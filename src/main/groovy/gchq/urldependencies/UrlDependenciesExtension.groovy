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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UrlDependenciesExtension {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    def dependencies = [:]
    def projectRootDir
    def libs = "libs"

    void compile(String name, String url){
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("name cannot be null or empty")
        }
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("url cannot be null or empty")
        }
        LOGGER.debug("compile called for name [${name}] url [${url}]")
        dependencies.put(name, url)
    }

    String getAsPath(String name){
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("name cannot be null or empty")
        }
        String path = "$projectRootDir/${libs}/${name}.jar"
        LOGGER.debug("getAsPath called for name [${name}], returning ${path}")
        return path
    }

    FileCollection getAsFile(String path){
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("path cannot be null or empty")
        }
        LOGGER.debug("getAsFile called for [${path}]")
        return new SimpleFileCollection(new File(path))
    }

    FileCollection get(String name) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("name cannot be null or empty")
        }
        return new SimpleFileCollection(new File(getAsPath(name)))
    }
}