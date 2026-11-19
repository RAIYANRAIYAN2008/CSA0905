# A2 – Don’t Read What You Don’t Need

## A2.1 Predict the cost
A 50 MiB file read one byte at a time requests about 52,428,800 calls to `read()` (50 × 1,048,576 bytes). Each call incurs Java/native and often OS I/O overhead, so it is much slower than reading blocks. The exact number depends on file size convention and EOF read.

## A2.2 Buffered redesign
Use `BufferedInputStream` with a byte buffer, or read a byte array in chunks (e.g. 8192 bytes). Count `'\n'` in each returned block. The application still examines O(N) bytes, but underlying reads are approximately N / bufferSize rather than N individual requests.

## A2.3 When buffering helps little
If data is already in memory, or the program reads only one tiny block once, extra buffering adds little benefit. It may also add latency for interactive streams that must be consumed immediately.

## Alternative: Files.readAllLines
`Files.readAllLines(path)` reads all lines into memory: time O(N), memory O(N) plus String/list overhead. Buffered streaming is O(N) time and O(B) extra memory for buffer size B. Use readAllLines for small files needing random line access; use buffered streaming for 50 MB or larger logs and bounded-memory processing.
