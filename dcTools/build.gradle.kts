plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("maven-publish")
}

android {
    namespace = "com.dason.dctools"
    compileSdk = 34

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//afterEvaluate {
//    // 官方建议使用上传方法
//    publishing {
//        publications {
//            // Creates a Maven publication called "release".
//            release(MavenPublication) {
//                from components.release // 表示发布 release（jitpack 都不会使用到）
//                groupId = 'com.github.dason007' //groupId 随便取 , 这个是依赖库的组 id
//                artifactId = 'dcTools'  //artifactId 随便取 , 依赖库的名称（jitpack 都不会使用到）
//                version = '1.0.0' // 当前版本依赖库版本号，这个jitpack不会使用到，只是我们开发者自己查看
//            }
//        }
//    }
//}
afterEvaluate {
    // 官方建议使用上传方法
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                from(components["release"]) // 表示发布 release（jitpack 都不会使用到）
                groupId = "com.github.dason007" // groupId 随便取, 这个是依赖库的组 id
                artifactId = "dcTools" // artifactId 随便取, 依赖库的名称（jitpack 都不会使用到）
                version = "1.0.0" // 当前版本依赖库版本号，这个jitpack不会使用到，只是我们开发者自己查看
            }
        }
    }
}

