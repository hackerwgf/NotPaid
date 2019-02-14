## NotPaid
Add opacity to Activity and decrease it every day until their app completely fades away. Set a due date and customize the number of days you offer them until the app is fully vanished. 

## Dependency
```gradle
dependencies {
    implementation 'com.github.hackerwgf:notpaid:1.0.0'
}
```

## Usage
```java
public class App extends Application {

  @Override 
  public void onCreate() {
    super.onCreate();
    NotPaid.init(this, "2019-02-14", 30);
  }
}
```
## Author

Inspired from github (@kleampa)

Made by hackerwgf