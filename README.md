# USB (Useful System for the Blind _ Team.BIOS)

점자 교육 어플리케이션

### 해야 할 것들

1. AndroidManifest.xml 파일에 사용 권한 추가

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

2. build.grade (Module: STTTTStest.app) 파일에 dependencies 변경

```
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.6.0'
```
