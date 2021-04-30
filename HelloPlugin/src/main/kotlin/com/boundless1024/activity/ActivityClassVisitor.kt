package com.boundless1024.activity

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 说明：//TODO
 * 文件名称：ActivityClassVisitor.kt
 * 创建者: hallo
 * 邮箱: hallo@xxx.xx
 * 时间: 2021/4/22 5:43 PM
 * 版本：V1.0.0
 */

class ActivityClassVisitor(cv: ClassVisitor?) : ClassVisitor(Opcodes.ASM5, cv), Opcodes {
    private var mClassName: String? = null
    override fun visit(
        version: Int, access: Int, name: String, signature: String,
        superName: String, interfaces: Array<String>
    ) {
        println("ActivityClassVisitor : visit -----> started ：$name")
        mClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int, name: String, desc: String,
        signature: String, exceptions: Array<String>
    ): MethodVisitor {
        println("LifecycleClassVisitor : visitMethod : $name")
        val mv = cv.visitMethod(access, name, desc, signature, exceptions)
        //匹配FragmentActivity
        if ("android/support/v4/app/FragmentActivity" == mClassName) {
            if ("onCreate" == name) {
                //处理onCreate
                return OnCreateMethodVisitor(mv)
            } else if ("onDestroy" == name) {
                //处理onDestroy
//                return LifecycleOnDestroyMethodVisitor(mv)
            }
        }
        return mv
    }

    override fun visitEnd() {
        println("ActivityClassVisitor : visit -----> end")
        super.visitEnd()
    }
}