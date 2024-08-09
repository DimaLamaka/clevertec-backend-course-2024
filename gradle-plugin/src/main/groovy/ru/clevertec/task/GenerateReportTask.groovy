package ru.clevertec.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.extension.FileCountReportExtension

class GenerateReportTask extends DefaultTask {

    @TaskAction
    void generateReport(){
        def extension = project.extensions.getByType(FileCountReportExtension)
        def fileCounts = project.ext.fileCounts

        def reportFile = extension.reportTargetFile.getOrElse(project.layout.buildDirectory.asFile.get())

        reportFile.text = "File and directory count report:\n"
        reportFile.append("Files: ${fileCounts.files}\n")
        reportFile.append("Directories: ${fileCounts.dirs}\n")

        println "Report generated at ${reportFile.absolutePath}"
    }
}
