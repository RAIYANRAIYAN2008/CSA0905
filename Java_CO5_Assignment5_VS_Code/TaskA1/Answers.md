# A1 – Design a File Format

## A1.1 Record layouts
**Delimited text:** one UTF-8 CSV row in this order:
`passId,regNo,name,routeNo,boardingStop,validityDate,amountPaid`
Use comma delimiter, quote fields containing commas/quotes/newlines, and escape an internal quote by doubling it (`"` becomes `""`). Never split a quoted CSV row with a plain `split(",")`; use a CSV parser or a documented escaping routine.

**Binary DataOutputStream:** write fields in the same logical order:
`writeUTF(passId)`, `writeUTF(regNo)`, `writeUTF(name)`, `writeInt(routeNo)`, `writeUTF(boardingStop)`, `writeUTF(validityDate)`, `writeDouble(amountPaid)`.
The UTF fields are length-prefixed by `writeUTF`; route is 4 bytes and amount is 8 bytes. The record is variable length because UTF strings vary.

## A1.2 Operations – text design pseudocode
**ADD(pass):** Open `FileWriter(file, true)` wrapped in `BufferedWriter`; validate fields; encode one CSV row; write row and newline; flush/close using try-with-resources. BufferedWriter reduces small write calls.

**FIND(passId):** Open `FileReader` wrapped in `BufferedReader`; read each line; parse CSV; if malformed or field count is not seven, record/skip the damaged row; compare field 1 with passId; return matching record; otherwise return NOT_FOUND. Sequential scan is O(n).

**LIST_BY_ROUTE(route):** Open `BufferedReader`; scan each row; parse and validate; print/store rows whose route field equals route; skip malformed rows and report their line numbers. O(n).

FileInputStream/FileOutputStream are byte streams, suitable for binary data, not preferred for human-readable text. FileReader/FileWriter are character streams; BufferedReader/BufferedWriter add efficient buffering.

## A1.3 Damaged data
- Damaged line: validate field count, numeric route/amount and date format; skip it, log line number and reason; do not crash the whole scan.
- Missing file: for reads report “file not found”; for first add create parent directory and file safely.
- Incomplete write/power failure: write a complete replacement to a temporary file, flush and close, then atomically move it over the original where supported. For append-only designs, keep backups/checksums or a journal; a plain append cannot guarantee an entire final line survived.

## Alternative: RandomAccessFile
Fixed-size records permit direct seek to `i * recordSize`, O(1) access by record index. Finding by pass ID is still O(n) unless a separate index maps ID to record number. Fixed-size name/stop fields need padding and may waste space; overlong values need rejection/truncation policy. Sequential listing remains O(n).
