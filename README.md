# BITalino Aware plugin
Plugin allows to collect BITalino data and propagate in device for another apps by broadcast receiver.

[How install](How-install)<br/>
[How connect to device by plugin](How-connect-to-device-by-plugin)<br/>
[How configure connection to device](How-configure-connection-to-device)<br/>
[How listen by client](How-listen-by-client)<br/>
[Plugin settings](Plugin-settings)<br/>

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

### How listen by client

### Plugin settings
To change value click on:<br/>
**Start/stop plugin** - Start/stop connection to BITalino device<br/>
**MAC** - MAC Address BITalino device - *eg. 20:16:12:XX:XX:XX*<br/>
**Channels** - Which channels you want to collect<br/>
