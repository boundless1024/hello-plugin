package com.boundless1024.activity

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 说明：//TODO
 * 文件名称：OnCreateMethodVisitor.kt
 * 创建者: hallo
 * 邮箱: hallo@xxx.xx
 * 时间: 2021/4/22 5:47 PM
 * 版本：V1.0.0
 */
class OnCreateMethodVisitor(mv: MethodVisitor):MethodVisitor(Opcodes.ASM4, mv) {


    override fun visitCode() {
        inertSelfCode()
        //方法执行前插入
        super.visitCode()
        //方法执行后插入
    }

    override fun visitInsn(opcode: Int) {
        super.visitInsn(opcode)
    }


    private  fun inertSelfCode(){
        mv.visitLdcInsn("TAG");
        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("-------> onCreate : ");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

}