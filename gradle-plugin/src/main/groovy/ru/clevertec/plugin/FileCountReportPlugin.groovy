package ru.clevertec.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.clevertec.extension.FileCountReportExtension
import ru.clevertec.task.CheckLimitTask
import ru.clevertec.task.CountFilesTask
import ru.clevertec.task.GenerateReportTask

class FileCountReportPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create("fileCountReportExtension", FileCountReportExtension)

        project.tasks.register("countFiles", CountFilesTask) {
            group = "File Analysis"
            description = "Counts files and directories."
        }

        project.tasks.register("checkLimits", CheckLimitTask) {
            group = "File Analysis"
            description = "Checks file and directory count limits."
            dependsOn("countFiles")
        }

        project.tasks.register("generateReport", GenerateReportTask) {
            group = "File Analysis"
            description = "Generates a report of the file and directory counts."
            dependsOn("checkLimits")
        }

        project.tasks.register("fileAnalysis") {
            group = "File Analysis"
            description = "Runs the file analysis tasks."
            dependsOn "countFiles", "checkLimits", "generateReport"
        }
    }
}
