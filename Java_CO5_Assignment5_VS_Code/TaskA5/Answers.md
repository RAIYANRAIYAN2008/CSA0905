# A5 – Design for Correctness and Safety

## A5.1 Transaction pseudocode
```text
BEGIN
SET autoCommit FALSE
TRY
  UPDATE wallet SET balance = balance - 500 WHERE wallet_id = 'HOSTEL'
  VERIFY exactly one row changed and sufficient funds
  UPDATE wallet SET balance = balance + 500 WHERE wallet_id = 'CANTEEN'
  VERIFY exactly one row changed
  COMMIT
CATCH error
  ROLLBACK
  REPORT failure
FINALLY
  RESTORE original autoCommit
END
```
Both updates must use the same Connection and transaction. Validate sufficient balance and row counts.

## A5.2 SQL injection
Concatenating input into SQL lets crafted input alter SQL syntax. For example, an ID value such as `' OR '1'='1' -- ` can make a condition true and comment out the remaining predicate depending on the exact query and SQL dialect. Use:
```java
String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
try (PreparedStatement ps = con.prepareStatement(sql)) {
  ps.setString(1, id);
  ps.setString(2, pwd);
  try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
}
```
PreparedStatement treats values as data, not SQL syntax. Passwords should be verified using a password-hashing algorithm, not plaintext comparison.

## Alternative: escaping/regex
Manual quote escaping or regex validation is weaker because SQL syntax varies, encoding/collation edge cases exist, and new inputs can bypass incomplete rules. PreparedStatement parameterizes values. Validation is generally O(length of input), while prepared execution has query preparation/binding overhead; security is the decisive difference. Separate auto-commit updates can leave money debited but not credited if failure occurs between them. A transaction provides atomicity; transaction overhead is usually small relative to the correctness benefit.
