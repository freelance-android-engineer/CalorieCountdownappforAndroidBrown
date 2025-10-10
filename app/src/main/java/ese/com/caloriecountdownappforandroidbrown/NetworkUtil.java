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
                return capabilities != null && (
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                );
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