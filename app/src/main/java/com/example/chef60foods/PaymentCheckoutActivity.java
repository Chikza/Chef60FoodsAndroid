package com.example.chef60foods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.cancel.OnCancel;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import java.util.ArrayList;

public class PaymentCheckoutActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";

    PaymentButtonContainer paymentButtonContainer;
    private Handler timeoutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_checkout);

        paymentButtonContainer = findViewById(R.id.payment_button_container);

        timeoutHandler = new Handler(Looper.getMainLooper());

        paymentButtonContainer.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        Log.d(TAG, "create: ");
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.USD)
                                                        .value("3.00")
                                                        .build()
                                        )
                                        .build()
                        );
                        OrderRequest order = new OrderRequest(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                        resetTimeout();
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {

                                Log.d(TAG, String.format("CaptureOrderResult: %s", result));
                                Toast.makeText(PaymentCheckoutActivity.this, "Reservation Successful", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }

        );
        new OnCancel() {
            @Override
            public void onCancel() {
                Log.d("OnCancel", "Buyer cancelled the PayPal experience.");
            }
        };
    }

    private void resetTimeout() {
        timeoutHandler.removeCallbacksAndMessages(null); // Remove existing callbacks
        timeoutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PaymentCheckoutActivity.this, "Authentication Timeout", Toast.LENGTH_SHORT).show();
            }
        }, 10 * 60 * 1000); // 10 minutes timeout
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeoutHandler.removeCallbacksAndMessages(null);
    }
}