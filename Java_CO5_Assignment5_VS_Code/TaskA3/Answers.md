# A3 – Discover the Data-Access Design Pattern

## A3.1 Common pattern
Repeated steps: obtain Connection, prepare SQL, bind parameters, execute, iterate ResultSet, map rows to objects, handle SQLException, close ResultSet/statement/connection. Differences: entity/table names, column lists, key type and object mapping.

## A3.2 Generic DAO
```java
interface Dao<T,K> {
  void insert(T value) throws SQLException;
  Optional<T> findById(K id) throws SQLException;
  List<T> findAll() throws SQLException;
  void update(T value) throws SQLException;
  void delete(K id) throws SQLException;
}
```
`StudentDao implements Dao<Student,Integer>` owns student-specific SQL and maps each ResultSet row to Student. A shared connection provider supplies connections; try-with-resources closes JDBC resources.

## A3.3 Mapping
`Student.id` → `student.student_id` (PRIMARY KEY); `Student.name` → `student.name`; `Student.email` → `student.email`. Use matching SQL/JDBC types.

## Alternative: SQL in service/GUI
SQL becomes duplicated across screens/services. Renaming one column may require edits in many classes; with DAO it is usually centralized in StudentDao. Runtime and per-operation space complexity are effectively the same (database query dominates); DAO improves maintainability, not Big-O query cost.
