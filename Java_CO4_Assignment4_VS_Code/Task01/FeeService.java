public class FeeService {
    public String pay(String regNo, String amountText) {
        if (regNo == null || regNo.trim().isEmpty()) return "Enter registration number.";
        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (Exception ex) {
            return "Enter a valid amount.";
        }
        if (amount <= 0) return "Amount must be greater than zero.";
        // Demo payment service. Replace this line with secure bank/JDBC integration.
        return String.format("Payment successful for %s. Amount: ₹%.2f", regNo.trim(), amount);
    }
}
