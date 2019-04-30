-ignorewarnings

#-dontshrink
-keepattributes Signature
-keepattributes *Annotation*
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontwarn
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTabl


#第三方
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *;}
-dontwarn android.support.design.**
-keep class android.support.design.** { *;}

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod

-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-dontwarn javax.annotation.**

-dontwarn kotlin.Unit

-dontwarn retrofit2.KotlinExtensions

-if interface * { @retrofit2.http.* <methods>; }

-keep,allowobfuscation interface <1>

#okhttp

-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-dontwarn org.codehaus.mojo.animal_sniffer.*

-dontwarn okhttp3.internal.platform.ConscryptPlatform

#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}

-keep public class com.alibaba.android.arouter.facade.**{*;}

-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

#Zxing
-dontwarn com.google.zxing.**
-keep  class com.google.zxing.**{*;}


-keep class com.plus.main.bean.** { *; }
-keep class * implements java.io.Serializable { *; }


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}## 保留support下的所有类及其内部类

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent
