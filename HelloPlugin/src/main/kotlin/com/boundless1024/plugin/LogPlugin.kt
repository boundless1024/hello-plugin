package com.boundless1024.plugin

import com.android.build.gradle.AppExtension
import com.boundless1024.transform.HelloTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 说明：//TODO
 * 文件名称：LogPlugin.kt
 * 创建者: hallo
 * 邮箱: hallo@xxx.xx
 * 时间: 2021/4/22 9:51 AM
 * 版本：V1.0.0
 */
class LogPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)
        println("android = $android 。。。。。")
        android.registerTransform(HelloTransform())
    }
}