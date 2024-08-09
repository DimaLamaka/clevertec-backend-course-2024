package ru.clevertec.extension

import org.gradle.api.provider.Property

interface FileCountReportExtension {
    Property<File> getSourceDir()
    Property<Integer> getMaxDirsCount()
    Property<Integer> getMaxFilesCount()
    Property<File> getReportTargetFile()
}
