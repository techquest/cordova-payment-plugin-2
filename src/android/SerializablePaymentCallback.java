import com.interswitchng.sdk.payment.IswCallback;

import org.apache.cordova.CallbackContext;

public class SerializablePaymentCallback<T> extends IswCallback<T> {

	private IswCallback<T> callback;
	
	public SerializablePaymentCallback (IswCallback<T> callback) {
		this.callback = callback;
	}
	
	@Override
	public void onError(Exception error) {
		callback.onError(error);
	}

	@Override
	public void onSuccess(T response) {
		callback.onSuccess(response);
	}
	
}