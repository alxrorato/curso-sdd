# Example Mapping: Basic Cashback Calculation

**Feature:** Basic Cashback Calculation  
**Business Domain:** Customer Loyalty

---

## Rule: Should calculate cashback as a percentage of purchase amount
**Business Logic:** Purchase amount × Merchant's cashback rate = Cashback amount

**Example:** The one where a customer makes a $100 purchase with a merchant having a 5% cashback rate → $5.00 cashback (rounded down to 2 decimal places)

**Counter-example:** The one where a customer makes a $100 purchase with a merchant having a 0.1% cashback rate → $0.10 cashback (minimum 0.01 threshold applies for amounts < $10)

---

## Rule: Must configure merchant cashback rates
**Business Logic:** Each merchant has a predefined cashback rate percentage stored in the system

**Example:** The one where a merchant "Amazon" has a configured 2% cashback rate in the system

**Counter-example:** The one where a merchant's rate is set to 0% (merchants can opt-out of cashback program)

**Implementation:** Cashback rates cannot exceed 50% to prevent excessive rewards

---

## Rule: Must credit calculated cashback to customer's rewards balance
**Business Logic:** After calculation, the cashback amount is added to the customer's existing rewards balance

**Example:** The one where customer has $0.00 balance, makes a $50 purchase with 3% cashback → $1.50 added, new balance $1.50

**Counter-example:** The one where customer has $100.00 balance, makes $100 purchase with 1% cashback → $1.00 added, new balance $101.00

**Implementation:** System should throw an exception if cashback credit would cause balance to exceed system-defined maximum

---

## Rule: Must use BigDecimal for monetary values with specific rounding
**Business Logic:** All monetary calculations use BigDecimal with RoundingMode.DOWN, scale 2, never float/double

**Example:** The one where $29.99 × 7.5% = $2.24925 → rounded down to $2.24 (not $2.25)

**Counter-example:** The one where $0.01 × 15% = $0.0015 → rounded down to $0.00 (minimum rounding down applies)

**Implementation:** Fractional cents less than $0.005 are always rounded down to $0.00
**Implementation:** Apply minimum cashback of $0.01 per transaction (if calculated amount is less than $0.01, credit $0.01)

---

## Rule: Must associate purchases with specific merchants
**Business Logic:** Each purchase is linked to a single merchant to determine the applicable cashback rate

**Example:** The one where purchase at "BestBuy" electronics store uses BestBuy's 3% rate (not the generic 1% default)

**Counter-example:** The one where purchase at a "Hybrid Store" that sells both Amazon and third-party products uses the lower of the applicable rates

**Implementation:** Seasonal promotions: process via admin API with 48-hour advance notice; rate changes effective from next transaction

---

## Rule: Must maintain separate customer balances
**Business Logic:** Each customer has their own independent rewards balance tracked separately

**Example:** The one where Customer A's balance is $10.00, Customer B's balance is $5.00 → each gets cashback added to their own balance only

**Counter-example:** The one where one customer has a negative balance due to promotions/refunds → no further cashback until balance reaches $0.00

**Implementation:** No cashback transfers between customers (prevents abuse, maintains balance independence)

---

## Rule: Must validate purchase amount before calculation
**Business Logic:** Purchase amounts must be positive and within system limits before calculating cashback

**Example:** The one where customer attempts $500.00 purchase with 5% rate → $25.00 cashback calculated (within $1000 maximum)

**Counter-example:** The one where customer attempts $0.00 purchase with 10% rate → no cashback calculated (purchase amount must be > $0.00)

**Implementation:** Maximum purchase amount is $1,000.00 per transaction

---

## Rule: Must apply cashback only to completed purchases
**Business Logic:** Cashback is only calculated and credited for purchases that have been finalized/charged

**Example:** The one where customer completes a $75.00 purchase → $1.50 cashback added (after order confirmation)

**Counter-example:** The one where customer abandons shopping cart with $50.00 items → no cashback calculated until purchase is completed

**Implementation:** Refund processing: reverse cashback credits to customer's balance immediately upon refund; merchant rate remains unchanged

---

## Rule: Must support multiple payment methods for the same merchant
**Business Logic:** Regardless of payment method (credit card, PayPal, etc.), the same cashback rate applies

**Example:** The one where customer pays with credit card for $200.00 at a merchant with 2% rate → $4.00 cashback

**Counter-example:** The one where customer pays with store credit for $50.00 at same merchant → $1.00 cashback (rate still applies, but payment method may affect other business rules)

**Implementation:** Gift cards and store credit excluded from cashback (to prevent circular reward loops)

---

## Rule: Must show pending reward payments as pending
**Business Logic:** When customers use rewards/points to pay for purchases, those payments are marked as pending until the transaction is fully completed

**Example:** The one where customer has $5.00 in rewards balance and uses $5.00 to pay for a $5.00 purchase → payment status shows "pending rewards" until transaction completes successfully

**Implementation:** Reward payments are flagged as "pending" during transaction processing and only released after successful completion

---