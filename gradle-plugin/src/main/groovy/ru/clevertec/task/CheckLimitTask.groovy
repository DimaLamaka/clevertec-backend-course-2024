package ru.clevertec.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extension.FileCountReportExtension

class CheckLimitTask extends DefaultTask {

    @TaskAction
    void checkLimit() {
        def extension = project.extensions.getByType(FileCountReportExtension)
        def fileCounts = project.ext.fileCounts

        def maxDirs = extension.getMaxDirsCount().getOrElse(1_000)
        def maxFiles = extension.getMaxFilesCount().getOrElse(1_000)

        if (fileCounts.files > maxFiles || fileCounts.dirs > maxDirs) {
            throw new GradleException("File or directory count exceeded limits. Files: ${fileCounts.files}, Dirs: ${fileCounts.dirs}")
        }

        println "File and directory count within limits. Files: ${fileCounts.files}, Dirs: ${fileCounts.dirs}"
    }
}
