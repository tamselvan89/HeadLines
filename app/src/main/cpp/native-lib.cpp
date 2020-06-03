#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_newsapp_app_BaseApplication_Hello(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from native code C++";
    return env->NewStringUTF(hello.c_str());
}