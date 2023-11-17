package com.example.chef60foods;

import android.app.Application;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "AVGFQ20YbD4vNGPuNixDtnMlvrWXkxeqi7IyuwirMI_he5MI2QpuMqJDSzHLGhVilMLFAXLsQ1PBdC4D",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "com.example.chef60foods://paypalpay"
        ));
    }
}
