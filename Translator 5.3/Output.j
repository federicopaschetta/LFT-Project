.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 ldc 10
 dup
 istore 0
 pop
L1:
 ldc 20
 dup
 istore 1
 pop
L2:
 iload 1
 dup
 istore 2
 pop
L3:
 iload 0
 ldc 1
 iadd 
 invokestatic Output/print(I)V
L4:
 invokestatic Output/read()I
 istore 3
L5:
 ldc 2
 dup
 istore 4
 pop
L6:
 ldc 1
 dup
 istore 5
 pop
L7:
L9:
 iload 4
 iload 3
 if_icmpge L8
 iload 5
 invokestatic Output/print(I)V
L10:
 ldc 1
 iload 4
 iadd 
 dup
 istore 4
 pop
L11:
 goto L9
L8:
 invokestatic Output/read()I
 istore 6
L12:
 invokestatic Output/read()I
 istore 7
L13:
L15:
 iload 6
 iload 7
 if_icmpeq L14
 iload 6
 iload 7
 if_icmple L17
 iload 6
 iload 7
 isub 
 dup
 istore 6
 pop
 goto L16
L17:
 iload 7
 iload 6
 isub 
 dup
 istore 7
 pop
L16:
 goto L15
L14:
 iload 6
 invokestatic Output/print(I)V
L18:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

