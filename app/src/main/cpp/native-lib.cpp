#include <jni.h>
#include <string>

extern "C" {
#include <libavcodec/avcodec.h>
}

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_fplayer_free_fplayer_FPlayerSplashActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    hello += avcodec_configuration();
    return env->NewStringUTF(hello.c_str());
}
