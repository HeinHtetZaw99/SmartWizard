#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_eternalweintsein_model_BaseModel_getAppTokenMessage(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "eternalweintsein");
}

JNIEXPORT jstring JNICALL
Java_com_eternalweintsein_model_BaseModel_getAppTokenKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "b!n@ryL@b");
}
