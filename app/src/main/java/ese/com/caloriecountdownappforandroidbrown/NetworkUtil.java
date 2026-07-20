package ese.com.caloriecountdownappforandroidbrown;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkUtil {

    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;

            // ✅ For Android 10 (API 29) and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Network network = cm.getActiveNetwork();
                if (network == null) return false;

                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                if (capabilities == null) return false;

                // Must have a transport layer (Wi-Fi, cellular, etc.)
                boolean hasTransport =
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH);

                // Must also be configured for internet (not just a local transport link)
                // NET_CAPABILITY_INTERNET: network has internet routing
                // NET_CAPABILITY_VALIDATED: OS has confirmed actual internet reachability
                boolean hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
                boolean isValidated = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);

                return hasTransport && hasInternet && isValidated;
            }
            // ✅ For Android 9 (API 28) and below
            else {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}