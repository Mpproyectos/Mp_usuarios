
package kreandoapp.mpclientes.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PagoDetalles {

    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("payer")
    private Payer mPayer;

    @SerializedName("payment_methods")
    private PaymentMethods paymentMethods;
    @SerializedName("excluded_payment_types")
    private ExcludedPaymentMethod excludedPaymentMethod;

    public ExcludedPaymentMethod getExcludedPaymentMethod() {
        return excludedPaymentMethod;
    }

    public void setExcludedPaymentMethod(ExcludedPaymentMethod excludedPaymentMethod) {
        this.excludedPaymentMethod = excludedPaymentMethod;
    }

    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Payer getPayer() {
        return mPayer;
    }

    public void setPayer(Payer payer) {
        mPayer = payer;
    }

}
