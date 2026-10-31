package com.app.util;

/** Product form ki checking, add aur edit dono servlets yahi use karte hain. */
public class FormUtil {

    /** Sahi ho to null, galat ho to error message. */
    public static String validateProduct(String name, String priceText, String quantityText) {
        if (name == null || name.trim().isEmpty()) {
            return "Product ka naam likho";
        }
        if (name.trim().length() > 100) {
            return "Naam 100 characters se chhota rakho";
        }
        try {
            double price = Double.parseDouble(priceText.trim());
            int quantity = Integer.parseInt(quantityText.trim());

            if (!Double.isFinite(price) || price < 0 || quantity < 0) {
                return "Price aur quantity negative nahi ho sakte";
            }
            if (price > 99999999.99) {
                return "Price bahut zyada hai";
            }
        } catch (NullPointerException | NumberFormatException e) {
            return "Price aur quantity sahi number me likho";
        }
        return null;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
