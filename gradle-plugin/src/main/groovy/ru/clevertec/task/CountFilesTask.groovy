package ru.clevertec.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extension.FileCountReportExtension

class CountFilesTask extends DefaultTask {

    @TaskAction
    void countFiles() {
        def extension = project.extensions.getByType(FileCountReportExtension)
        def fileCounts = [:]
        def fileCount = 0
        def dirCount = 0

        if (!extension.sourceDir.present || !extension.sourceDir.get().isDirectory()) {
            throw new GradleException("Target dir is null or it is not directory")
        }
        def targetDir = extension.sourceDir.get()
        targetDir.eachFileRecurse { file ->
            if (file.isFile()) {
                fileCount++
            } else if (file.isDirectory()) {
                dirCount++
            }
        }

        fileCounts = [dirs: dirCount, files: fileCount]
        project.ext.fileCounts = fileCounts
        println "Counted ${dirCount} directories and ${fileCount} files."
    }
}
