# BITalino Aware plugin
Plugin allows to collect BITalino data and propagate in device for another apps by broadcast receiver.

[How install](#how-install)<br/>
[How connect to device by plugin](#how-connect-to-device-by-plugin)<br/>
[How configure connection to device](#how-configure-connection-to-device)<br/>
[Plugin settings](#plugin-settings)<br/>
[How listen by client](#how-listen-by-client)<br/>
[Client example](#broadcast-example)<br/>

### How install
1. Download and install [Aware Framework](http://www.awareframework.com/)
2. Download repository and open in Android Studio
3. Deploy app to smartphone (just by run application) - *Aware framework should detect and install new plugin*

### How connect to device by plugin
1. **Enable BLUETOOTH**
2. Open Aware Framework application on smartphone
3. Go to **stream** tab
4. Click a round plus icon (I mean button to open view with custom plugins) - *bottom right corner*
5. Click **BITalino** plugin
6. Click **SETTINGS**
7. [Configure connection](How-configure-connection-to-device)
8. Click **Start/stop plugin** to start connection (*or stop*)

### How configure connection to device
1. Set BITalino MAC Address
2. Choose channels - *TIP: If you disable channel - BITalino will always send on this place zero value*

### Plugin settings
To change value click on:<br/>
**Start/stop plugin** - Start/stop connection to BITalino device<br/>
**MAC** - MAC Address BITalino device - *eg. 20:16:12:XX:XX:XX*<br/>
**Channels** - Which channels you want to collect<br/>

### How listen by client
Create in your app [BroadcastReceiver](https://developer.android.com/guide/components/broadcasts)<br/>
action name: **pl.agh.broadcast.FRAMES**<br/>
[Example](#broadcast-example)<br/>

### Broadcast example
*Import dependencies in gradle*
```gradle
dependencies {
    implementation 'com.bitalino:bitalino-java-sdk:1.0'
    implementation 'com.google.code.gson:gson:2.8.5'
}
```
*Java code*
```java
class FramesReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(final Context context, final Intent intent) {
    if("pl.agh.broadcast.FRAMES".equalsIgnoreCase(intent.getAction())) {
      String rawFrames = intent.getStringExtra("frames"));
      BITalinoFrame[] frames = new Gson().fromJson(rawFrames);
      
      //do what you want with frames
    }
  }
}

//register broadcast eg. in Activity
framesReceiver = new FramesReceiver();
registerReceiver(framesReceiver, new IntentFilter("pl.agh.broadcast.FRAMES"));
```
