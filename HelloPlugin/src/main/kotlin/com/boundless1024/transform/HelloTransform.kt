package com.boundless1024.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.jar.JarFile

/**
 * 说明：测试Transform
 * 文件名称：HelloTransform.kt
 * 创建者: hallo
 * 邮箱: hallo@xxx.xx
 * 时间: 2021/4/22 9:55 AM
 * 版本：V1.0.0
 */
class HelloTransform : Transform() {
    override fun getName(): String = "HelloTransform"
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS //类型，android这边默认有两中类型：class 、资源

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
        TransformManager.SCOPE_FULL_PROJECT  //范围

    override fun isIncremental(): Boolean = false //是否是增量

    override fun transform(transformInvocation: TransformInvocation?) { //这里面可以操作字节码
        transformInvocation?.let {
            println("--------------- HelloTransform transform start --------------- ")
            val startTime = System.currentTimeMillis()
            val inputs = transformInvocation.inputs
            val outputProvider = transformInvocation.outputProvider

            //删除之前的输出,注意一般需要根据是否是增量，不是增量就全部删除
            outputProvider?.deleteAll()

            //遍历inputs
            inputs.forEach { tt ->
                tt.directoryInputs.forEach { dirInput ->
                    printDirInput(dirInput)
                    //处理完输入文件之后，要把输出给下一个任务
                    val dest = outputProvider.getContentLocation(
                        dirInput.name,
                        dirInput.contentTypes, dirInput.scopes,
                        Format.DIRECTORY
                    )
                    FileUtils.copyDirectory(dirInput.file, dest)
                }
                tt.jarInputs.forEach { jarInput ->
                    printJarInput(jarInput)
                    //处理完输入文件之后，要把输出给下一个任务
                    val dest: File = outputProvider.getContentLocation(
                        jarInput.name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR
                    )
                    FileUtils.copyFile(jarInput.file, dest)
                }
            }

            val cost = (System.currentTimeMillis() - startTime)
            println("--------------- HelloTransform transform end --------------- ")
            println("--------------- HelloTransform 耗时 = $cost ms --------------- ")

        }

    }

    private fun printJarInput(jarInput: JarInput?) {

        jarInput?.let {
            println("jar ${it.name}\n ${it.file.path} \n\n")

            val jarFile = JarFile(jarInput.file)
            jarFile.entries().toList().forEach { jarEntry ->
                println("jar.file  file.name = ${jarEntry.name} ")
            }
        }
    }

    private fun printDirInput(dirInput: DirectoryInput?) {

        dirInput?.let { dirInput ->
            println("dir ${dirInput.name} ${dirInput.file.path}")
            val systemDir = File(dirInput.file.path)
            val fileTree: FileTreeWalk = systemDir.walk()
            fileTree.maxDepth(10)
                .forEach { file->
                    println("file  ${file.isDirectory} file.name = ${file.name}")
                }
          val desiredFile =  fileTree.maxDepth(10)
                .filter { !it.isDirectory && it.name=="BuildConfig.class" }
                .firstOrNull()

            desiredFile?.let {
                println("\n期望操作的class文件 路径 = ${desiredFile.absolutePath}\n")
            }
        }

    }

    private fun addFieldIntoBuildConfigCls(file: File) {

    }
}