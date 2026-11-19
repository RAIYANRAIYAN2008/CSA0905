# A4 – What Should Be Serialized?

## A4.1 Decide what to persist
Mark the live JDBC `Connection` field `transient`: it is a runtime resource, not durable state, and is not serializable. Password should generally not be persisted; preferably remove it from the model or store a salted password hash in a protected credential store. If it must exist in the object but must not be serialized, mark it transient. Names and register numbers are persisted only if required and access-controlled.

Serialization traverses the reachable object graph from Hostel: its Rooms and each Room's Residents are serialized recursively unless a field is static/transient or the referenced object is otherwise not serializable. Cycles are handled by serialization handles.

## A4.2 Plan for change
With a stable explicit `serialVersionUID`, an older stream can often be read after adding a field; the new field receives its default value (null/0/false) unless custom migration logic supplies a value. If the UID changes or is incompatible, `InvalidClassException` may occur. Define defaults and migration/version logic.

## Alternative: CSV/JSON
Text formats are often larger due to field names/delimiters and encoding, but are inspectable and language-independent. Save/load n residents is O(n) time and O(n) output/input space (or O(1) extra memory with streaming). Adding a field is easier when readers tolerate missing optional properties; serialization is compact/fast for Java object graphs but more tightly coupled to class versions and Java.
